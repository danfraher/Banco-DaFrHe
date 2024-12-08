package com.example.banco_dafrhe.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.banco_dafrhe.Adapter.CuentaAdapter
import com.example.banco_dafrhe.Adapter.OnClickListener
import com.example.banco_dafrhe.bd.MiBancoOperacional
import com.example.banco_dafrhe.databinding.FragmentAccountsBinding
import com.example.banco_dafrhe.pojo.Cliente
import com.example.banco_dafrhe.pojo.Cuenta
import com.example.banco_dafrhe.pojo.Movimiento

private const val ARG_CLIENTE = "Cliente"

class AccountsFragment : Fragment(), OnClickListener{

    private lateinit var cuentaAdapter: CuentaAdapter
    private lateinit var binding: FragmentAccountsBinding
    private lateinit var linearLayoutManager: LinearLayoutManager
    private lateinit var itemDecoration: DividerItemDecoration
    private lateinit var listener: AccountListener

    private lateinit var cliente: Cliente

    companion object{

        @JvmStatic
        fun newInstance(cliente: Cliente?) =
            AccountsFragment().apply {
                arguments = Bundle().apply {
                    putSerializable(ARG_CLIENTE, cliente)
                }
            }

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

            val tempCliente: Cliente? = if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.TIRAMISU) {
                it.getSerializable("Cliente", Cliente::class.java)
            } else {
                @Suppress("DEPRECATION")
                it.getSerializable("Cliente") as? Cliente
            }

            if (tempCliente != null) {
                cliente = tempCliente
            }

        }

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val mbo: MiBancoOperacional? = MiBancoOperacional.getInstance(context)
        val cuentas = mbo?.getCuentas(cliente) as ArrayList<Cuenta>

        binding = FragmentAccountsBinding.inflate(inflater, container, false)
        linearLayoutManager = LinearLayoutManager(context)
        cuentaAdapter = CuentaAdapter(cuentas, this)

        binding.recyclerAcc.apply {

            layoutManager = linearLayoutManager
            adapter = cuentaAdapter

        }

        return binding.root

    }

    fun setCuentasListener(listener: AccountListener){

        this.listener = listener

    }

    override fun onClick(cuenta: Cuenta) {

        listener.onCuentaSeleccionada(cuenta)

    }

    override fun onClickMovimiento(movimiento: Movimiento) {
        TODO("Not yet implemented")
    }


}