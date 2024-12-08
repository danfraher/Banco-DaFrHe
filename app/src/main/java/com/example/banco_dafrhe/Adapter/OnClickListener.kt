package com.example.banco_dafrhe.Adapter

import com.example.banco_dafrhe.pojo.Cuenta
import com.example.banco_dafrhe.pojo.Movimiento

interface OnClickListener {

    fun onClick(cuenta: Cuenta)
    fun onClickMovimiento(movimiento: Movimiento)

}