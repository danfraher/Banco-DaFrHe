package com.example.banco_dafrhe.activities

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.banco_dafrhe.R
import com.example.banco_dafrhe.databinding.ActivityMainBinding
import com.example.banco_dafrhe.pojo.Cliente

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //Recibe el usuario de la pantalla de login

        val usuarioRecibido = intent.getSerializableExtra("Cliente") as? Cliente

        if (usuarioRecibido == null || usuarioRecibido.getId() == 0){

            Toast.makeText(this, "Error al recibir el cliente", Toast.LENGTH_SHORT).show()
            finish()
            return

        }

        //Muestra el usuario en la pantalla principal

        binding.textoBienvenida.text = "${getString(R.string.Bienvenida)} \n \n ${usuarioRecibido.getNif().toString()}"

        //Botones de la pantalla principal

        //Botón Transferencias (lleva a la pantalla transferencias)

        binding.btTransfer.setOnClickListener {

            val int = Intent(this, TransferActivity::class.java)
            int.putExtra("Cliente", usuarioRecibido)
            startActivity(int)

        }

        //Botón Cambiar Contraseña (lleva a la pantalla de cambiar contraseña)

        binding.btCambiarPss.setOnClickListener {

            val int = Intent(this, PasswordChange::class.java)
            int.putExtra("Cliente", usuarioRecibido)
            startActivity(int)

        }

        //Botón Salir (lleva a la pantalla de login)

        binding.btExit.setOnClickListener {

            finish()

        }

        binding.btGlobal.setOnClickListener {

            Log.d("MainActivity", "Enviando cliente: $usuarioRecibido")
            val int = Intent(this, GlobalPositionActivity::class.java)
            int.putExtra("Cliente", usuarioRecibido)
            startActivity(int)

        }

        binding.btMovimientos.setOnClickListener {

            val int = Intent(this, Movimientos::class.java)
            int.putExtra("Cliente", usuarioRecibido)
            startActivity(int)

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