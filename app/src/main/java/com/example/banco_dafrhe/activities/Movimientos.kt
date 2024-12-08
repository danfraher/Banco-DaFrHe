package com.example.banco_dafrhe.activities

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.banco_dafrhe.Adapter.MovimientoAdapter
import com.example.banco_dafrhe.R
import com.example.banco_dafrhe.bd.MiBancoOperacional
import com.example.banco_dafrhe.databinding.ActivityMovimientosBinding
import com.example.banco_dafrhe.pojo.Cliente
import com.example.banco_dafrhe.pojo.Cuenta
import com.example.banco_dafrhe.pojo.Movimiento

class Movimientos : AppCompatActivity() {

    private lateinit var binding: ActivityMovimientosBinding
    private lateinit var movimientoAdapter: MovimientoAdapter

    override fun onCreate(savedInstanceState: Bundle?) {

        binding = ActivityMovimientosBinding.inflate(layoutInflater)
        setContentView(binding.root)

        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val usuarioRecibido = intent.getSerializableExtra("Cliente") as? Cliente

        if (usuarioRecibido == null || usuarioRecibido.getId() == 0) {

            Toast.makeText(this, "Error al recuperar el cliente", Toast.LENGTH_SHORT).show()
            finish()
            return

        }

        val mbo: MiBancoOperacional? = MiBancoOperacional.getInstance(this)

        val listaCuentas = mbo?.getCuentas(usuarioRecibido) as? List<Cuenta> ?: emptyList()

        if (listaCuentas.isEmpty()) {
            Toast.makeText(this, "No se encontraron cuentas asociadas al cliente", Toast.LENGTH_SHORT).show()
            finish()
            return
        }

        val cuentasAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, listaCuentas.map { cuenta -> cuenta.getNumeroCuenta() })
        cuentasAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        binding.spinnerAcc.adapter = cuentasAdapter
        binding.rvMovimientos.layoutManager = LinearLayoutManager(this)
        binding.rvMovimientos.adapter = movimientoAdapter

        binding.spinnerAcc.setOnItemSelectedListener(object : AdapterView.OnItemSelectedListener {

            override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
                val cuentaSeleccionada = listaCuentas[position]
                val listaMovimientos = mbo?.getMovimientos(cuentaSeleccionada) as? List<Movimiento> ?: emptyList()

                //movimientoAdapter.updateData(listaMovimientos)

            }

            override fun onNothingSelected(parent: AdapterView<*>) {

            }

        })

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}