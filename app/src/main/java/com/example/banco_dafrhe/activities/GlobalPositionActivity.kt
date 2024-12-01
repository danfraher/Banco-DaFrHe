package com.example.banco_dafrhe.activities

import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.banco_dafrhe.Adapter.AccountsListener
import com.example.banco_dafrhe.Adapter.CuentaAdapter
import com.example.banco_dafrhe.R
import com.example.banco_dafrhe.bd.MiBancoOperacional
import com.example.banco_dafrhe.databinding.ActivityGlobalPositionDetailBinding
import com.example.banco_dafrhe.databinding.ActivityPosicionGlobalBinding
import com.example.banco_dafrhe.fragments.AccountsFragment
import com.example.banco_dafrhe.pojo.Cliente
import com.example.banco_dafrhe.pojo.Cuenta

class GlobalPositionActivity : AppCompatActivity(){

   private lateinit var binding: ActivityPosicionGlobalBinding

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        binding = ActivityPosicionGlobalBinding.inflate(layoutInflater)
        setContentView(binding.root)

        Log.d("GlobalPositionActivity", "Intent recibido: $intent")
        val cliente = intent.getSerializableExtra("Cliente") as? Cliente
        Log.d("GlobalPositionActivity", "Cliente recibido: $cliente")

        if (cliente == null) {
            Log.e("GlobalPositionActivity", "Cliente es nulo o no fue pasado correctamente")
            finish() // Finaliza la actividad si el cliente no es válido
            return
        }

        val accountsFragment = AccountsFragment.newInstance(cliente)


            supportFragmentManager.beginTransaction()
                .replace(binding.fragmentContainer.id, accountsFragment)
                .commit()



    }

}