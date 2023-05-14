package com.example.carsmotos.model

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import com.example.carsmotos.db.HelperDB
import java.sql.Timestamp


class FavoritosAutomoviles (context: Context) {

    private var helper: HelperDB? = null
    private var db: SQLiteDatabase? = null

    init {
        helper = HelperDB(context)
        db = helper!!.getWritableDatabase()
    }

    companion object {
        //TABLA FAVORITOSAUTOMOVIL
        val TABLE_NAME_FAVORITOSAUTOMOVIL = "favoritos_automovil"

        //nombre de los campos de la tabla usuario
        val COL_ID = "idfavoritosautomovil"
        val COL_IDUSUARIO = "idusuario"
        val COL_IDAUTOMOVIL = "idfavoritoautomovil"
        val COL_FECHA_AGREGADO = "fecha_agregado"

        //sentencia SQL para crear la tabla
        val CREATE_TABLE_FAVORITOSAUTOMOVIL = (
                "CREATE TABLE IF NOT EXISTS " + TABLE_NAME_FAVORITOSAUTOMOVIL + "("
                        + COL_ID + " integer primary key autoincrement,"
                        + COL_IDUSUARIO + " integer NOT NULL,"
                        + COL_IDAUTOMOVIL + " integer NOT NULL,"
                        + COL_FECHA_AGREGADO + " TIMESTAMP DEFAULT CURRENT_TIMESTAMP, "
                        + " FOREIGN KEY ("+ COL_IDUSUARIO +") REFERENCES usuario(idusuario),"
                        + " FOREIGN KEY ("+ COL_IDAUTOMOVIL +") REFERENCES automovil(idautomovil)"
                        + ");"
                )
    }

    // ContentValues
    fun generarContentValues(
        idusuario: Int?,
        idautomovil: Int?,
        fecha_agregado: String? //Es una timestamp, asi que debe de convertirse a string
    ): ContentValues? {
        val favoritosAutomovilesValores = ContentValues()
        favoritosAutomovilesValores.put(COL_IDUSUARIO, idusuario)
        favoritosAutomovilesValores.put(COL_IDAUTOMOVIL, idautomovil)
        favoritosAutomovilesValores.put(COL_FECHA_AGREGADO, fecha_agregado)

        return favoritosAutomovilesValores
    }


    fun showAllFavoritosAutomovil(): Cursor? {
        val columns = arrayOf(COL_ID, COL_IDUSUARIO, COL_IDAUTOMOVIL, COL_FECHA_AGREGADO)
        return db!!.query(
            TABLE_NAME_FAVORITOSAUTOMOVIL, columns,
            null, null, null, null, "$COL_FECHA_AGREGADO ASC"
        )
    }

    // Debido a que el Spinner solamente guarda el nombre, esta funcion nos ayudara a recuperar el ID de la categoria
    fun searchID(nombre: String): Int? {
        val columns = arrayOf(COL_ID, COL_FECHA_AGREGADO)
        var cursor: Cursor? = db!!.query(
            TABLE_NAME_FAVORITOSAUTOMOVIL, columns,
            "$COL_FECHA_AGREGADO=?", arrayOf(nombre.toString()), null, null, null
        )
        cursor!!.moveToFirst()
        return cursor!!.getInt(0)
    }


    //POR SI ES NECESARIO UN BUSCADOR
    fun searchFechaAgregado(id: Int): String? {
        val columns = arrayOf(COL_ID, COL_FECHA_AGREGADO)
        var cursor: Cursor? = db!!.query(
            TABLE_NAME_FAVORITOSAUTOMOVIL, columns,
            "$COL_ID=?", arrayOf(id.toString()), null, null, null
        )
        cursor!!.moveToFirst()
        return cursor!!.getString(1)
    }
}