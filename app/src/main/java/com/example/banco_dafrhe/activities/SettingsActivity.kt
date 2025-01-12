package com.example.banco_dafrhe.activities
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.banco_dafrhe.fragments.SettingsFragment

class SettingsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportFragmentManager
            .beginTransaction()
            .replace(android.R.id.content, SettingsFragment())
            .commit()
    }
}