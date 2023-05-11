package com.example.carsmotos.model

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.widget.ListView
import com.example.carsmotos.db.HelperDB

class Marcas(context: Context) {
    private var helper: HelperDB? = null
    private var db: SQLiteDatabase? = null


    init {
        helper = HelperDB(context)
        db = helper!!.getWritableDatabase()
    }

    companion object {
        //TABLA MARCA
        val TABLE_NAME_MARCA = "marcas"

        //nombre de los campos de la tabla marcas
        val COL_ID = "idmarcas"
        val COL_NOMBRE = "nombre"

        //sentencia SQL para crear la tabla
        val CREATE_TABLE_MARCAS = (
                "CREATE TABLE IF NOT EXISTS " + TABLE_NAME_MARCA + "("
                        + COL_ID + " integer primary key autoincrement,"
                        + COL_NOMBRE + " varchar(45) NOT NULL);"
                )
    }

    // ContentValues
    fun generarContentValues(
        nombre: String?
    ): ContentValues? {
        val marcasValores = ContentValues()
        marcasValores.put(COL_NOMBRE, nombre)

        return marcasValores
    }

    fun insertValuesDefault() {
        val marcas = arrayOf(
            "BMW",
            "Mercedes-Benz",
            "Audi",
            "Lexus",
            "Renault",
            "Ford",
            "Opel",
            "Seat"
        )

        // Verificacion si existen registros precargados
        val columns = arrayOf(COL_ID, COL_NOMBRE)
        var cursor: Cursor? =
            db!!.query(TABLE_NAME_MARCA, columns, null, null, null, null, null)
        // Validando que se ingrese la informacion solamente una vez, cuando se instala por primera vez la aplicacion
        if (cursor == null || cursor!!.count <= 0) {
            // Registrando categorias por defecto
            for (item in marcas) {
                db!!.insert(TABLE_NAME_MARCA, null, generarContentValues(item))
            }
        }
    }


    fun showAllUsuario(): Cursor? {
        val columns = arrayOf(COL_ID, COL_NOMBRE)
        return db!!.query(
            TABLE_NAME_MARCA, columns,
            null, null, null, null, "$COL_NOMBRE ASC"
        )
    }

    // Debido a que el Spinner solamente guarda el nombre, esta funcion nos ayudara a recuperar el ID de la categoria
    fun searchID(nombre: String): Int? {
        val columns = arrayOf(COL_ID, COL_NOMBRE)
        var cursor: Cursor? = db!!.query(
            TABLE_NAME_MARCA, columns,
            "$COL_NOMBRE=?", arrayOf(nombre.toString()), null, null, null
        )
        cursor!!.moveToFirst()
        return cursor!!.getInt(0)
    }


    //POR SI ES NECESARIO UN BUSCADOR
    fun searchNombre(id: Int): String? {
        val columns = arrayOf(COL_ID, COL_NOMBRE)
        var cursor: Cursor? = db!!.query(
            TABLE_NAME_MARCA, columns,
            "$COL_ID=?", arrayOf(id.toString()), null, null, null
        )
        cursor!!.moveToFirst()
        return cursor!!.getString(1)
    }
}
