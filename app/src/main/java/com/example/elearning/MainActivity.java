package com.example.elearning;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.app.AppCompatActivity;

import com.example.elearning.adapters.SubjectAdapter;
import com.example.elearning.models.Chapter;
import com.example.elearning.models.Subject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private RecyclerView rvSubject;
    private TextView tvUsername, tvEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initComponents();
        ImageButton btnEditProfile = findViewById(R.id.btnEditProfile);
        btnEditProfile.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, Profile.class);
            startActivity(intent);
        });

        // Referensi TextView di activity_main.xml
        tvUsername = findViewById(R.id.tvUsername);
        tvEmail = findViewById(R.id.tvEmail);
        // Ambil data dari Intent
        String username = getIntent().getStringExtra("username");
        String email = getIntent().getStringExtra("email");

        // Tampilkan data ke TextView
        if (username != null) {
            tvUsername.setText(username);
        }
        if (email != null) {
            tvEmail.setText(email);
        }

        ArrayList<Subject> subjects = prepareData();

        SubjectAdapter subjectAdapter = new SubjectAdapter(subjects, MainActivity.this);
        LinearLayoutManager manager = new LinearLayoutManager(MainActivity.this);
        rvSubject.setLayoutManager(manager);
        rvSubject.setAdapter(subjectAdapter);
        // Menemukan tombol btnNextIcon
        // Button btnNextIcon = findViewById(R.id.btnNextIcon);

    }

    private void initComponents() {
        rvSubject = findViewById(R.id.rvSubject);
    }

    private ArrayList<Subject> prepareData() {
        ArrayList<Subject> subjects = new ArrayList<Subject>();

        Subject website = new Subject();
        website.id = 1;
        website.subjectName = "Website Development";
        website.chapters = new ArrayList<Chapter>();

        Chapter chapter1 = new Chapter();
        chapter1.id = 1;
        chapter1.chapterName = "Introduction to HTML";
        chapter1.imageUrl = "https://upload.wikimedia.org/wikipedia/commons/thumb/6/61/HTML5_logo_and_wordmark.svg/800px-HTML5_logo_and_wordmark.svg.png";

        Chapter chapter2 = new Chapter();
        chapter2.id = 2;
        chapter2.chapterName = "CSS Basics";
        chapter2.imageUrl = "https://upload.wikimedia.org/wikipedia/commons/thumb/d/d5/CSS3_logo_and_wordmark.svg/640px-CSS3_logo_and_wordmark.svg.png";

        Chapter chapter3 = new Chapter();
        chapter3.id = 3;
        chapter3.chapterName = "JavaScript Fundamentals";
        chapter3.imageUrl = "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRuHnJDLOcdm_0b6N6kNj-1OvO9KhKYgqIy0w&s";

        Chapter chapter4 = new Chapter();
        chapter4.id = 4;
        chapter4.chapterName = "Responsive Design";
        chapter4.imageUrl = "https://cdn-icons-png.flaticon.com/512/65/65381.png";

        Chapter chapter5 = new Chapter();
        chapter5.id = 5;
        chapter5.chapterName = "Introduction to Web Hosting";
        chapter5.imageUrl = "https://cdn-icons-png.flaticon.com/512/5139/5139874.png";

        website.chapters.add(chapter1);
        website.chapters.add(chapter2);
        website.chapters.add(chapter3);
        website.chapters.add(chapter4);
        website.chapters.add(chapter5);

        Subject mobile = new Subject();
        mobile.id = 2;
        mobile.subjectName = "Mobile Development";
        mobile.chapters = new ArrayList<Chapter>();

        Chapter chapter6 = new Chapter();
        chapter6.id = 6;
        chapter6.chapterName = "Introduction to Mobile Development";
        chapter6.imageUrl = "https://upload.wikimedia.org/wikipedia/commons/thumb/7/74/Kotlin_Icon.png/1200px-Kotlin_Icon.png";

        Chapter chapter7 = new Chapter();
        chapter7.id = 7;
        chapter7.chapterName = "Building User Interfaces";
        chapter7.imageUrl = "https://cdn-icons-png.flaticon.com/512/6047/6047946.png";

        Chapter chapter8 = new Chapter();
        chapter8.id = 8;
        chapter8.chapterName = "Handling User Input";
        chapter8.imageUrl = "https://cdn-icons-png.flaticon.com/512/338/338106.png";

        Chapter chapter9 = new Chapter();
        chapter9.id = 9;
        chapter9.chapterName = "Data Storage and Persistence";
        chapter9.imageUrl = "https://cdn-icons-png.flaticon.com/512/2906/2906274.png";

        mobile.chapters.add(chapter6);
        mobile.chapters.add(chapter7);
        mobile.chapters.add(chapter8);
        mobile.chapters.add(chapter9);

        Subject data = new Subject();
        data.id = 3;
        data.subjectName = "Data Analysis";
        data.chapters = new ArrayList<Chapter>();

        Chapter chapter10 = new Chapter();
        chapter10.id = 10;
        chapter10.chapterName = "Introduction to Data Analysis";
        chapter10.imageUrl = "https://cdn-icons-png.freepik.com/256/6897/6897881.png?semt=ais_hybrid";

        Chapter chapter11 = new Chapter();
        chapter11.id = 11;
        chapter11.chapterName = "Data Collection Techniques";
        chapter11.imageUrl = "https://cdn-icons-png.flaticon.com/512/3270/3270865.png";

        Chapter chapter12 = new Chapter();
        chapter12.id = 12;
        chapter12.chapterName = "Data Cleaning and Preprocessing";
        chapter12.imageUrl = "https://cdn-icons-png.flaticon.com/512/5143/5143301.png";

        Chapter chapter13 = new Chapter();
        chapter13.id = 13;
        chapter13.chapterName = "Exploratory Data Analysis (EDA)";
        chapter13.imageUrl = "https://cdn-icons-png.flaticon.com/512/8920/8920975.png";

        data.chapters.add(chapter10);
        data.chapters.add(chapter11);
        data.chapters.add(chapter12);
        data.chapters.add(chapter13);

        Subject ml = new Subject();
        ml.id = 4;
        ml.subjectName = "Machine Learning";
        ml.chapters = new ArrayList<Chapter>();

        Chapter chapter14 = new Chapter();
        chapter14.id = 14;
        chapter14.chapterName = "Introduction to Machine Learning";
        chapter14.imageUrl = "https://cdn-icons-png.flaticon.com/512/2980/2980560.png";

        Chapter chapter15 = new Chapter();
        chapter15.id = 15;
        chapter15.chapterName = "Supervised Learning";
        chapter15.imageUrl = "https://thumbs.dreamstime.com/b/supervised-ai-machine-learning-line-color-icon-artificial-intelligence-algorithm-technology-innovation-outline-vector-sign-315721335.jpg";

        Chapter chapter16 = new Chapter();
        chapter16.id = 16;
        chapter16.chapterName = "Unsupervised Learning";
        chapter16.imageUrl = "https://thumbs.dreamstime.com/b/machine-unsupervised-learning-thin-line-icon-ai-artificial-intelligence-algorithm-future-technology-data-analysis-computer-science-308423519.jpg";

        Chapter chapter17 = new Chapter();
        chapter17.id = 17;
        chapter17.chapterName = "Feature Engineering";
        chapter17.imageUrl = "https://img.freepik.com/premium-vector/feature-engineering-icon-vector-image-can-be-used-data-science_120816-348650.jpg";

        Chapter chapter18 = new Chapter();
        chapter18.id = 18;
        chapter18.chapterName = "Model Evaluation and Metrics";
        chapter18.imageUrl = "https://img.freepik.com/premium-vector/performance-metrics-icon-vector-image-can-be-used-action-plan_120816-355524.jpg";

        ml.chapters.add(chapter14);
        ml.chapters.add(chapter18);
        ml.chapters.add(chapter15);
        ml.chapters.add(chapter16);
        ml.chapters.add(chapter17);

        Subject devops = new Subject();
        devops.id = 5;
        devops.subjectName = "DevOps";
        devops.chapters = new ArrayList<Chapter>();

        Chapter chapter19 = new Chapter();
        chapter19.id = 19;
        chapter19.chapterName = "Introduction to DevOps";
        chapter19.imageUrl = "https://cdn-icons-png.flaticon.com/512/5396/5396960.png";

        Chapter chapter20 = new Chapter();
        chapter20.id = 20;
        chapter20.chapterName = "Continuous Integration (CI)";
        chapter20.imageUrl = "https://cdn-icons-png.flaticon.com/512/10435/10435186.png";

        Chapter chapter21 = new Chapter();
        chapter21.id = 21;
        chapter21.chapterName = "Continuous Deployment (CD)";
        chapter21.imageUrl = "https://cdn-icons-png.flaticon.com/512/8759/8759142.png";

        Chapter chapter22 = new Chapter();
        chapter22.id = 22;
        chapter22.chapterName = "Infrastructure as Code (IaC)";
        chapter22.imageUrl = "https://cdn-icons-png.flaticon.com/512/11632/11632029.png";

        Chapter chapter23 = new Chapter();
        chapter23.id = 23;
        chapter23.chapterName = "Monitoring and Logging";
        chapter23.imageUrl = "https://cdn-icons-png.flaticon.com/512/2942/2942789.png";

        devops.chapters.add(chapter19);
        devops.chapters.add(chapter20);
        devops.chapters.add(chapter21);
        devops.chapters.add(chapter22);
        devops.chapters.add(chapter23);

        subjects.add(website);
        subjects.add(mobile);
        subjects.add(data);
        subjects.add(ml);
        subjects.add(devops);

        return subjects;
    }
}