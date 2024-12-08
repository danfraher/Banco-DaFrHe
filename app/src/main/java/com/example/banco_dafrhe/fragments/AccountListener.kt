package com.example.banco_dafrhe.fragments

import com.example.banco_dafrhe.pojo.Cuenta

interface AccountListener {
    fun onCuentaSeleccionada(cuenta: Cuenta)
}
