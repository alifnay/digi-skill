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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_detail);

        // Ambil data dari Intent
        String chapterName = getIntent().getStringExtra("chapterName");
        TextView tvChapterName = findViewById(R.id.tvSubjectName);
        tvChapterName.setText(chapterName);;
        // Set elemen kedua
        TextView tvChapterName2 = findViewById(R.id.tvSubjectName2);
        tvChapterName2.setText(chapterName);
        // Back button
        Button btnBack = findViewById(R.id.btnBack);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();  // Menutup halaman Profile dan kembali ke MainActivity
            }
        });
    }
}