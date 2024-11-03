package com.example.banco_dafrhe

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.banco_dafrhe.databinding.ActivityPasswordChangeBinding

class PasswordChange : AppCompatActivity() {

    private lateinit var binding: ActivityPasswordChangeBinding

    override fun onCreate(savedInstanceState: Bundle?) {

        binding = ActivityPasswordChangeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //Botón cancelar (lleva a la pantalla principal)

        binding.btCancelar.setOnClickListener {

            val int = Intent(this, MainActivity::class.java)
            startActivity(int)
            finish()

        }

        //Funcion comprobación
        //Si algún campo está vacío, muestra un snackbar con un mensaje de error

        binding.textAC.onFocusChangeListener = View.OnFocusChangeListener { v, hasFocus ->

            if (!hasFocus) {

                val userInput = binding.textAC.text.toString().trim()

                if (userInput.isEmpty()) {

                    binding.layoutAC.error = getString(R.string.errorAC)

                } else {

                    binding.layoutAC.error = null

                }

            }

        }

        binding.textCN.onFocusChangeListener = View.OnFocusChangeListener { v, hasFocus ->

            if (!hasFocus) {

                val userInput = binding.textCN.text.toString().trim()

                if (userInput.isEmpty()) {

                    binding.layoutCN.error = getString(R.string.errorCN)

                } else {

                    binding.layoutCN.error = null

                }

            }

        }

        binding.textCC.onFocusChangeListener = View.OnFocusChangeListener { v, hasFocus ->

            if (!hasFocus) {

                val userInput = binding.textCC.text.toString().trim()

                if (userInput.isEmpty()) {

                    binding.layoutCC.error = getString(R.string.errorCC)

                } else {

                    binding.layoutCC.error = null

                }

            }

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