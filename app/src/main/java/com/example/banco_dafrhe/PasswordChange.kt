package com.example.banco_dafrhe

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.banco_dafrhe.bd.MiBancoOperacional
import com.example.banco_dafrhe.databinding.ActivityLoginBinding
import com.example.banco_dafrhe.databinding.ActivityPasswordChangeBinding
import com.example.banco_dafrhe.pojo.Cliente

class PasswordChange : AppCompatActivity() {

    private lateinit var binding: ActivityPasswordChangeBinding

    override fun onCreate(savedInstanceState: Bundle?) {

        binding = ActivityPasswordChangeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //Botón cancelar (lleva a la pantalla principal)
        val mbo: MiBancoOperacional? = MiBancoOperacional.getInstance(this)
        val usuarioRecibido = intent.getSerializableExtra("Cliente")

        binding.btCP.setOnClickListener {

            var cliente = Cliente()
            cliente?.setNif(usuarioRecibido.toString())
            cliente?.setClaveSeguridad(binding.textAC.text.toString())
            val clienteLogeado = mbo?.login(cliente) ?: -1
            if(clienteLogeado == -1) {
                Toast.makeText(this, "Contraseña actual incorrecta", Toast.LENGTH_SHORT).show()
            }else{

                if (binding.textCN.text.toString() == binding.textCC.text.toString()) {

                    cliente.setClaveSeguridad(binding.textCN.text.toString())
                    mbo?.changePassword(cliente)
                    Toast.makeText(this, "Contraseña cambiada correctamente", Toast.LENGTH_SHORT).show()
                    val int = Intent(this, LoginActivity::class.java)
                    startActivity(int)
                    finish()

                } else{

                    Toast.makeText(this, "Las contraseñas no coinciden", Toast.LENGTH_SHORT).show()

                }

            }

        }

        binding.btCancelar.setOnClickListener {

            val int = Intent(this, MainActivity::class.java)
            int.putExtra("Cliente", usuarioRecibido.toString())
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