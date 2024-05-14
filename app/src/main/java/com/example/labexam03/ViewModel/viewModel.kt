package com.example.labexam03.ViewModel

import android.os.Bundle
import androidx.lifecycle.ViewModel

class ScoreViewModel : ViewModel() {
    var score: Int = 0

    companion object {
        private const val SCORE_KEY = "score_key"
    }

    fun saveInstanceState(outState: Bundle) {
        outState.putInt(SCORE_KEY, score)
    }

    fun restoreInstanceState(savedInstanceState: Bundle?) {
        savedInstanceState?.let {
            score = it.getInt(SCORE_KEY, 0)
        }
    }
}
