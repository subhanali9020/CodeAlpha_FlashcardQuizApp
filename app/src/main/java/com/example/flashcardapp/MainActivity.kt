package com.example.flashcardapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.flashcardapp.ui.FlashcardScreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            // Agar koi Theme hai to yahan wrap karein, warna direct screen call karein
            FlashcardScreen()
        }
    }
}