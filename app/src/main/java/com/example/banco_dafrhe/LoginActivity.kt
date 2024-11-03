package com.example.banco_dafrhe

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.banco_dafrhe.databinding.ActivityLoginBinding
import com.google.android.material.snackbar.Snackbar


class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {

        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //Función botón Inicio
        //Si el usuario y la contraseña no están vacíos, pasa a la pantalla principal

        binding.inicioSesion.setOnClickListener {

            if (binding.textUsuario.text.toString().isNotEmpty() && binding.textContraseA.text.toString().isNotEmpty()) {

                val intent = Intent(this, MainActivity::class.java)
                intent.putExtra("Usuario", binding.textUsuario.text.toString())
                startActivity(intent)

            }

        }

        //Función comprobación
        //Si el usuario o la contraseña están vacíos, muestra un snackbar con un mensaje de error

        binding.textUsuario.onFocusChangeListener = View.OnFocusChangeListener { v, hasFocus ->

            if (!hasFocus) {

                val userInput = binding.textUsuario.text.toString().trim()

                if (userInput.isEmpty()) {

                    binding.layoutUsuario.error = getString(R.string.errorLI)

                } else {

                    binding.layoutUsuario.error = null

                }

            }

        }

        binding.textContraseA.onFocusChangeListener = View.OnFocusChangeListener { v, hasFocus ->

            if (!hasFocus) {

               val userInput = binding.textContraseA.text.toString().trim()

               if (userInput.isEmpty()) {

                   binding.layoutContraseA.error = getString(R.string.errorPS)

               } else {

                   binding.layoutContraseA.error = null

               }

            }

        }

        //Función botón salir
        //Finaliza la aplicación

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