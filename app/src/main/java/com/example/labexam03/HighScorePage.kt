package com.example.labexam03

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class HighScorePage : AppCompatActivity() {
    lateinit var highestScoreValue: TextView
    lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_high_score_page)

        sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
        highestScoreValue = findViewById(R.id.highestScoreValue)
        // Display the highest score
        displayHighestScore()

        val GameStartButton: Button = findViewById(R.id.ScorePageStartbtn)
        val HomePageBtn: Button = findViewById(R.id.highScoreTohome)


        GameStartButton.setOnClickListener {
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
    private fun displayHighestScore() {
        val highestScore = sharedPreferences.getInt("highestScore", 0)
        highestScoreValue.text = highestScore.toString()
    }
}