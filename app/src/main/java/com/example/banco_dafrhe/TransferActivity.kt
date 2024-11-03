package com.example.banco_dafrhe

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.PopupWindow
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.banco_dafrhe.databinding.ActivityTransferBinding
import com.example.banco_dafrhe.databinding.ToastPersonalizadoBinding

class TransferActivity : AppCompatActivity() {

    private lateinit var binding: ActivityTransferBinding

    override fun onCreate(savedInstanceState: Bundle?) {

        binding = ActivityTransferBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //Declarar variables para los arrays de strings de spinner

        val cuentasBancarias = resources.getStringArray(R.array.cuentasBancarias)
        val divisas = resources.getStringArray(R.array.divisa)

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

        //Adaptador para los spinners

        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, cuentasBancarias)

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        //Asignar adaptadores a los spinners

        binding.spinnerAcc.adapter = adapter

        binding.spinnerCP.adapter = adapter

        //Adaptador para el spinner de divisas

        val adapter2 = ArrayAdapter(this, android.R.layout.simple_spinner_item, divisas)

        //Asignar adaptador al spinner de divisas

        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        binding.spinnerDivisa.adapter = adapter2

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

            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()

        }

        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}