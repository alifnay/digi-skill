package com.example.elearning;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.elearning.MainActivity;
import com.example.elearning.R;
import com.example.elearning.register;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class login extends AppCompatActivity {

    private FirebaseAuth auth;
    private FirebaseFirestore firestore;
    private EditText editText_email, editText_password;
    private Button btn_login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);

        auth = FirebaseAuth.getInstance();
        firestore = FirebaseFirestore.getInstance();
        editText_email = findViewById(R.id.editText_email);
        editText_password = findViewById(R.id.editText_password);
        btn_login = findViewById(R.id.btn_login);

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = editText_email.getText().toString().trim();
                String password = editText_password.getText().toString().trim();

                if (!email.isEmpty() && Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                    if (!password.isEmpty()) {
                        auth.signInWithEmailAndPassword(email, password)
                                .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                                    @Override
                                    public void onSuccess(AuthResult authResult) {
                                        String userId = auth.getCurrentUser().getUid();

                                        // Mengambil data pengguna dari Firestore
                                        firestore.collection("users").document(userId)
                                                .get()
                                                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                                                    @Override
                                                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                                                        if (documentSnapshot.exists()) {
                                                            String username = documentSnapshot.getString("username");
                                                            String email = documentSnapshot.getString("email");

                                                            // Tampilkan pesan atau simpan data untuk digunakan di MainActivity
                                                            Toast.makeText(login.this, "Welcome, " + username, Toast.LENGTH_SHORT).show();
                                                            Intent intent = new Intent(login.this, MainActivity.class);
                                                            intent.putExtra("username", username);
                                                            intent.putExtra("email", email);
                                                            startActivity(intent);
                                                            finish();
                                                        }
                                                    }
                                                })
                                                .addOnFailureListener(e -> Toast.makeText(login.this, "Gagal mengambil data pengguna", Toast.LENGTH_SHORT).show());
                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Toast.makeText(login.this, "Login Gagal", Toast.LENGTH_SHORT).show();
                                    }
                                });
                    } else {
                        editText_password.setError("Password tidak boleh kosong");
                    }
                } else if (email.isEmpty()) {
                    editText_email.setError("Email tidak boleh kosong");
                } else {
                    editText_email.setError("Format email tidak valid");
                }
            }
        });

        TextView textToRegister = findViewById(R.id.text_to_register);
        textToRegister.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(login.this, register.class);
                startActivity(intent);
            }
        });
    }
    protected void onStart() {
        super.onStart();

        // Cek apakah pengguna sudah login
        if (auth.getCurrentUser() != null) {
            // Langsung ke MainActivity
            Intent intent = new Intent(login.this, MainActivity.class);
            startActivity(intent);
            finish(); // Tutup LoginActivity
        }
    }
}
