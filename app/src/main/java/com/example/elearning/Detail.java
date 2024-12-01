package com.example.elearning;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import java.util.HashMap;

public class Detail extends AppCompatActivity {

    private TextView chapterNameTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_detail);

        // Ambil data dari Intent
        String chapterName = getIntent().getStringExtra("chapterName");
        Log.d("DetailActivity", "Chapter Name: " + chapterName);

        // Deklarasi elemen UI
        TextView tvChapterName = findViewById(R.id.tvSubjectName);
        TextView tvChapterName2 = findViewById(R.id.tvSubjectName2);
        TextView modulDescription = findViewById(R.id.modul_description);
        WebView youtubeWebView = findViewById(R.id.ivBanner);
        try {
            youtubeWebView = findViewById(R.id.ivBanner);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        // Atur nama chapter di TextView
        if (chapterName != null) {
            tvChapterName.setText(chapterName);
            tvChapterName2.setText(chapterName);

            // Atur deskripsi berdasarkan chapterName
            String description = getDescriptionByChapter(chapterName);
            modulDescription.setText(description);

            // Ambil video ID berdasarkan chapterName
            String videoId = getVideoIdByChapter(chapterName);

            // Muat video YouTube ke WebView
            if (!videoId.isEmpty()) {
                youtubeWebView.getSettings().setJavaScriptEnabled(true); // Aktifkan JavaScript
                youtubeWebView.loadData(
                        "<html><body style='margin:0;padding:0;'><iframe width=\"100%\" height=\"100%\" src=\"https://www.youtube.com/embed/" + videoId + "\" " +
                                "frameborder=\"0\" allow=\"autoplay; encrypted-media\" allowfullscreen></iframe></body></html>",
                        "text/html",
                        "utf-8"
                );
            } else {
                youtubeWebView.setVisibility(View.GONE); // Sembunyikan WebView jika tidak ada video
            }
        }

        // Tombol kembali
        Button btnBack = findViewById(R.id.btnBack);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();  // Menutup halaman Detail dan kembali ke aktivitas sebelumnya
            }
        });
    }

    // Metode untuk mengambil deskripsi berdasarkan nama chapter
    private String getDescriptionByChapter(String chapterName) {
        switch (chapterName) {
            case "Introduction to HTML":
                return getString(R.string.descriptionhtml); // Deskripsi untuk HTML
            case "CSS Basics":
                return getString(R.string.descriptioncss); // Deskripsi untuk CSS
            case "JavaScript Fundamentals":
                return getString(R.string.descriptionjs); // Deskripsi untuk JavaScript
            case "Responsive Design":
                return getString(R.string.descriptionrd); // Deskripsi untuk Responsive Design
            case "Introduction to Web Hosting":
                return getString(R.string.descriptionwh); // Deskripsi untuk web hosting
            case "Introduction to Mobile Development":
                return getString(R.string.descriptionmd); // Deskripsi untuk mobile development
            case "Building User Interfaces":
                return getString(R.string.descriptionbui); // Deskripsi untuk building user interfaces
            case "Handling User Input":
                return getString(R.string.descriptionhui); // Deskripsi untuk handling user input
            case "Data Storage and Persistence":
                return getString(R.string.descriptiondsp); // Deskripsi untuk data storage and persistence
            case "Introduction to Data Analysis":
                return getString(R.string.descriptionda); // Deskripsi untuk data analysis
            case "Data Collection Techniques":
                return getString(R.string.descriptiondct); // Deskripsi untuk data collection techniques
            case "Data Cleaning and Preprocessing":
                return getString(R.string.descriptiondcp); // Deskripsi untuk data cleaning and preprocessing
            case "Exploratory Data Analysis (EDA)":
                return getString(R.string.descriptioneda); // Deskripsi untuk exploratory data analysis
            case "Introduction to Machine Learning":
                return getString(R.string.descriptioniml); // Deskripsi untuk introduction to machine learning
            case "Model Evaluation and Metrics":
                return getString(R.string.descriptionmem); // Deskripsi untuk model evaluation and metrics
            case "Supervised Learning":
                return getString(R.string.descriptionsl); // Deskripsi untuk supervised learning
            case "Unsupervised Learning":
                return getString(R.string.descriptionul); // Deskripsi untuk unsupervised learning
            case "Feature Engineering":
                return getString(R.string.descriptionfe); // Deskripsi untuk feature engineering
            case "Introduction to DevOps":
                return getString(R.string.descriptionid); // Deskripsi untuk introduction to DevOps
            case "Continuous Integration (CI)":
                return getString(R.string.descriptionci); // Deskripsi untuk continuous integration
            case "Continuous Deployment (CD)":
                return getString(R.string.descriptioncd); // Deskripsi untuk continuous deployment
            case "Infrastructure as Code (IaC)":
                return getString(R.string.descriptioniac); // Deskripsi untuk infrastructure as code
            case "Monitoring and Logging":
                return getString(R.string.descriptionmal); // Deskripsi untuk monitoring and logging
            default:
                return getString(R.string.description); // Deskripsi default
        }
    }

    private String getVideoIdByChapter(String chapterName) {
        HashMap<String, String> chapterToVideoMap = new HashMap<>();
        chapterToVideoMap.put("Introduction to HTML", "F7oLxNzpYB4");

        return chapterToVideoMap.getOrDefault(chapterName, ""); // Return ID default jika tidak ditemukan
    }
}
