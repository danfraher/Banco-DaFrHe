package com.example.banco_dafrhe.activities

import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.banco_dafrhe.R
import com.example.banco_dafrhe.bd.CajeroDatabase
import com.example.banco_dafrhe.databinding.ActivityFormularioCajeroBinding
import com.example.banco_dafrhe.pojo.CajeroEntity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class FormularioCajeroActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFormularioCajeroBinding
    private lateinit var database: CajeroDatabase
    private var cajeroId: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        binding = ActivityFormularioCajeroBinding.inflate(layoutInflater)
        setContentView(binding.root)

        database = CajeroDatabase.getInstance(this)

        cajeroId = intent.getIntExtra("cajeroId", 0)
        if (cajeroId != 0) {
            cargarCajero(cajeroId)
        }

        binding.btnGuardar.setOnClickListener {

            guardarCajero()

        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

    }

    private fun cargarCajero(id: Int) {

        CoroutineScope(Dispatchers.IO).launch {

            val cajero = database.cajeroDAO().getAllCajeros().find { it.id == id }
            cajero?.let {

                runOnUiThread {

                    binding.etNombre.setText(it.nombre)
                    binding.etDireccion.setText(it.direccion)
                    binding.etLatitud.setText(it.latitud.toString())
                    binding.etLongitud.setText(it.longitud.toString())
                    binding.etZoom.setText(it.zoom)

                }

            }


        }

    }

    private fun guardarCajero() {

        val nombre = binding.etNombre.text.toString()
        val direccion = binding.etDireccion.text.toString()
        val latitud = binding.etLatitud.text.toString().toDouble()
        val longitud = binding.etLongitud.text.toString().toDouble()
        val zoom = binding.etZoom.text.toString()

        if (nombre.isBlank() || direccion.isBlank() || latitud == null || longitud == null || zoom.isBlank()) {

            Toast.makeText(this, "Por favor, complete todos los campos", Toast.LENGTH_SHORT).show()
            return

        }

        val cajero = CajeroEntity(

            id = cajeroId,
            nombre = nombre,
            direccion = direccion,
            latitud = latitud,
            longitud = longitud,
            zoom = zoom

        )

        CoroutineScope(Dispatchers.IO).launch {

            if (cajeroId == 0) {

                database.cajeroDAO().addCajero(cajero)

            } else {

                database.cajeroDAO().updateCajero(cajero)

            }

            runOnUiThread {

                Toast.makeText(this@FormularioCajeroActivity, "Cajero guardado correctamente", Toast.LENGTH_SHORT).show()
                finish()

            }

        }

    }

}