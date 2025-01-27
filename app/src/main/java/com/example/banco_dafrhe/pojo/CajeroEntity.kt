package com.example.banco_dafrhe.pojo

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "cajeros")
data class CajeroEntity(

    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val nombre: String,
    val direccion: String,
    val latitud: Double,
    val longitud: Double,
    val zoom: String

){
}
