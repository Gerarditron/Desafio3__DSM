package com.example.carsmotos.model

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import com.example.carsmotos.classes.ColoresModel
import com.example.carsmotos.classes.TipoAutomovilModel
import com.example.carsmotos.db.HelperDB

class TiposAutomoviles (context: Context) {

    private var helper: HelperDB? = null
    private var db: SQLiteDatabase? = null


    init {
        helper = HelperDB(context)
        db = helper!!.getWritableDatabase()
    }

    companion object {
        //TABLA MARCA
        val TABLE_NAME_TIPOAUTOMOVIL = "tipo_automovil"

        //nombre de los campos de la tabla colores
        val COL_ID = "idtipoautomovil"
        val COL_DESCRIPCION = "descripcion"

        //sentencia SQL para crear la tabla
        val CREATE_TABLE_TIPOAUTOMOVIL = (
                "CREATE TABLE IF NOT EXISTS " + TABLE_NAME_TIPOAUTOMOVIL + "("
                        + COL_ID + " integer primary key autoincrement,"
                        + COL_DESCRIPCION + " varchar(45) NOT NULL);"
                )
    }

    // ContentValues
    fun generarContentValues(
        descripcion: String?
    ): ContentValues? {
        val tipoAutomovilesValores = ContentValues()
        tipoAutomovilesValores.put(COL_DESCRIPCION, descripcion)

        return tipoAutomovilesValores
    }

    fun insertValuesDefault() {
        val tiposAutomoviles = arrayOf(
            "Sedan",
            "Camion",
            "Pick-up",
            "Camioneta",
            "Convertible",
            "Minivan",
            "Deportivo",
            "Motomami"
        )

        // Verificacion si existen registros precargados
        val columns = arrayOf(COL_ID, COL_DESCRIPCION)
        var cursor: Cursor? =
            db!!.query(TABLE_NAME_TIPOAUTOMOVIL, columns, null, null, null, null, null)
        // Validando que se ingrese la informacion solamente una vez, cuando se instala por primera vez la aplicacion
        if (cursor == null || cursor!!.count <= 0) {
            // Registrando categorias por defecto
            for (item in tiposAutomoviles) {
                db!!.insert(TABLE_NAME_TIPOAUTOMOVIL, null, generarContentValues(item))
            }
        }
    }

    fun addNewTipoAutomovil(descripcion: String?) {
        db!!.insert(
            TABLE_NAME_TIPOAUTOMOVIL,
            null,
            generarContentValues(descripcion)
        )
    }

    // Eliminar un registro
    fun deleteTipoAutomovil(id: Int) {
        db!!.delete(TABLE_NAME_TIPOAUTOMOVIL, "$COL_ID=?", arrayOf(id.toString()))
    }

    //Modificar un registro
    fun updateTipoAutomovil(
        id: Int,
        nombre: String?
    ) {
        db!!.update(
            TABLE_NAME_TIPOAUTOMOVIL, generarContentValues(nombre),
            "$COL_ID=?", arrayOf(id.toString())
        )
    }

    fun showAllTiposAutomoviles(): Cursor? {
        val columns = arrayOf(COL_ID, COL_DESCRIPCION) //Como la tabla solo tiene 2 columnas, yo solo dos le voy a agregar pero aqui se agregan o quitan
        val cursorAllTiposAutomoviles : Cursor = db!!.query(
            TABLE_NAME_TIPOAUTOMOVIL, columns,
            null, null, null, null, "$COL_DESCRIPCION ASC"
        )
        return cursorAllTiposAutomoviles
    }

    @SuppressLint("Range")
    fun showAllList(): ArrayList<TipoAutomovilModel> {
        val modelList: ArrayList<TipoAutomovilModel> = ArrayList()
        val columns = arrayOf(COL_ID, COL_DESCRIPCION) //Como la tabla solo tiene 2 columnas, yo solo dos le voy a agregar pero aqui se agregan o quitan
        val cursor : Cursor = db!!.query(
            TiposAutomoviles.TABLE_NAME_TIPOAUTOMOVIL, columns,
            null, null, null, null, "$COL_DESCRIPCION ASC"
        )

        var id : Int
        var desripcion: String

        if(cursor.moveToFirst()){
            do{
                id = cursor.getInt(cursor.getColumnIndex("idtipoautomovil"))
                desripcion = cursor.getString(cursor.getColumnIndex("descripcion"))

                val tipoAutomovilModel = TipoAutomovilModel(id = id, descripcion = desripcion)
                modelList.add(tipoAutomovilModel)
            } while (cursor.moveToNext())
        }
        return modelList
    }

    // Debido a que el Spinner solamente guarda el nombre, esta funcion nos ayudara a recuperar el ID de la categoria
    fun searchID(nombre: String): Int? {
        val columns = arrayOf(COL_ID, COL_DESCRIPCION)
        var cursor: Cursor? = db!!.query(
            TABLE_NAME_TIPOAUTOMOVIL, columns,
            "$COL_DESCRIPCION=?", arrayOf(nombre.toString()), null, null, null
        )
        cursor!!.moveToFirst()
        return cursor!!.getInt(0)
    }


    //POR SI ES NECESARIO UN BUSCADOR
    fun searchDesc(id: Int): String? {
        val columns = arrayOf(COL_ID, COL_DESCRIPCION)
        var cursor: Cursor? = db!!.query(
            TABLE_NAME_TIPOAUTOMOVIL, columns,
            "$COL_ID=?", arrayOf(id.toString()), null, null, null
        )
        cursor!!.moveToFirst()
        return cursor!!.getString(1)
    }


}