package com.example.elearning;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.Nullable;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;

public class Profile extends AppCompatActivity {

    private EditText editNama, editEmail, editGender;
    private Button btnSave;
    private FirebaseAuth auth;
    private FirebaseFirestore firestore;
    private ImageView profile;
    private ImageButton profileeditbutton;
    private final int IMG_REQ =2000;
    Uri imageProfile;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_profile);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        // Kustomisasi gambar profile

        profile = findViewById(R.id.ivProfilePicture);
        profileeditbutton = findViewById(R.id.btn_editprofile);


        profileeditbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent kustomprofileintent = new Intent(Intent.ACTION_PICK);
                kustomprofileintent.setData(MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(kustomprofileintent,IMG_REQ);


            }
        });

        // Back button
        Button btnBack = findViewById(R.id.btnBack);
        btnBack.setOnClickListener(v -> finish());

        auth = FirebaseAuth.getInstance();
        firestore = FirebaseFirestore.getInstance();

        // Inisialisasi EditText dan Button
        editNama = findViewById(R.id.edit_Nama);
        editEmail = findViewById(R.id.edit_Email);
        editGender = findViewById(R.id.edit_Gender);
        btnSave = findViewById(R.id.btn_save);

        // Tampilkan Toast ketika user mencoba mengklik input email
        editEmail.setOnClickListener(v ->
                Toast.makeText(Profile.this, "Email tidak dapat diubah", Toast.LENGTH_SHORT).show()
        );

        // Menampilkan data pengguna yang sudah ada dari Firestore
        String userId = Objects.requireNonNull(auth.getCurrentUser()).getUid();
        firestore.collection("users").document(userId).get()
                .addOnSuccessListener(documentSnapshot -> {
                    if (documentSnapshot.exists()) {
                        String username = documentSnapshot.getString("username");
                        String email = documentSnapshot.getString("email");
                        String gender = documentSnapshot.getString("gender");
                        String profile_picture = documentSnapshot.getString("profile_picture");

                        editNama.setText(username);
                        editEmail.setHint(email); // Gunakan hint untuk tampilan email
                        editGender.setText(gender);
                        if (profile_picture != null) {
                            displayProfileImage(profile_picture);
                        }
                    }
                })
                .addOnFailureListener(e ->
                        Toast.makeText(Profile.this, "Gagal memuat data: " + e.getMessage(), Toast.LENGTH_SHORT).show()
                );

        // Ketika tombol Save diklik
        btnSave.setOnClickListener(v -> {
            String nama = editNama.getText().toString().trim();
            String gender = editGender.getText().toString().trim();

            if (nama.isEmpty() || gender.isEmpty()) {
                Toast.makeText(Profile.this, "Semua field harus diisi", Toast.LENGTH_SHORT).show();
                return;
            }

            // Update data di Firestore (hanya username dan gender)
            firestore.collection("users").document(userId)
                    .update("username", nama, "gender", gender)
                    .addOnSuccessListener(aVoid -> {
                        Toast.makeText(Profile.this, "Profil berhasil diperbarui", Toast.LENGTH_SHORT).show();

                        // Muat ulang data ke input field
                        firestore.collection("users").document(userId).get()
                                .addOnSuccessListener(updatedSnapshot -> {
                                    if (updatedSnapshot.exists()) {
                                        editNama.setText(updatedSnapshot.getString("username"));
                                        editGender.setText(updatedSnapshot.getString("gender"));
                                    }
                                });
                    })
                    .addOnFailureListener(e ->
                            Toast.makeText(Profile.this, "Gagal memperbarui profil: " + e.getMessage(), Toast.LENGTH_SHORT).show()
                    );
        });
    }

    private String encodeImageToBase64(Uri imageUri) {
        try {
            // Membaca file gambar dari URI
            InputStream inputStream = getContentResolver().openInputStream(imageUri);

            // Membaca gambar menjadi bitmap
            Bitmap bitmap = BitmapFactory.decodeStream(inputStream);

            // Mengonversi bitmap menjadi byte array
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
            byte[] imageBytes = byteArrayOutputStream.toByteArray();

            // Mengonversi byte array ke string Base64
            return Base64.encodeToString(imageBytes, Base64.DEFAULT);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    private void saveImageToFirestore(String base64Image) {
        String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        FirebaseFirestore firestore = FirebaseFirestore.getInstance();

        // Menyimpan Base64 image ke Firestore
        firestore.collection("users").document(userId)
                .update("profile_picture", base64Image)
                .addOnSuccessListener(aVoid -> {
                    Toast.makeText(Profile.this, "Gambar berhasil disimpan", Toast.LENGTH_SHORT).show();
                    // Kirim data kembali ke MainActivity
                    Intent resultIntent = new Intent();
                    resultIntent.putExtra("profile_picture", base64Image);
                    setResult(RESULT_OK, resultIntent);
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(Profile.this, "Gagal menyimpan gambar", Toast.LENGTH_SHORT).show();
                });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK && data != null) {
            // Mengambil URI gambar yang dipilih
            Uri imageUri = data.getData();

            // Mengonversi gambar ke Base64
            String base64Image = encodeImageToBase64(imageUri);

            if (base64Image != null) {
                // Menyimpan Base64 ke Firestore
                saveImageToFirestore(base64Image);

                // Ambil gambar terbaru dari Firestore dan tampilkan
                firestore.collection("users").document(FirebaseAuth.getInstance().getCurrentUser().getUid()).get()
                        .addOnSuccessListener(documentSnapshot -> {
                            if (documentSnapshot.exists()) {
                                String base64ImageFromFirestore = documentSnapshot.getString("profile_picture");
                                if (base64ImageFromFirestore != null) {
                                    displayProfileImage(base64ImageFromFirestore);
                                }
                            }
                        })
                        .addOnFailureListener(e -> Toast.makeText(Profile.this, "Gagal memuat gambar", Toast.LENGTH_SHORT).show());
            } else {
                Toast.makeText(Profile.this, "Gagal mengonversi gambar", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private Bitmap decodeBase64ToBitmap(String base64Image) {
        try {
            byte[] decodedBytes = Base64.decode(base64Image, Base64.DEFAULT);
            return BitmapFactory.decodeByteArray(decodedBytes, 0, decodedBytes.length);
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
            return null;
        }
    }

    // Menampilkan gambar di ImageView
    private void displayProfileImage(String base64Image) {
        Bitmap bitmap = decodeBase64ToBitmap(base64Image);
        if (bitmap != null) {
            profile.setImageBitmap(bitmap);  // Update tampilan dengan gambar baru
        } else {
            Toast.makeText(Profile.this, "Gagal mendekode gambar", Toast.LENGTH_SHORT).show();
        }
    }
}

