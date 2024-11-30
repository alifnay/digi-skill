package com.example.elearning;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
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

        // Back button
        Button btnBack = findViewById(R.id.btnBack);
        btnBack.setOnClickListener(v -> {
            finish(); // Menutup ProfileActivity dan kembali ke aktivitas sebelumnya
        });

        auth = FirebaseAuth.getInstance();
        firestore = FirebaseFirestore.getInstance();

        // Inisialisasi EditText dan Button
        editNama = findViewById(R.id.edit_Nama);
        editEmail = findViewById(R.id.edit_Email);
        editGender = findViewById(R.id.edit_Gender);
        btnSave = findViewById(R.id.btn_save);

        // Menampilkan data pengguna yang sudah ada (misalnya dari Intent atau Firestore)
        // Misalnya, kamu bisa mengambil data pengguna yang sudah ada dari Intent yang dikirim dari LoginActivity
        String username = getIntent().getStringExtra("username");
        String email = getIntent().getStringExtra("email");
        editNama.setText(username);
        editEmail.setText(email);

        // Ketika tombol Save diklik
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nama = editNama.getText().toString().trim();
                String email = editEmail.getText().toString().trim();
                String gender = editGender.getText().toString().trim();

                if (nama.isEmpty() || email.isEmpty() || gender.isEmpty()) {
                    Toast.makeText(Profile.this, "Semua field harus diisi", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Mengambil ID pengguna dari Firebase Authentication
                String userId = Objects.requireNonNull(auth.getCurrentUser()).getUid();

                // Update data di Firestore
                firestore.collection("users").document(userId)
                        .update("username", nama, "email", email, "gender", gender)
                        .addOnSuccessListener(aVoid -> {
                            Toast.makeText(Profile.this, "Profil berhasil diperbarui", Toast.LENGTH_SHORT).show();
                        })
                        .addOnFailureListener(e -> {
                            Toast.makeText(Profile.this, "Gagal memperbarui profil: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                        });
            }
        });
    }
}
