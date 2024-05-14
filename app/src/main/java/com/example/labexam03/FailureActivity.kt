package com.example.labexam03

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity


class FailureActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_failure)
        // Retrieve the score from the intent
        val score = intent.getIntExtra("score", 0)

        // Set the score to the TextView
        val gameOverTextView: TextView = findViewById(R.id.CurrentScore)
        gameOverTextView.text = getString(R.string.score, score)
        val playagainbtn: Button = findViewById(R.id.TryAginbtn)
        val HomePageBtn: Button = findViewById(R.id.ToMainMenu)
        playagainbtn.setOnClickListener {
            val intent = Intent(this,MainActivity::class.java)
            startActivity(intent)
            finish()
        }

        HomePageBtn.setOnClickListener {
            val intent = Intent(this,HomePage::class.java)
            startActivity(intent)
            finish()
        }
    }
}