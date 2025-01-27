package com.example.banco_dafrhe.Adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.banco_dafrhe.R
import com.example.banco_dafrhe.databinding.ItemCajeroBinding
import com.example.banco_dafrhe.pojo.CajeroEntity

class CajeroAdapter(private val cajeros: List<CajeroEntity>,
                    private val onItemClick: (CajeroEntity) -> Unit,
                    private val onDeleteClick: (CajeroEntity) -> Unit
                    ): RecyclerView.Adapter<CajeroAdapter.CajeroViewHolder>() {

    inner class CajeroViewHolder(private val binding: ItemCajeroBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(cajero: CajeroEntity) {

            binding.tvNombre.text = cajero.nombre
            binding.tvDireccion.text = cajero.direccion

            itemView.setOnClickListener{

                onItemClick(cajero)

            }

            binding.btnEliminar.setOnClickListener {

                onDeleteClick(cajero)

            }

        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CajeroViewHolder {

        val binding = ItemCajeroBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CajeroViewHolder(binding)

    }

    override fun onBindViewHolder(holder: CajeroViewHolder, position: Int) {
        holder.bind(cajeros[position])
    }

    override fun getItemCount(): Int = cajeros.size

}