package com.example.carsmotos.model

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import com.example.carsmotos.db.HelperDB

class Automoviles(context: Context?) {

    private var helper: HelperDB? = null
    private var db: SQLiteDatabase? = null


    init {
        helper = HelperDB(context)
        db = helper!!.getWritableDatabase()
    }

    companion object {
        //TABLA MARCA
        val TABLE_NAME_AUTOMOVIL = "automovil"

        //nombre de los campos de la tabla colores
        val COL_ID = "idautomovil"
        val COL_MODELO = "modelo"
        val COL_NUMERO_VIN = "numero_vin"
        val COL_NUMERO_CHASIS = "numero_chasis"
        val COL_NUMERO_MOTOR = "numero_motor"
        val COL_NUMERO_ASIENTOS = "numero_asientos"
        val COL_ANIO = "anio"
        val COL_CAPACIDAD_ASIENTOS = "capacidad_asientos"
        val COL_PRECIO = "precio"
        val COL_URI_IMG = "URI_IMG"
        val COL_DESCRIPCION = "descripcion"
        val COL_IDMARCAS = "idmarcas"
        val COL_IDTIPOAUTOMOVIL = "idtipoautomovil"
        val COL_IDCOLORES = "idcolores"

        //sentencia SQL para crear la tabla
        val CREATE_TABLE_AUTOMOVIL = (
                "CREATE TABLE IF NOT EXISTS " + TABLE_NAME_AUTOMOVIL + "("
                        + COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                        + COL_MODELO + " varchar(45) NOT NULL,"
                        + COL_NUMERO_VIN + " varchar(45) NOT NULL,"
                        + COL_NUMERO_CHASIS + " varchar(45) NOT NULL,"
                        + COL_NUMERO_MOTOR + " varchar(45) NOT NULL,"
                        + COL_NUMERO_ASIENTOS + " integer NOT NULL,"
                        + COL_ANIO + " year NOT NULL,"
                        + COL_CAPACIDAD_ASIENTOS + " int NOT NULL,"
                        + COL_PRECIO + " decimal(10,2) NOT NULL,"
                        + COL_URI_IMG + " varchar(45) NOT NULL,"
                        + COL_DESCRIPCION + " varchar(45) NOT NULL,"
                        + COL_IDMARCAS + " integer NOT NULL,"
                        + COL_IDTIPOAUTOMOVIL + " integer NOT NULL,"
                        + COL_IDCOLORES + " integer NOT NULL,"
                        + " FOREIGN KEY ("+ COL_IDMARCAS +") REFERENCES marcas(idmarcas),"
                        + " FOREIGN KEY ("+ COL_IDTIPOAUTOMOVIL +") REFERENCES tipo_automovil(idtipoautomovil),"
                        + " FOREIGN KEY ("+ COL_IDCOLORES +") REFERENCES colores(idcolores) "
                        + ");"
                )
    }

    // ContentValues
    fun generarContentValues(
        modelo: String?,
        numero_vin: String?,
        numero_chasis: String?,
        numero_motor: String?,
        numero_asientos: Int?,
        anio: String?, //Viene como un valor tipo YEAR, hay que convertirlo
        capacidad_asientos: String?,
        precio: Double?, //Viene como un Decimal de 2 decimales, hay que convertirlo
        URI_IMG: String?,
        descripcion: String?,
        idmarcas: Int?,
        idtipoautomovil: Int?,
        idcolores: Int?
    ): ContentValues? {
        val automovilesValores = ContentValues()
        automovilesValores.put(COL_MODELO, modelo)
        automovilesValores.put(COL_NUMERO_VIN, numero_vin)
        automovilesValores.put(COL_NUMERO_CHASIS, numero_chasis)
        automovilesValores.put(COL_NUMERO_MOTOR, numero_motor)
        automovilesValores.put(COL_NUMERO_ASIENTOS, numero_asientos)
        automovilesValores.put(COL_ANIO, anio)
        automovilesValores.put(COL_CAPACIDAD_ASIENTOS, capacidad_asientos)
        automovilesValores.put(COL_PRECIO, precio)
        automovilesValores.put(COL_URI_IMG, URI_IMG)
        automovilesValores.put(COL_DESCRIPCION, descripcion)
        automovilesValores.put(COL_IDMARCAS, idmarcas)
        automovilesValores.put(COL_IDTIPOAUTOMOVIL, idtipoautomovil)
        automovilesValores.put(COL_IDCOLORES, idcolores)

        return automovilesValores
    }

    fun showAllAutomoviles(): Cursor? {
        val columns = arrayOf(COL_ID, COL_NUMERO_VIN, COL_NUMERO_CHASIS, COL_NUMERO_MOTOR, COL_NUMERO_ASIENTOS, COL_PRECIO, COL_URI_IMG, COL_DESCRIPCION, COL_IDMARCAS, COL_IDTIPOAUTOMOVIL, COL_IDCOLORES)
        return db!!.query(
            TABLE_NAME_AUTOMOVIL, columns,
            null, null, null, null, "$COL_DESCRIPCION ASC"
        )
    }

    //Por si deseamos que nos imprima SOLO LA DESCRIPCION, que encontremos con un id
    fun searchID(nombre: String): Int? {
        val columns = arrayOf(COL_ID, COL_DESCRIPCION)
        var cursor: Cursor? = db!!.query(
            TABLE_NAME_AUTOMOVIL, columns,
            "$COL_DESCRIPCION=?", arrayOf(nombre.toString()), null, null, null
        )
        cursor!!.moveToFirst()
        return cursor!!.getInt(0)
    }




}