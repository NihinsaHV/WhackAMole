package com.example.labexam03

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class HomePage : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContentView(R.layout.activity_home_page)

        val startBtn: Button = findViewById(R.id.startbtn)
        val HighScore: Button = findViewById(R.id.highestScorebtn)
        startBtn.setOnClickListener {
            val intent = Intent(this,MainActivity::class.java)
            startActivity(intent)
            finish()
        }

        HighScore.setOnClickListener {
            val intent = Intent(this,HighScorePage::class.java)
            startActivity(intent)
            finish()
        }
    }
}