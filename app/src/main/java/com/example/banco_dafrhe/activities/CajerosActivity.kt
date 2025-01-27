package com.example.banco_dafrhe.activities

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.banco_dafrhe.Adapter.CajeroAdapter
import com.example.banco_dafrhe.R
import com.example.banco_dafrhe.bd.CajeroDatabase
import com.example.banco_dafrhe.databinding.ActivityCajerosBinding
import com.example.banco_dafrhe.pojo.CajeroEntity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CajerosActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCajerosBinding
    private lateinit var cajeroAdapter: CajeroAdapter
    private lateinit var database: CajeroDatabase
    private var cajeroList = mutableListOf<CajeroEntity>()

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        binding = ActivityCajerosBinding.inflate(layoutInflater)
        setContentView(binding.root)

        database = CajeroDatabase.getInstance(this)

        binding.rvCajeros.layoutManager = LinearLayoutManager(this)

        cajeroAdapter = CajeroAdapter(cajeroList, { cajero ->
            val intent = Intent(this, FormularioCajeroActivity::class.java)
            intent.putExtra("cajeroId", cajero.id)
            startActivity(intent)
        }, { cajero ->
            eliminarCajero(cajero)
        })

        binding.rvCajeros.adapter = cajeroAdapter

        cargarCajeros()

        binding.btnAgregarCajero.setOnClickListener {

            val intent = Intent(this, FormularioCajeroActivity::class.java)
            startActivity(intent)

        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

    }

    private fun cargarCajeros() {

        CoroutineScope(Dispatchers.IO).launch {
            try {
                val cajeros = database.cajeroDAO().getAllCajeros()
                runOnUiThread {

                    cajeroList.clear()
                    cajeroList.addAll(cajeros)
                    cajeroAdapter.notifyDataSetChanged()

                }
            } catch (e: Exception){
                e.printStackTrace()
                runOnUiThread {
                    Toast.makeText(this@CajerosActivity, "Error al cargar los cajeros", Toast.LENGTH_SHORT).show()
                }
            }
        }

    }

    private fun eliminarCajero(cajero: CajeroEntity){

        CoroutineScope(Dispatchers.IO).launch {

            database.cajeroDAO().deleteCajero(cajero)
            runOnUiThread {
                cajeroList.remove(cajero)
                cajeroAdapter.notifyDataSetChanged()
            }

        }

    }

}