package com.example.banco_dafrhe.activities

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.banco_dafrhe.R
import com.example.banco_dafrhe.bd.MiBancoOperacional
import com.example.banco_dafrhe.databinding.ActivityLoginBinding
import com.example.banco_dafrhe.pojo.Cliente


class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //Función botón Inicio
        //Si el usuario y la contraseña no están vacíos, pasa a la pantalla principal

        binding.inicioSesion.setOnClickListener {

            val mbo: MiBancoOperacional? = MiBancoOperacional.getInstance(this)

            val cliente = Cliente()
            cliente.setNif(binding.textUsuario.text.toString())
            cliente.setClaveSeguridad(binding.textContraseA.text.toString())

            // Logueamos al cliente
            val clienteLogeado = mbo?.login(cliente) ?: -1
            if(clienteLogeado == -1) {
                Toast.makeText(this, "Datos incorrectos", Toast.LENGTH_SHORT).show()
            }else{
                val intent = Intent(this, MainActivity::class.java)
                intent.putExtra("Cliente", clienteLogeado)
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

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}