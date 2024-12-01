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
import com.example.banco_dafrhe.databinding.ActivityPasswordChangeBinding
import com.example.banco_dafrhe.pojo.Cliente

class PasswordChange : AppCompatActivity() {

    private lateinit var binding: ActivityPasswordChangeBinding

    override fun onCreate(savedInstanceState: Bundle?) {

        binding = ActivityPasswordChangeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //Botón cancelar (lleva a la pantalla principal)
        val mbo: MiBancoOperacional? = MiBancoOperacional.getInstance(this)
        val usuarioRecibido = intent.getSerializableExtra("Cliente") as? Cliente

        if (usuarioRecibido == null || usuarioRecibido.getId() == 0) {

            Toast.makeText(this, "Error al recuperar el cliente", Toast.LENGTH_SHORT).show()
            finish()
            return

        }

        binding.btCP.setOnClickListener {

            val contraActual = binding.textAC.text.toString()
            if (contraActual != usuarioRecibido.getClaveSeguridad()) {

                Toast.makeText(this, "Contraseña actual incorrecta", Toast.LENGTH_SHORT).show();
                return@setOnClickListener

            }

            val contraNueva = binding.textCN.text.toString()
            val confirmContraNueva = binding.textCC.text.toString()

            if (contraNueva != confirmContraNueva) {

                Toast.makeText(this, "Las contraseñas no coinciden", Toast.LENGTH_SHORT).show();
                return@setOnClickListener

            }

            usuarioRecibido.setClaveSeguridad(contraNueva)
            val actualizado = mbo?.changePassword(usuarioRecibido)

            if (actualizado == 1) {

                Toast.makeText(this, "Contraseña cambiada correctamente", Toast.LENGTH_SHORT).show();
                val int = Intent(this, LoginActivity::class.java)
                startActivity(int)
                finish()

            } else {

                Toast.makeText(this, "Error al cambiar la contraseña", Toast.LENGTH_SHORT).show()

            }

        }

        binding.btCancelar.setOnClickListener {

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