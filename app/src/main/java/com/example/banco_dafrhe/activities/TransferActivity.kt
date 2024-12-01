package com.example.banco_dafrhe.activities

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.banco_dafrhe.R
import com.example.banco_dafrhe.databinding.ActivityTransferBinding
import com.example.banco_dafrhe.databinding.ToastPersonalizadoBinding
import com.example.banco_dafrhe.bd.MiBancoOperacional
import com.example.banco_dafrhe.pojo.Cliente
import com.example.banco_dafrhe.pojo.Cuenta

class TransferActivity : AppCompatActivity() {

    private lateinit var binding: ActivityTransferBinding

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityTransferBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //Inicializar MiBancoOperacional
        val mbo: MiBancoOperacional? = MiBancoOperacional.getInstance(this)

        //Declarar variables para los arrays de strings de spinner

        val cliente = intent.getSerializableExtra("Cliente") as? Cliente;

        if (cliente == null || cliente.getId() == 0){
            Log.e("TransferActivity", "Error: El cliente no llegó correctamente a TransferActivity.")
            Toast.makeText(this, "Error al recibir el cliente", Toast.LENGTH_SHORT).show()
            finish()
            return
        }



        //Listener para el radio button
        //Si está seleccionado 1 muestra el spinner y oculta el editText si no muestra el editText y oculta el spinner

        binding.radioGroup.setOnCheckedChangeListener { group, checkedId ->

            when (checkedId) {
                binding.radioBCP.id -> {

                    binding.spinnerCP.visibility = View.VISIBLE
                    binding.edCA.visibility = View.GONE
                    binding.edCA.text?.clear()

                }
                binding.radoBCA.id -> {

                    binding.edCA.visibility = View.VISIBLE
                    binding.spinnerCP.visibility = View.GONE
                    binding.spinnerCP.setSelection(0)

                }
            }

        }

        //Obtener las cuentas del cliente desde la base de datos
        val listaCuentas = mbo?.getCuentas(cliente) as? List<Cuenta> ?: emptyList()

        //validar obtencion de cuentas
        if (listaCuentas.isEmpty()) {
            Toast.makeText(this, "No se encontraron cuentas asociadas al cliente", Toast.LENGTH_SHORT).show()
            finish()
            return
        }

        //adaptador cuentas propias

        val cuentasAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, listaCuentas.map { cuenta -> "${ cuenta.getNumeroCuenta()}"})
        cuentasAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        //asignar adaptador a spinner

        binding.spinnerAcc.adapter = cuentasAdapter
        binding.spinnerCP.adapter = cuentasAdapter

        //declarar divisas

        val divisas = resources.getStringArray(R.array.divisa)

        //Adaptador para el spinner de divisas

        val adapterDivisas = ArrayAdapter(this, android.R.layout.simple_spinner_item, divisas)

        //Asignar adaptador al spinner de divisas

        adapterDivisas.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        binding.spinnerDivisa.adapter = adapterDivisas

        //Función botón enviar
        //Recoge toda la información dada antes por los spinners, editTexts, checkbox... y la muestra en un toast personalizado

        binding.btnEnviar.setOnClickListener{

            val cuentaOrigen = binding.spinnerAcc.selectedItem.toString()

            val cuentaDestino = if (binding.radioBCP.isChecked) {

                binding.spinnerCP.selectedItem.toString()

            } else {

                binding.edCA.text.toString()

            }

            val cantidad = binding.edCant.text.toString()

            val divisa = binding.spinnerDivisa.selectedItem.toString()

            val justificante = if (binding.cbJustificante.isChecked) "Si" else "No"

            val mensaje = "Cuenta Origen:  $cuentaOrigen\n" +
                    "Cuenta Destino: $cuentaDestino\n" +
                    "Importe: $cantidad $divisa\n" +
                    "Justificante: $justificante"

            val toastBinding = ToastPersonalizadoBinding.inflate(layoutInflater)

            toastBinding.toastText.text = mensaje

            val toast = Toast(applicationContext).apply {

                duration = Toast.LENGTH_LONG
                view = toastBinding.root

            }
            toast.show()

        }

        //Función botón cancelar
        //Vuelve a la pantalla principal a la vez que borra la información dada

        binding.btnCancelar.setOnClickListener{

            finish()

        }
        
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}