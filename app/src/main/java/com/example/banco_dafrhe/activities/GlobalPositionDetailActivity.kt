package com.example.banco_dafrhe.activities

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.banco_dafrhe.R
import com.example.banco_dafrhe.databinding.ActivityGlobalPositionDetailBinding
import com.example.banco_dafrhe.fragments.AccountsMovementFragment
import com.example.banco_dafrhe.pojo.Cuenta

class GlobalPositionDetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityGlobalPositionDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        binding = ActivityGlobalPositionDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val cuenta: Cuenta? = if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.TIRAMISU) {
            intent.getSerializableExtra("Cuenta", Cuenta::class.java)
        } else {
            @Suppress("DEPRECATION")
            intent.getSerializableExtra("Cuenta") as? Cuenta
        }

        mostrarMovimientos(-1, cuenta)

        binding.bottomNavigation.setOnItemSelectedListener { menuItem ->
            val tipoMovimiento = when (menuItem.itemId) {
                R.id.nav_todos -> -1
                R.id.nav_tipo_0 -> 0
                R.id.nav_tipo_1 -> 1
                R.id.nav_tipo_2 -> 2
                else -> -1
            }
            mostrarMovimientos(tipoMovimiento, cuenta)
            true
        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

    }

    private fun mostrarMovimientos(tipo: Int, cuenta: Cuenta?) {
        val movementsFragment = AccountsMovementFragment.newInstance(cuenta, tipo)
        supportFragmentManager.beginTransaction()
            .replace(binding.fragmentContainerMovimientos.id, movementsFragment)
            .commit()
    }

}