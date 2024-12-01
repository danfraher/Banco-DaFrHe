package com.example.banco_dafrhe.Adapter

import com.example.banco_dafrhe.pojo.Cuenta

interface AccountsListener {

        fun onClick(cuenta: Cuenta)

        fun onAccountSelected(cuenta: Cuenta)

}