package com.example.banco_dafrhe

import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.banco_dafrhe.bd.MiBancoOperacional
import com.example.banco_dafrhe.databinding.ActivityMovimientosBinding
import com.example.banco_dafrhe.pojo.Cliente
import com.example.banco_dafrhe.pojo.Cuenta
import com.example.banco_dafrhe.pojo.Movimiento

class Movimientos : AppCompatActivity() {

    private lateinit var binding: ActivityMovimientosBinding

    override fun onCreate(savedInstanceState: Bundle?) {

        binding = ActivityMovimientosBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val usuarioRecibido = intent.getSerializableExtra("Cliente")
        val mbo: MiBancoOperacional? = MiBancoOperacional.getInstance(this)

        var cliente = Cliente()
        cliente.setNif(usuarioRecibido.toString())

        val listaCuentas: ArrayList<Cuenta>? = mbo?.getCuentas(cliente) as ArrayList<Cuenta>?

        val listaMovimientos: ArrayList<Movimiento>? = mbo?.getMovimientos(listaCuentas?.get(0)) as ArrayList<Movimiento>?
        Log.e("LISTAMOVIMIENTOS", listaMovimientos?.size.toString())
        if (listaMovimientos != null) {
            // Usar un bucle for para recorrer la lista
            for (movimiento in listaMovimientos) {
                // Aquí puedes acceder a las propiedades de la cuenta y mostrar su contenido
                binding.textAccs.append("Movimiento: ${movimiento.toString()}\n\n")
            }
        } else {
            println("La lista de movimientos es nula")
        }
        Log.e("LISTAMOVIMIENTO", listaMovimientos?.get(0).toString())

        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}