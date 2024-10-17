package com.example.banco_dafrhe

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.banco_dafrhe.databinding.ActivityLoginBinding
import com.example.banco_dafrhe.databinding.ActivityMainBinding

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {

        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.inicioSesion.setOnClickListener{
            if (binding.textUsuario.text.toString().isNotEmpty() && binding.textContraseA.text.toString().isNotEmpty()) {

                val intent = Intent(this, MainActivity::class.java)
                intent.putExtra("Usuario", binding.textUsuario.text.toString())
                startActivity(intent)

            } else {

                binding.errorText.text = "Por favor introduce Usuario y Contraseña"

            }
        }

        binding.salir.setOnClickListener{

            finishAffinity()

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