package com.example.carsmotos.model

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import com.example.carsmotos.classes.AutomovilModel
import com.example.carsmotos.classes.UsuarioModel
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
                        + COL_ANIO + " integer NOT NULL,"
                        + COL_CAPACIDAD_ASIENTOS + " integer NOT NULL,"
                        + COL_PRECIO + " double NOT NULL,"
                        + COL_URI_IMG + " varchar(45) NOT NULL,"
                        + COL_DESCRIPCION + " varchar(45) NOT NULL,"
                        + COL_IDMARCAS + " integer NOT NULL,"
                        + COL_IDTIPOAUTOMOVIL + " integer NOT NULL,"
                        + COL_IDCOLORES + " integer NOT NULL,"
                        + " FOREIGN KEY (" + COL_IDMARCAS + ") REFERENCES marcas(idmarcas),"
                        + " FOREIGN KEY (" + COL_IDTIPOAUTOMOVIL + ") REFERENCES tipo_automovil(idtipoautomovil),"
                        + " FOREIGN KEY (" + COL_IDCOLORES + ") REFERENCES colores(idcolores) "
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
        anio: Int?, //Por cuestiones de tiempo la voy a mandar como un INT
        capacidad_asientos: Int?,
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

    //Agregar un nuevo registro
    fun addNewAuto(
        modelo: String?,
        numero_vin: String?,
        numero_chasis: String?,
        numero_motor: String?,
        numero_asientos: Int?,
        anio: Int?,
        capacidad_asientos: Int?,
        precio: Double?,
        URI_IMG: String?,
        descripcion: String?,
        idmarcas: Int?,
        idtipoautomovil: Int?,
        idcolores: Int?
    ) {
        db!!.insert(
            Automoviles.TABLE_NAME_AUTOMOVIL,
            null,
            generarContentValues(
                modelo,
                numero_vin,
                numero_chasis,
                numero_motor,
                numero_asientos,
                anio,
                capacidad_asientos,
                precio,
                URI_IMG,
                descripcion,
                idmarcas,
                idtipoautomovil,
                idcolores
            )
        )
    }

    fun updateAuto(
        id: Int,
        modelo: String?,
        numero_vin: String?,
        numero_chasis: String?,
        numero_motor: String?,
        numero_asientos: Int?,
        anio: Int?,
        capacidad_asientos: Int?,
        precio: Double?,
        URI_IMG: String?,
        descripcion: String?,
        idmarcas: Int?,
        idtipoautomovil: Int?,
        idcolores: Int?
    ) {
        db!!.update(
            Automoviles.TABLE_NAME_AUTOMOVIL, generarContentValues(
                modelo,
                numero_vin,
                numero_chasis,
                numero_motor,
                numero_asientos,
                anio,
                capacidad_asientos,
                precio,
                URI_IMG,
                descripcion,
                idmarcas,
                idtipoautomovil,
                idcolores
            ),
            "$COL_ID=?", arrayOf(id.toString())
        )
    }


    fun showAllAutomoviles(): Cursor? {
        val columns = arrayOf(
            COL_ID,
            COL_MODELO,
            COL_NUMERO_VIN,
            COL_NUMERO_CHASIS,
            COL_NUMERO_MOTOR,
            COL_NUMERO_ASIENTOS,
            COL_ANIO,
            COL_CAPACIDAD_ASIENTOS,
            COL_PRECIO,
            COL_URI_IMG,
            COL_DESCRIPCION,
            COL_IDMARCAS,
            COL_IDTIPOAUTOMOVIL,
            COL_IDCOLORES
        )
        return db!!.query(
            TABLE_NAME_AUTOMOVIL, columns,
            null, null, null, null, "$COL_DESCRIPCION ASC"
        )
    }

    @SuppressLint("Range")
    fun showAllList(): ArrayList<AutomovilModel> {
        val modelList: ArrayList<AutomovilModel> = ArrayList()
        val columns = arrayOf(
            COL_ID,
            COL_MODELO,
            COL_NUMERO_VIN,
            COL_NUMERO_CHASIS,
            COL_NUMERO_MOTOR,
            COL_NUMERO_ASIENTOS,
            COL_ANIO,
            COL_CAPACIDAD_ASIENTOS,
            COL_PRECIO,
            COL_URI_IMG,
            COL_DESCRIPCION,
            COL_IDMARCAS,
            COL_IDTIPOAUTOMOVIL,
            COL_IDCOLORES
        )
        val cursor: Cursor = db!!.query(
            TABLE_NAME_AUTOMOVIL, columns,
            null, null, null, null, "$COL_MODELO ASC"
        )

        var id: Int
        var modelo: String
        var numero_vin: String
        var numero_chasis: String
        var numero_motor: String
        var numero_asientos: Int
        var anio: Int
        var capacidad_asientos: Int
        var precio: Double
        var URI_IMG: String
        var descripcion: String
        var idmarcas: Int
        var idtipoautomovil: Int
        var idcolores: Int

        if (cursor.moveToFirst()) {
            do {
                id = cursor.getInt(cursor.getColumnIndex("idautomovil"))
                modelo = cursor.getString(cursor.getColumnIndex("modelo"))
                numero_vin = cursor.getString(cursor.getColumnIndex("numero_vin"))
                numero_chasis = cursor.getString(cursor.getColumnIndex("numero_chasis"))
                numero_motor = cursor.getString(cursor.getColumnIndex("numero_motor"))
                numero_asientos = cursor.getInt(cursor.getColumnIndex("numero_asientos"))
                anio = cursor.getInt(cursor.getColumnIndex("anio"))
                capacidad_asientos = cursor.getInt(cursor.getColumnIndex("capacidad_asientos"))
                precio = cursor.getDouble(cursor.getColumnIndex("precio"))
                URI_IMG = cursor.getString(cursor.getColumnIndex("URI_IMG"))
                descripcion = cursor.getString(cursor.getColumnIndex("descripcion"))
                idmarcas = cursor.getInt(cursor.getColumnIndex("idmarcas"))
                idtipoautomovil = cursor.getInt(cursor.getColumnIndex("idtipoautomovil"))
                idcolores = cursor.getInt(cursor.getColumnIndex("idcolores"))


                val autosModel = AutomovilModel(
                    id = id,
                    modelo = modelo,
                    numero_vin = numero_vin,
                    numero_chasis = numero_chasis,
                    numero_motor = numero_motor,
                    numero_asientos = numero_asientos,
                    anio = anio,
                    capacidad_asientos = capacidad_asientos,
                    precio = precio, //Decimal de 2 decimales
                    URI_IMG = URI_IMG,
                    descripcion = descripcion,
                    idmarcas = idmarcas,
                    idtipoautomovil = idtipoautomovil,
                    idcolores = idcolores
                )
                modelList.add(autosModel)
            } while (cursor.moveToNext())
        }
        return modelList
    }

    //Por si deseamos que nos imprima SOLO LA DESCRIPCION, que encontremos con un id
    fun searchID(id: Int): Cursor? {
        val columns = arrayOf(
            COL_ID,
            COL_MODELO,
            COL_NUMERO_VIN,
            COL_NUMERO_CHASIS,
            COL_NUMERO_MOTOR,
            COL_NUMERO_ASIENTOS,
            COL_ANIO,
            COL_CAPACIDAD_ASIENTOS,
            COL_PRECIO,
            COL_URI_IMG,
            COL_DESCRIPCION,
            COL_IDMARCAS,
            COL_IDTIPOAUTOMOVIL,
            COL_IDCOLORES
        )
        var cursor: Cursor? = db!!.query(
            TABLE_NAME_AUTOMOVIL, columns,
            "$COL_ID=?", arrayOf(id.toString()), null, null, null
        )
        return cursor
    }

    // Eliminar un registro
    fun deleteAuto(id: Int) {
        db!!.delete(TABLE_NAME_AUTOMOVIL, "$COL_ID=?", arrayOf(id.toString()))
    }
}