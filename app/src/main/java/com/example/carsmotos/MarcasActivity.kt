package com.example.carsmotos

import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.os.Bundle
import android.util.Log
import android.widget.ListView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.carsmotos.adapters.MarcaAdapter
import com.example.carsmotos.db.HelperDB
import com.example.carsmotos.model.Marcas
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MarcasActivity: AppCompatActivity() {

    //Variables de la Base de Datos
    private var managerMarcas: Marcas? = null
    private var dbHelper : HelperDB? = null
    private var db: SQLiteDatabase? = null
    private var cursor: Cursor? = null

    //Variables del formulario
    private lateinit var btnAgregarMarca: FloatingActionButton
    var marcas: MutableList<Marcas>? = null
    var listMarcas: ListView? = null

    /*var marcasREAD: Query = InvoiceActivity.refInvoices.orderByChild("fecha")
    var invoices: MutableList<Invoice>? = null
    var listInvoices: ListView? = null*/


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.marcas_activity)

        //Declarando la base de datos
        dbHelper = HelperDB(this)
        db = dbHelper!!.writableDatabase

        //Declarando objetos en el formulario
        btnAgregarMarca = findViewById(R.id.btnAgregarMarca)
        listMarcas = findViewById(R.id.listMarcas)



        //Buscando los valores para la lista
        managerMarcas = Marcas(this)

        //Verificando si hay conexion a la bdd
        if (db != null) {

            //Revisando si existe el usuario que quiero registar
            cursor = managerMarcas!!.showAllMarcas()

            if (cursor != null && cursor!!.count > 0) {
                cursor!!.moveToFirst()

                Log.d("MARCAS-ACTIVITY",cursor!!.getString(1))

                /*val adapter = MarcaAdapter(
                    this@MarcasActivity,
                    marcas as ArrayList<Marcas>
                )
                listMarcas!!.adapter = adapter*/

                /*val marcasList = cursor?.let {
                    val list = ArrayList<Marcas>()
                    while (it.moveToNext()) {
                        val marca = Marcas(
                            it.getInt(it.getColumnIndexOrThrow(COL_ID),
                            it.getString(it.getColumnIndexOrThrow(COL_NOMBRE))
                        )
                        list.add(marca)
                    }
                    list
                } ?: ArrayList<Marcas>()

                var marcasAdapter = MarcaAdapter(this, marcasList)
                listMarcas!!.adapter = marcasAdapter*/

                //Guardando en variables la información del usuario con el que ingreso
                /*
                var userIDLog: Int? = managerMarcas!!.searchUserID(cursor!!.getInt(0))
                Log.d("LOGIN",userIDLog.toString())
                 */

                //Encontrando los valores anexados a esa ID de usuario
                /*
                val (email, tipo) = managerUsuarios!!.informacionUsuario(userIDLog)
                val emailLog: String = email.toString()
                val tipoLog: String = tipo.toString()
                */

                /*
                //Abrimos la actividad principal
                Toast.makeText(this, "Bienvenido a CarsMotors", Toast.LENGTH_LONG).show()
                val intent = Intent(this, MainActivity::class.java)
                intent.putExtra("userIDLog",userIDLog) //Int
                intent.putExtra("emailLog",emailLog)
                intent.putExtra("tipoLog",tipoLog)
                startActivity(intent) */

            } else {
                Toast.makeText(this, "No se encontraron marcas", Toast.LENGTH_LONG).show()
            }

            marcas = ArrayList<Marcas>()

        } else {
            Toast.makeText(this, "No se puede conectar a la Base de Datos", Toast.LENGTH_LONG).show()
        }

        //Al darle clic a un valor de la lista


        //Al agregar una marca
        btnAgregarMarca.setOnClickListener {


        }

    }


}