package com.example.elearning;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
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

                        editNama.setText(username);
                        editEmail.setHint(email); // Gunakan hint untuk tampilan email
                        editGender.setText(gender);
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode==RESULT_OK){
            imageProfile = data.getData();
            profile.setImageURI(imageProfile);
        }
    }
}

