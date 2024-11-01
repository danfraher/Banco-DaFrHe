package com.example.banco_dafrhe

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.banco_dafrhe.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val usuarioRecibido = intent.getStringExtra("Usuario")

        binding.textoBienvenida.text = "${getString(R.string.Bienvenida)} \n \n $usuarioRecibido"

        binding.btExit.setOnClickListener {

            val int = Intent(this, LoginActivity::class.java)
            startActivity(int)
            finish()

        }

        binding.btCambiarPss.setOnClickListener {

            val int2 = Intent(this, PasswordChange::class.java)
            startActivity(int2)

        }

        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

    }

}