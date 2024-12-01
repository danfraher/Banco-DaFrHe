package com.example.banco_dafrhe

import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import com.example.banco_dafrhe.databinding.DialogMovementBinding
import com.example.banco_dafrhe.pojo.Movimiento

class MovementDialog(private val movimiento: Movimiento) : DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        val binding = DialogMovementBinding.inflate(layoutInflater)

        binding.tvDescripcion.text = movimiento.getDescripcion()
        binding.tvAmount.text = movimiento.getImporte().toString()
        binding.tvDate.text = movimiento.getFechaOperacion().toString()
        binding.btnClose.setOnClickListener { dismiss() }

        return Dialog(requireContext()).apply {

            setContentView(binding.root)

        }

    }

}