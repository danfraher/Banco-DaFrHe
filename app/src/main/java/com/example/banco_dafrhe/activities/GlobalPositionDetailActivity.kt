package com.example.banco_dafrhe.activities

import android.os.Bundle
import android.os.PersistableBundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.banco_dafrhe.R
import com.example.banco_dafrhe.bd.MiBancoOperacional
import com.example.banco_dafrhe.databinding.ActivityGlobalPositionDetailBinding
import com.example.banco_dafrhe.fragments.AccountsMovementFragment
import com.example.banco_dafrhe.pojo.Cuenta
import com.example.banco_dafrhe.pojo.Movimiento

class GlobalPositionDetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityGlobalPositionDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        binding = ActivityGlobalPositionDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val cuenta = intent.getSerializableExtra("Cuenta") as Cuenta
        val movementsFragment = AccountsMovementFragment.newInstance(cuenta)
        supportFragmentManager.beginTransaction()
            .replace(binding.fragmentContainer.id, movementsFragment)
            .commit()

    }

}