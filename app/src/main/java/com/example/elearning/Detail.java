package com.example.elearning;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class Detail extends AppCompatActivity {

    private TextView chapterNameTextView;
    private ImageView chapterImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_detail);

        // Ambil data dari Intent
        String subjectName = getIntent().getStringExtra("subjectName");
        TextView tvSubjectName = findViewById(R.id.tvSubjectName);
        // Atur data ke elemen UI
        tvSubjectName.setText(subjectName);
        Button btnBack = findViewById(R.id.btnBack);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Detail.this, MainActivity.class);
                startActivity(intent);  // Pindah ke ActivityDetail
            }
        });
    }
}