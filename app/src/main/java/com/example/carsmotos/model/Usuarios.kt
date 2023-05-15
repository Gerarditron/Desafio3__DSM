package com.example.carsmotos.model

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import com.example.carsmotos.classes.ColoresModel
import com.example.carsmotos.classes.MarcaModel
import com.example.carsmotos.classes.UsuarioModel
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

    //Agregar un nuevo registro
    fun addNewUser(nombre: String?, apellido: String?, email: String?, user: String?, password: String?, tipo: String?) {
        db!!.insert(
            TABLE_NAME_USUARIO,
            null,
            generarContentValues(nombre, apellido, email, user, password,tipo)
        )
    }

    // Eliminar un registro
    fun deleteUser(id: Int) {
        db!!.delete(TABLE_NAME_USUARIO, "$COL_ID=?", arrayOf(id.toString()))
    }

    //Modificar un registro
    fun updateUser(
        id: Int,
        nombre: String?,
        apellido: String?,
        email: String?,
        user: String?,
        password: String?,
        tipo: String?
    ) {
        db!!.update(
            TABLE_NAME_USUARIO, generarContentValues(nombre, apellido, email, user,password,tipo),
            "$COL_ID=?", arrayOf(id.toString())
        )
    }



    fun showAllUsuario(): Cursor? {
        val columns = arrayOf(COL_ID, COL_APELLIDO, COL_NOMBRE, COL_EMAIL, COL_TIPO)
        return db!!.query(
            TABLE_NAME_USUARIO, columns,
            null, null, null, null, "$COL_APELLIDO ASC"
        )
    }



    // Mostrar un registro particular
    fun searchID(id: Int): Cursor? {
        val columns = arrayOf(COL_ID, COL_NOMBRE, COL_APELLIDO, COL_EMAIL, COL_USER, COL_PASSWORD, COL_TIPO)
        val cursor = db!!.query(
            TABLE_NAME_USUARIO, columns,
            "$COL_ID=?", arrayOf(id.toString()), null, null, null
        )

        return cursor
    }


    // Mostrar un registro particular POR EMAIL
    fun searchUsuarioEmail(email: String): Cursor? {
        //Este es el arreglo de columnas que se envian a la otra actividad
        //Si se desean mas o menos columnas, aqui se agregan o quitan
        val columns = arrayOf(COL_ID, COL_EMAIL, COL_TIPO)
        //Y con este QUERY yo busco todos los valores que cumplan con el EMAIL que le he mandado desde el LOGIN
        return db!!.query(
            TABLE_NAME_USUARIO, columns,
            "$COL_EMAIL=?", arrayOf(email), null, null, null
        )
    }


    // Mostrar TODOS LOS REGISTROS
    fun showAllUsuarios(): Cursor? {
        val columns = arrayOf(COL_ID, COL_NOMBRE, COL_APELLIDO,COL_USER, COL_EMAIL ) //Agrego todas las columnas que sean necesarias
        val cursorAllUsuarios : Cursor = db!!.query(
            TABLE_NAME_USUARIO, columns,
            null, null, null, null, "$COL_APELLIDO ASC"
        )
        return cursorAllUsuarios
    }

    @SuppressLint("Range")
    fun showAllList(): ArrayList<UsuarioModel> {
        val modelList: ArrayList<UsuarioModel> = ArrayList()
        val columns = arrayOf(COL_ID, COL_NOMBRE, COL_APELLIDO, COL_EMAIL, COL_USER, COL_PASSWORD, COL_TIPO) //Como la tabla solo tiene 2 columnas, yo solo dos le voy a agregar pero aqui se agregan o quitan
        val cursor : Cursor = db!!.query(
            Usuarios.TABLE_NAME_USUARIO, columns,
            null, null, null, null, "$COL_NOMBRE ASC"
        )

        var id : Int
        var nombres : String
        var apellidos : String
        var email : String
        var user : String
        var password : String
        var tipo : String


        if(cursor.moveToFirst()){
            do{
                id = cursor.getInt(cursor.getColumnIndex("idusuario"))
                nombres = cursor.getString(cursor.getColumnIndex("nombres"))
                apellidos = cursor.getString(cursor.getColumnIndex("apellidos"))
                email = cursor.getString(cursor.getColumnIndex("email"))
                user = cursor.getString(cursor.getColumnIndex("user"))
                password = cursor.getString(cursor.getColumnIndex("password"))
                tipo = cursor.getString(cursor.getColumnIndex("tipo"))


                val usersModel = UsuarioModel(id = id, nombres = nombres, apellidos = apellidos, email = email, user = user, password = password, tipo = tipo)
                modelList.add(usersModel)
            } while (cursor.moveToNext())
        }
        return modelList
    }


}