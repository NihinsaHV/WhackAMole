package com.example.labexam03

import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.os.CountDownTimer
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.GridLayout
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.labexam03.ViewModel.ScoreViewModel
import kotlin.random.Random

class MainActivity : AppCompatActivity() {

    private lateinit var scoreViewModel: ScoreViewModel
    private lateinit var tvScore: TextView
    private lateinit var gridLayout: GridLayout
    private val moleCount = 3// Change this for more or fewer moles
    private val gameTime = 45000L // 45 seconds in milliseconds
    private lateinit var sharedPreferences: SharedPreferences
    private var gamePaused = false
    private lateinit var pauseDialog: Dialog
    private var isNewGame = true
    private lateinit var countDownTimer: CountDownTimer
    private var isActive = false // Flag to track if MainActivity is active

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        scoreViewModel = ViewModelProvider(this).get(ScoreViewModel::class.java)
        scoreViewModel.restoreInstanceState(savedInstanceState)

        sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)

        tvScore = findViewById(R.id.tvScore)
        gridLayout = findViewById(R.id.gridLayout)

        isActive = true // MainActivity is active when created
        startGame()

        val stopButton: Button = findViewById(R.id.stopbtn)
        stopButton.setOnClickListener {
            val intent = Intent(this,HomePage::class.java)
            startActivity(intent)
            finish()
        }
        val pauseButton: Button = findViewById(R.id.pausebtn)
        pauseButton.setOnClickListener {
            pauseGame()
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        scoreViewModel.saveInstanceState(outState)
    }

    private fun pauseGame() {
        gamePaused = true
        gridLayout.removeAllViews()
        gridLayout.removeCallbacks { spawnmole() }
        showPauseDialog()
        isActive = false // MainActivity is no longer active when paused
    }

    private fun showPauseDialog() {
        val dialogView = LayoutInflater.from(this).inflate(R.layout.pause_dialog, null)
        val resumeButton = dialogView.findViewById<Button>(R.id.resumeButton)

        resumeButton.setOnClickListener {
            resumeGame()
            pauseDialog.dismiss()
        }

        pauseDialog = AlertDialog.Builder(this)
            .setView(dialogView)
            .setCancelable(false)
            .create()

        pauseDialog.show()
    }

    private fun resumeGame() {
        gamePaused = false
        isActive = true // MainActivity is active when resumed
        startGame()
    }

    private fun startGame() {
        if (isNewGame) { // Check if it's a new game
            isNewGame = false // Reset the flag
        }

        updateScore()

        countDownTimer = object : CountDownTimer(gameTime, 2000) { // 1 second interval
            override fun onTick(millisUntilFinished: Long) {
                if (!gamePaused) {
                    spawnmole()
                }
            }

            override fun onFinish() {
                if (!gamePaused && isActive) { // Check if the game is not paused and MainActivity is active
                    tvScore.text = getString(R.string.score, scoreViewModel.score)
                    gridLayout.removeAllViews()

                    // Only navigate to FailActivity if this is still the active activity
                    if (!isFinishing) {
                        val intent = Intent(this@MainActivity, FailureActivity::class.java)
                        intent.putExtra(
                            "score",
                            scoreViewModel.score
                        ) // Pass the score to FailActivity
                        startActivity(intent)
                        finish() // Finish the current activity
                    }
                }
            }

        }.start()
    }

    private fun updateScore() {
        tvScore.text = getString(R.string.score, scoreViewModel.score)
    }

    private fun spawnmole() {
        gridLayout.removeAllViews()

        val screenWidth = resources.displayMetrics.widthPixels
        val screenHeight = resources.displayMetrics.heightPixels

        val random = Random

        for (i in 0 until moleCount) {
            val mole = ImageView(this)
            mole.setImageResource(R.mipmap.mole_foreground)
            mole.tag = getString(R.string.mole_tag)
            mole.setOnClickListener { v -> onMoleWhacked(v) }

            val layoutParams = GridLayout.LayoutParams().apply {
                width = GridLayout.LayoutParams.WRAP_CONTENT
                height = GridLayout.LayoutParams.WRAP_CONTENT
                // Set random X and Y coordinates within screen boundaries
                leftMargin = random.nextInt(screenWidth - mole.width)
                topMargin = random.nextInt(screenHeight - mole.height)
            }
            mole.layoutParams = layoutParams

            gridLayout.addView(mole)
        }
    }

    private fun onMoleWhacked(view: View) {
        if (view.tag == getString(R.string.mole_tag)) {
            scoreViewModel.score++
            updateScore()
            saveHighestScore(scoreViewModel.score)
        }
        gridLayout.removeView(view)
    }

    private fun saveHighestScore(score: Int) {
        val highestScore = sharedPreferences.getInt("highestScore", 0)
        if (score > highestScore) {
            val editor = sharedPreferences.edit()
            editor.putInt("highestScore", score)
            editor.apply()
        }
    }
}
