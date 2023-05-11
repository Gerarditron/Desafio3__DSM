package com.example.carsmotos.model

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import com.example.carsmotos.db.HelperDB

class Colores (context: Context){

    private var helper: HelperDB? = null
    private var db: SQLiteDatabase? = null


    init {
        helper = HelperDB(context)
        db = helper!!.getWritableDatabase()
    }

    companion object {
        //TABLA MARCA
        val TABLE_NAME_COLORES = "colores"

        //nombre de los campos de la tabla colores
        val COL_ID = "idcolores"
        val COL_DESCRIPCION = "descripcion"

        //sentencia SQL para crear la tabla
        val CREATE_TABLE_COLORES = (
                "CREATE TABLE IF NOT EXISTS " + TABLE_NAME_COLORES + "("
                        + COL_ID + " integer primary key autoincrement,"
                        + COL_DESCRIPCION + " varchar(45) NOT NULL);"
                )
    }

    // ContentValues
    fun generarContentValues(
        descripcion: String?
    ): ContentValues? {
        val coloresValores = ContentValues()
        coloresValores.put(COL_DESCRIPCION, descripcion)

        return coloresValores
    }

    fun insertValuesDefault() {
        val colores = arrayOf(
            "Blanco",
            "Gris",
            "Negro",
            "Plata",
            "Azul",
            "Rojo",
            "Aqua",
            "Verde"
        )

        // Verificacion si existen registros precargados
        val columns = arrayOf(COL_ID, COL_DESCRIPCION)
        var cursor: Cursor? =
            db!!.query(TABLE_NAME_COLORES, columns, null, null, null, null, null)
        // Validando que se ingrese la informacion solamente una vez, cuando se instala por primera vez la aplicacion
        if (cursor == null || cursor!!.count <= 0) {
            // Registrando categorias por defecto
            for (item in colores) {
                db!!.insert(TABLE_NAME_COLORES, null, generarContentValues(item))
            }
        }
    }


    fun showAllColores(): Cursor? {
        val columns = arrayOf(COL_ID, COL_DESCRIPCION)
        return db!!.query(
            TABLE_NAME_COLORES, columns,
            null, null, null, null, "$COL_DESCRIPCION ASC"
        )
    }

    // Debido a que el Spinner solamente guarda el nombre, esta funcion nos ayudara a recuperar el ID de la categoria
    fun searchID(nombre: String): Int? {
        val columns = arrayOf(COL_ID, COL_DESCRIPCION)
        var cursor: Cursor? = db!!.query(
            TABLE_NAME_COLORES, columns,
            "$COL_DESCRIPCION=?", arrayOf(nombre.toString()), null, null, null
        )
        cursor!!.moveToFirst()
        return cursor!!.getInt(0)
    }


    //POR SI ES NECESARIO UN BUSCADOR
    fun searchDesc(id: Int): String? {
        val columns = arrayOf(COL_ID, COL_DESCRIPCION)
        var cursor: Cursor? = db!!.query(
            TABLE_NAME_COLORES, columns,
            "$COL_ID=?", arrayOf(id.toString()), null, null, null
        )
        cursor!!.moveToFirst()
        return cursor!!.getString(1)
    }

}