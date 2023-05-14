package com.example.carsmotos.adapters

import android.app.Activity
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import com.example.carsmotos.R
import com.example.carsmotos.db.HelperDB
import com.example.carsmotos.model.Marcas

class MarcaAdapter(private val context: Activity, private var marcas: List<Marcas>) : ArrayAdapter<Marcas?>(context, R.layout.marcas_layout,marcas) {


    //Database variables
    private var dbHelper : HelperDB? = null
    private var db: SQLiteDatabase? = null
    private var managerMarcas: Marcas? = null
    private var cursorMarcas: Cursor? = null
    private lateinit var txtMarca: TextView


    override fun getView(position: Int, view: View?, parent: ViewGroup): View {
        //Declaramos los valores para leer a la bdd
        managerMarcas = Marcas(context)
        cursorMarcas = managerMarcas!!.showAllMarcas() //query con el que leemos los datos de la Base de datos

        // Método invocado tantas veces como elementos tenga la coleccion
        val layoutInflater = context.layoutInflater
        var rowview: View? = null
        rowview = view ?: layoutInflater.inflate(R.layout.marcas_layout, null)
        txtMarca.text = cursorMarcas!!.getString(1)

        return rowview!!

    }

}