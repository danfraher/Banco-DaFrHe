package com.example.banco_dafrhe.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.banco_dafrhe.Adapter.MovimientoAdapter
import com.example.banco_dafrhe.R
import com.example.banco_dafrhe.activities.Movimientos
import com.example.banco_dafrhe.bd.MiBancoOperacional
import com.example.banco_dafrhe.databinding.FragmentAccountsMovementBinding
import com.example.banco_dafrhe.pojo.Cuenta
import com.example.banco_dafrhe.pojo.Movimiento

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [AccountsMovementFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class AccountsMovementFragment : Fragment() {

    private var _binding: FragmentAccountsMovementBinding? = null
    private val binding get() = _binding!!
    private lateinit var movimientoAdapter: MovimientoAdapter

    companion object{

        private const val ARG_CUENTA = "Cuenta"

        @JvmStatic
        fun newInstance(cuenta: Cuenta) = AccountsMovementFragment().apply {

            arguments = Bundle().apply {

                putSerializable(ARG_CUENTA, cuenta)

            }

        }

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentAccountsMovementBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        super.onViewCreated(view, savedInstanceState)
        val cuenta = arguments?.getSerializable(ARG_CUENTA) as Cuenta
        val movimientos = (MiBancoOperacional.getInstance(requireContext())?.getMovimientos(cuenta) as? List<Movimientos>) ?.map { it as Movimiento } ?: emptyList()

        movimientoAdapter = MovimientoAdapter(movimientos)
        binding.recyclerView.apply {

            layoutManager = LinearLayoutManager(requireContext())
            adapter = movimientoAdapter

        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}