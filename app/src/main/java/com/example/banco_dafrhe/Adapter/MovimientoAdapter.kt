package com.example.banco_dafrhe.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.banco_dafrhe.R
import com.example.banco_dafrhe.activities.Movimientos
import com.example.banco_dafrhe.databinding.ItemMovimientoBinding
import com.example.banco_dafrhe.pojo.Movimiento

class MovimientoAdapter (private var listaMovimientos: ArrayList<Movimiento>?, private var listener: OnClickListener) : RecyclerView.Adapter<MovimientoAdapter.MovimientoViewHolder>() {

    private lateinit var context: Context

    inner class MovimientoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val binding = ItemMovimientoBinding.bind(itemView)

        fun setListener(movimiento: Movimiento) {

            binding.root.setOnClickListener(){listener.onClickMovimiento(movimiento)}

        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovimientoViewHolder {

        context = parent.context
        val view = LayoutInflater.from(context).inflate(R.layout.item_movimiento, parent, false)
        return MovimientoViewHolder(view)

    }

    override fun getItemCount(): Int = listaMovimientos?.size!!

    override fun onBindViewHolder(holder: MovimientoViewHolder, position: Int) {

        val movimiento = listaMovimientos?.get(position)

        with(holder) {
            if (movimiento != null) {
                setListener(movimiento)
            }
            binding.tvOrder.text = (position + 1).toString()
            if (movimiento != null) {
                binding.tvMovimiento.text = movimiento.getDescripcion()
            }
            if (movimiento != null) {
                binding.tvFecha.text = movimiento.getFechaOperacion().toString()
            }
            if (movimiento != null) {
                binding.tvImporte.text = movimiento.getImporte().toString()
            }
            Glide.with(context)
                .load(R.drawable.cerdo)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .centerCrop()
                .circleCrop()
                .into(binding.imgPhoto)

        }

    }

}