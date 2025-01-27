package com.example.banco_dafrhe.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.banco_dafrhe.pojo.CajeroEntity


@Dao
interface CajeroDAO {

    @Query("SELECT * FROM cajeros")
    fun getAllCajeros(): List<CajeroEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(cajeros: List<CajeroEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addCajero(cajero: CajeroEntity)

    @Update
    fun updateCajero(cajero: CajeroEntity)

    @Delete
    fun deleteCajero(cajero: CajeroEntity)

}