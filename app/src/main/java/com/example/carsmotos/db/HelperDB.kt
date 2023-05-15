package com.example.carsmotos.db

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import com.example.carsmotos.model.Usuarios
import com.example.carsmotos.model.Automoviles
import com.example.carsmotos.model.Marcas
import com.example.carsmotos.model.Colores
import com.example.carsmotos.model.TiposAutomoviles
import com.example.carsmotos.model.FavoritosAutomoviles

class HelperDB(context: Context?) :
    SQLiteOpenHelper(context, DB_NAME, null, DB_VERSION) {
    companion object {
        private const val DB_NAME = "carsmotors.sqlite"
        private const val DB_VERSION = 1

    }

    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(Usuarios.CREATE_TABLE_USUARIO)
        db.execSQL(Marcas.CREATE_TABLE_MARCAS)
        db.execSQL(Colores.CREATE_TABLE_COLORES)
        db.execSQL(TiposAutomoviles.CREATE_TABLE_TIPOAUTOMOVIL)
        db.execSQL(Automoviles.CREATE_TABLE_AUTOMOVIL)
        db.execSQL(FavoritosAutomoviles.CREATE_TABLE_FAVORITOSAUTOMOVIL)
        Log.d("DB-CREATE","Se han creado todas las tablas")
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {}

}