package com.example.banco_dafrhe.Adapter

import android.content.Context
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.banco_dafrhe.R
import com.example.banco_dafrhe.databinding.ItemMovimientoBinding
import com.example.banco_dafrhe.pojo.Movimiento

class MovimientoAdapter (private var listaMovimientos: List<Movimiento>) : RecyclerView.Adapter<MovimientoAdapter.MovimientoViewHolder>() {

    private lateinit var context: Context

    fun updateData(nuevaListaMovimientos: List<Movimiento>) {

        listaMovimientos = nuevaListaMovimientos
        notifyDataSetChanged()

    }

    inner class MovimientoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val binding = ItemMovimientoBinding.bind(itemView)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovimientoViewHolder {

        context = parent.context
        val view = ItemMovimientoBinding.inflate(android.view.LayoutInflater.from(context), parent, false)
        return MovimientoViewHolder(view.root)

    }

    override fun getItemCount(): Int = listaMovimientos.size

    override fun onBindViewHolder(holder: MovimientoViewHolder, position: Int) {

        val movimiento = listaMovimientos.get(position)

        with(holder) {

            binding.tvOrder.text = (position + 1).toString()
            binding.tvMovimiento.text = movimiento.getDescripcion()
            binding.tvFecha.text = movimiento.getFechaOperacion().toString()
            binding.tvImporte.text = movimiento.getImporte().toString()
            Glide.with(context)
                .load(R.drawable.cerdo)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .centerCrop()
                .circleCrop()
                .into(binding.imgPhoto)

        }

    }

}