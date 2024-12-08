package com.example.banco_dafrhe.activities

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.banco_dafrhe.R
import com.example.banco_dafrhe.databinding.ActivityPosicionGlobalBinding
import com.example.banco_dafrhe.fragments.AccountListener
import com.example.banco_dafrhe.fragments.AccountsFragment
import com.example.banco_dafrhe.fragments.AccountsMovementFragment
import com.example.banco_dafrhe.pojo.Cliente
import com.example.banco_dafrhe.pojo.Cuenta

class GlobalPositionActivity : AppCompatActivity(), AccountListener{

   private lateinit var binding: ActivityPosicionGlobalBinding

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        binding = ActivityPosicionGlobalBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val cliente: Cliente? = if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.TIRAMISU) {
            intent.getSerializableExtra("Cliente", Cliente::class.java)
        } else {
            @Suppress("DEPRECATION")
            intent.getSerializableExtra("Cliente") as? Cliente
        }


        val frgCuenta: AccountsFragment = AccountsFragment.newInstance(cliente)

        supportFragmentManager.beginTransaction()
            .replace(R.id.fragmentContainerAcc, frgCuenta)
            .commit()

        frgCuenta.setCuentasListener(this)


        val mainView = findViewById<View>(R.id.main)
        if (mainView != null) {
            ViewCompat.setOnApplyWindowInsetsListener(mainView) { v, insets ->
                val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
                v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
                insets

            }
        } else {
            Log.e("GlobalPositionActivity", "mainView no se ha inicializado correctamente.")
        }


    }

    override fun onCuentaSeleccionada(cuenta: Cuenta) {

        var existeMovimiento = findViewById<View>(R.id.fragmentContainerMovimientos) != null
        if (existeMovimiento) {

            val movementFragment: AccountsMovementFragment = AccountsMovementFragment.newInstance(cuenta)
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragmentContainerMovimientos, movementFragment)
                .commit()

        } else {

            val int = Intent(this, GlobalPositionDetailActivity::class.java)
            int.putExtra("Cuenta", cuenta)
            startActivity(int)

        }
    }


}