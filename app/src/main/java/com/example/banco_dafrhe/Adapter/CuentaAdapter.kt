package com.example.banco_dafrhe.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.banco_dafrhe.R
import com.example.banco_dafrhe.databinding.ItemCuentaBinding
import com.example.banco_dafrhe.pojo.Cuenta

class CuentaAdapter (private val listaCuentas: List<Cuenta>, private val listener: AccountsListener) : RecyclerView.Adapter<CuentaAdapter.CuentaViewHolder>() {

    private lateinit var context: Context

    inner class CuentaViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val binding = ItemCuentaBinding.bind(itemView)

        fun setListener(cuenta: Cuenta) {

            binding.root.setOnClickListener {

                listener.onClick(cuenta)

            }

        }

        fun onAccountClicked(cuenta: Cuenta) {
            listener?.onAccountSelected(cuenta)
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CuentaViewHolder {
        context = parent.context
        val view = LayoutInflater.from(context).inflate(R.layout.item_cuenta, parent, false)
        return CuentaViewHolder(view)
    }

    override fun getItemCount(): Int = listaCuentas.size

    override fun onBindViewHolder(holder: CuentaViewHolder, position: Int) {
        val cuenta = listaCuentas.get(position)

        with(holder) {

            binding.tvOrder.text = (position + 1).toString()
            binding.tvName.text = cuenta.getNumeroCuenta()
            binding.tvDinero.text = cuenta.getSaldoActual().toString()
            Glide.with(context)
                .load(R.drawable.banco)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .centerCrop()
                .circleCrop()
                .into(binding.imgPhoto)

        }

    }

}