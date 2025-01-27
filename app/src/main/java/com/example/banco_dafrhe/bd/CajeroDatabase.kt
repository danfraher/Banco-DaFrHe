package com.example.banco_dafrhe.bd

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.banco_dafrhe.bd.CajeroDatabase.Companion.getInstance
import com.example.banco_dafrhe.dao.CajeroDAO
import com.example.banco_dafrhe.pojo.CajeroEntity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Database(entities = [CajeroEntity::class], version = 1)
abstract class CajeroDatabase: RoomDatabase() {

    abstract fun cajeroDAO(): CajeroDAO

    companion object{

        @Volatile
        private var INSTANCE: CajeroDatabase? = null

        fun getInstance(context: Context): CajeroDatabase{

            return INSTANCE ?: synchronized(this){

                val instance = Room.databaseBuilder(

                    context.applicationContext,
                    CajeroDatabase::class.java,
                    "cajeros_database"

                ).addCallback(CajeroDatabaseCallback(context))
                 .build()
                INSTANCE = instance
                instance

            }

        }

    }

}

private class CajeroDatabaseCallback(private val context: Context): RoomDatabase.Callback() {

    override fun onCreate(db: SupportSQLiteDatabase){

        super.onCreate(db)
        insertarDatosIniciales(context)

    }

    private fun insertarDatosIniciales(context: Context){

        CoroutineScope(Dispatchers.IO).launch {

            val database = getInstance(context)
            database.cajeroDAO().insertAll(

                listOf(

                    CajeroEntity(1,"Santander", "Carrer del Clariano, 1, 46021 Valencia, Valencia, España", 39.47600769999999, -0.3524475000000393, "15"),
                    CajeroEntity(2,"ATM", "Avinguda del Cardenal Benlloch, 65, 46021 València, Valencia, España", 39.4710366, -0.3547525000000178, "15"),
                    CajeroEntity(3,"Bankinter", "Av. del Port, 237, 46011 València, Valencia, España", 39.46161999999999, -0.3376299999999901, "15"),
                    CajeroEntity(4,"Revolut", "Carrer del Batxiller, 6, 46010 València, Valencia, España", 39.4826729, -0.3639118999999482, "15"),
                    CajeroEntity(5,"ING", "Av. del Regne de València, 2, 46005 València, Valencia, España", 39.4647669, -0.3732760000000326, "15")

                )

            )

        }

    }

}