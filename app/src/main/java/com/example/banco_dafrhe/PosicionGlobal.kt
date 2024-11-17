package com.example.banco_dafrhe

import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.banco_dafrhe.bd.MiBancoOperacional
import com.example.banco_dafrhe.databinding.ActivityPosicionGlobalBinding
import com.example.banco_dafrhe.pojo.Cliente
import com.example.banco_dafrhe.pojo.Cuenta

class PosicionGlobal : AppCompatActivity() {

    private lateinit var binding: ActivityPosicionGlobalBinding

    override fun onCreate(savedInstanceState: Bundle?) {

        binding = ActivityPosicionGlobalBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val usuarioRecibido = intent.getSerializableExtra("Cliente")

        val mbo: MiBancoOperacional? = MiBancoOperacional.getInstance(this)

        var cliente = Cliente()
        cliente.setNif(usuarioRecibido.toString())

        val listaCuentas: ArrayList<Cuenta>? = mbo?.getCuentas(cliente) as ArrayList<Cuenta>?

        Log.e("LISTACUENTAS", listaCuentas?.size.toString())
        // Verificar si la lista no es nula
        if (listaCuentas != null) {
            // Usar un bucle for para recorrer la lista
            for (cuenta in listaCuentas) {
                // Aquí puedes acceder a las propiedades de la cuenta y mostrar su contenido
                binding.textAccs.append("Número de cuenta: ${cuenta.getNumeroCuenta()} ")
                binding.textAccs.append(" Saldo: ${cuenta.getSaldoActual()}\n\n")

            }
        } else {
            println("La lista de cuentas es nula")
        }
        Log.e("LISTACUENTA", listaCuentas?.get(0).toString())

        super.onCreate(savedInstanceState)
        enableEdgeToEdge()



        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}