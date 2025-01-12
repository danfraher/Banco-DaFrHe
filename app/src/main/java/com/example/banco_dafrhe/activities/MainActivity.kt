package com.example.banco_dafrhe.activities

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.banco_dafrhe.R
import com.example.banco_dafrhe.databinding.ActivityMainBinding
import com.example.banco_dafrhe.pojo.Cliente
import com.google.android.material.navigation.NavigationView

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

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

        val drawerLayout = binding.drawerLayout
        val navView = binding.navView

        val toggle = ActionBarDrawerToggle(
            this, drawerLayout, binding.bottomAppBar, R.string.open_drawer, R.string.close_drawer
        )
        drawerLayout?.addDrawerListener(toggle)
        toggle.syncState()

        navView?.setNavigationItemSelectedListener(this)

        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.drawer_layout)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.nav_global -> binding.btGlobal.performClick()
            R.id.nav_movements -> binding.btMovimientos.performClick()
            R.id.nav_transfer -> binding.btTransfer.performClick()
            R.id.nav_settings -> {
                openSettings(view = View(this))
            }
        }
        binding.drawerLayout?.closeDrawer(GravityCompat.START)
        return true
    }

    fun openSettings(view: View) {
        val intent = Intent(this, SettingsActivity::class.java)
        startActivity(intent)
    }

}