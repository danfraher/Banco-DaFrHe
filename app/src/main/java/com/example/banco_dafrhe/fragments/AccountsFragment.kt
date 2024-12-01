package com.example.banco_dafrhe.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.banco_dafrhe.Adapter.AccountsListener
import com.example.banco_dafrhe.Adapter.CuentaAdapter
import com.example.banco_dafrhe.R
import com.example.banco_dafrhe.bd.MiBancoOperacional
import com.example.banco_dafrhe.databinding.FragmentAccountsBinding
import com.example.banco_dafrhe.pojo.Cliente
import com.example.banco_dafrhe.pojo.Cuenta

class AccountsFragment : Fragment(){

    private var _binding: FragmentAccountsBinding? = null
    private val binding get() = _binding!!
    private lateinit var cuentaAdapter: CuentaAdapter
    private lateinit var listener: AccountsListener

    companion object{

        private const val ARG_CLIENTE = "Cliente"

        @JvmStatic
        fun newInstance(cliente: Cliente) = AccountsFragment().apply {

            arguments = Bundle().apply{

                putSerializable(ARG_CLIENTE, cliente)

            }

        }

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentAccountsBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        super.onViewCreated(view, savedInstanceState)
        val cliente = arguments?.getSerializable(ARG_CLIENTE) as Cliente
        val cuentas = MiBancoOperacional.getInstance(requireContext())?.getCuentas(cliente) as? List<Cuenta> ?: emptyList()

        cuentaAdapter = CuentaAdapter(cuentas, listener)
        binding.recyclerView.apply {

            layoutManager = LinearLayoutManager(requireContext())
            adapter = cuentaAdapter
            val dividerItemDecoration = DividerItemDecoration(context, LinearLayoutManager.VERTICAL)
            addItemDecoration(dividerItemDecoration)

        }

    }

    fun setListener(listener: AccountsListener){

        this.listener = listener

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}