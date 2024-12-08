package com.example.banco_dafrhe.fragments

import android.content.DialogInterface
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.banco_dafrhe.Adapter.MovimientoAdapter
import com.example.banco_dafrhe.Adapter.OnClickListener
import com.example.banco_dafrhe.R
import com.example.banco_dafrhe.bd.MiBancoOperacional
import com.example.banco_dafrhe.databinding.FragmentAccountsMovementBinding
import com.example.banco_dafrhe.pojo.Cuenta
import com.example.banco_dafrhe.pojo.Movimiento
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import java.text.SimpleDateFormat


class AccountsMovementFragment : Fragment(), OnClickListener {

    private lateinit var binding: FragmentAccountsMovementBinding
    private lateinit var movimientoAdapter: MovimientoAdapter
    private lateinit var linearLayoutManager: LinearLayoutManager
    private lateinit var itemDecoration: DividerItemDecoration
    private lateinit var cuenta: Cuenta

    companion object{

        @JvmStatic
        fun newInstance(cuenta: Cuenta?) = AccountsMovementFragment().apply {

            arguments = Bundle().apply {

                putSerializable("Cuenta", cuenta)

            }

        }

    }

    override fun onCreate(savedInstanceState: Bundle?){

        super.onCreate(savedInstanceState)
        arguments?.let {

            val tempCuenta = if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.TIRAMISU) {
                it.getSerializable("Cuenta", Cuenta::class.java)

            } else {
                @Suppress("DEPRECATION")
                it.getSerializable("Cuenta") as? Cuenta
            }

            if (tempCuenta != null) {
                cuenta = tempCuenta
            }

        }

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentAccountsMovementBinding.inflate(inflater, container, false)
        linearLayoutManager = LinearLayoutManager(context)
        itemDecoration = DividerItemDecoration(context, DividerItemDecoration.VERTICAL)

        val mbo: MiBancoOperacional? = MiBancoOperacional.getInstance(context)

        @Suppress("DEPRECATION")
        var cuenta = arguments?.getSerializable("Cuenta")

        if (cuenta != null){

            cuenta = cuenta as Cuenta

            movimientoAdapter = MovimientoAdapter(mbo?.getMovimientos(cuenta) as ArrayList<Movimiento>, this)
            binding.recyclerAccMovement.apply {

                layoutManager = linearLayoutManager
                adapter = movimientoAdapter
                addItemDecoration(itemDecoration)

            }

        }

        return binding.root

    }

    override fun onClick(cuenta: Cuenta) {
        TODO("Not yet implemented")
    }

    override fun onClickMovimiento(movimiento: Movimiento) {

        val dialogView = layoutInflater.inflate(R.layout.dialog_movement, null)

        val tvId = dialogView.findViewById<TextView>(R.id.tvID)
        val tvTipo = dialogView.findViewById<TextView>(R.id.tvTipo)
        val tvFecha = dialogView.findViewById<TextView>(R.id.tvFecha)
        val tvDescripcion = dialogView.findViewById<TextView>(R.id.tvDescripcion)
        val tvImporte = dialogView.findViewById<TextView>(R.id.tvImporte)
        val formatoFecha = SimpleDateFormat("dd/MM/yyyy")
        val fecha = formatoFecha.format(movimiento.getFechaOperacion())

        tvId.text = "ID: ${movimiento.getId()}"
        tvTipo.text = "Tipo: ${movimiento.getTipo()}"
        tvFecha.text = "Fecha operacion: ${fecha}"
        tvDescripcion.text ="Descripcion: ${movimiento.getDescripcion()}"
        tvImporte.text = "Importe: ${movimiento.getImporte()}"

        context?.let {

            MaterialAlertDialogBuilder(it)
                .setTitle("Detalles del movimiento")
                .setView(dialogView)
                .setPositiveButton("Aceptar", DialogInterface.OnClickListener {dialog, i -> dialog.cancel()})
                .setCancelable(false)
                .show()

        }

    }

}