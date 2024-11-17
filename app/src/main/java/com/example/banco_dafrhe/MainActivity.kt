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

        //Recibe el usuario de la pantalla de login

        val usuarioRecibido = intent.getSerializableExtra("Cliente")

        //Muestra el usuario en la pantalla principal

        binding.textoBienvenida.text = "${getString(R.string.Bienvenida)} \n \n $usuarioRecibido"

        //Botones de la pantalla principal

        //Botón Transferencias (lleva a la pantalla transferencias)

        binding.btTransfer.setOnClickListener {

            val int = Intent(this, TransferActivity::class.java)
            int.putExtra("Cliente", usuarioRecibido.toString())
            startActivity(int)
            startActivity(int)

        }

        //Botón Cambiar Contraseña (lleva a la pantalla de cambiar contraseña)

        binding.btCambiarPss.setOnClickListener {

            val int = Intent(this, PasswordChange::class.java)
            int.putExtra("Cliente", usuarioRecibido.toString())
            startActivity(int)
            finish()

        }

        //Botón Salir (lleva a la pantalla de login)

        binding.btExit.setOnClickListener {

            val int = Intent(this, LoginActivity::class.java)
            startActivity(int)
            finish()

        }

        binding.btGlobal.setOnClickListener {

            val int = Intent(this, PosicionGlobal::class.java)
            int.putExtra("Cliente", usuarioRecibido.toString())
            startActivity(int)
            finish()

        }

        binding.btMovimientos.setOnClickListener {

            val int = Intent(this, Movimientos::class.java)
            int.putExtra("Cliente", usuarioRecibido.toString())
            startActivity(int)
            finish()

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