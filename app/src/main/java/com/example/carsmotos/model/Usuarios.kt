package com.example.carsmotos.model

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import com.example.carsmotos.db.HelperDB

class Usuarios(context: Context) {
    private var helper: HelperDB? = null
    private var db: SQLiteDatabase? = null

    init {
        helper = HelperDB(context)
        db = helper!!.getWritableDatabase()
    }

    companion object {
        //TABLA USUARIO
        val TABLE_NAME_USUARIO = "usuario"

        //nombre de los campos de la tabla usuario
        val COL_ID = "idusuario"
        val COL_NOMBRE = "nombres"
        val COL_APELLIDO = "apellidos"
        val COL_EMAIL = "email"
        val COL_USER = "user"
        val COL_PASSWORD = "password"
        val COL_TIPO = "tipo"

        //sentencia SQL para crear la tabla
        val CREATE_TABLE_USUARIO = (
                "CREATE TABLE IF NOT EXISTS " + TABLE_NAME_USUARIO + "("
                        + COL_ID + " integer primary key autoincrement,"
                        + COL_NOMBRE + " varchar(45) NOT NULL,"
                        + COL_APELLIDO + " varchar(45) NOT NULL,"
                        + COL_EMAIL + " varchar(45) NOT NULL,"
                        + COL_USER + " varchar(45) NOT NULL,"
                        + COL_PASSWORD + " varchar(45) NOT NULL,"
                        + COL_TIPO + " varchar(45) NOT NULL);"
                )
    }

    // ContentValues
    fun generarContentValues(
        nombre: String?,
        apellido: String?,
        email: String?,
        user: String?,
        password: String?,
        tipo: String?
    ): ContentValues? {
        val usuariosValores = ContentValues()
        usuariosValores.put(COL_NOMBRE, nombre)
        usuariosValores.put(COL_APELLIDO, apellido)
        usuariosValores.put(COL_EMAIL, email)
        usuariosValores.put(COL_USER, user)
        usuariosValores.put(COL_PASSWORD, password)
        usuariosValores.put(COL_TIPO, tipo)

        return usuariosValores
    }
    /*
    fun insertValuesDefault() {
        val usuarios = arrayOf(
            "Gerardo Jose",
            "Velasquez Cruz",
            "Embutidos",
            "Mariscos",
            "Pescado",
            "Bebidas",
            "Verduras",
            "Frutas",
            "Bebidas Carbonatadas",
            "Bebidas no carbonatadas"
        )


        // Verificacion si existen registros precargados
        val columns =
            arrayOf(COL_ID, COL_NOMBRE, COL_APELLIDO, COL_EMAIL, COL_USER, COL_PASSWORD, COL_TIPO)
        var cursor: Cursor? =
            db!!.query(TABLE_NAME_USUARIO, columns, null, null, null, null, null)
        // Validando que se ingrese la informacion solamente una vez, cuando se instala por primera vez la aplicacion
        if (cursor == null || cursor!!.count <= 0) {
            // Registrando categorias por defecto
            for (item in usuarios) {
                db!!.insert(TABLE_NAME_USUARIO, null, generarContentValues(item))
            }
        }
    }
    */

    fun showAllUsuario(): Cursor? {
        val columns = arrayOf(COL_ID, COL_APELLIDO, COL_NOMBRE, COL_EMAIL, COL_TIPO)
        return db!!.query(
            TABLE_NAME_USUARIO, columns,
            null, null, null, null, "$COL_APELLIDO ASC"
        )
    }

    // Debido a que el Spinner solamente guarda el nombre, esta funcion nos ayudara a recuperar el ID de la categoria
    fun searchID(nombre: String): Int? {
        val columns = arrayOf(COL_ID, COL_NOMBRE)
        var cursor: Cursor? = db!!.query(
            TABLE_NAME_USUARIO, columns,
            "$COL_NOMBRE=?", arrayOf(nombre.toString()), null, null, null
        )
        cursor!!.moveToFirst()
        return cursor!!.getInt(0)
    }


    //POR SI ES NECESARIO UN BUSCADOR
    fun searchNombre(id: Int): String? {
        val columns = arrayOf(COL_ID, COL_NOMBRE)
        var cursor: Cursor? = db!!.query(
            TABLE_NAME_USUARIO, columns,
            "$COL_ID=?", arrayOf(id.toString()), null, null, null
        )
        cursor!!.moveToFirst()
        return cursor!!.getString(1)
    }

}