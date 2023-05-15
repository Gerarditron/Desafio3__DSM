package com.example.carsmotos

import android.content.Intent
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.carsmotos.adapters.MarcaAdapter
import com.example.carsmotos.classes.MarcaModel
import com.example.carsmotos.db.HelperDB
import com.example.carsmotos.model.Marcas
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlin.String as String
import kotlin.collections.MutableList as MutableList

class MarcasActivity: AppCompatActivity() {

    //Variables de la Base de Datos
    private var managerMarcas: Marcas? = null
    private var dbHelper : HelperDB? = null
    private var db: SQLiteDatabase? = null

    //Variables del formulario
    private lateinit var btnRegresarMarca: FloatingActionButton
    private lateinit var btnAgregarMarca: FloatingActionButton
    private lateinit var listMarcas: RecyclerView
    private var adapter: MarcaAdapter? = null
    private var mrc: MarcaModel? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.marcas_activity)

        //Inicializando componentes y la lista
        inicializarView()
        inicializarRecyclerView()

        //Declarando la base de datos
        dbHelper = HelperDB(this)
        db = dbHelper!!.writableDatabase
        //Buscando los valores para la lista de todas las MARCAS
        managerMarcas = Marcas(this)
        val mrcList = managerMarcas!!.showAllMarcasList()

        //Desplegando la informacion en el RecyclerView
        adapter?.addItems(mrcList)

        //Al darle click a algun boton de la lista para editar datos
        adapter?.setOnClickItem {
            //Toast.makeText(this, "Id: ${it.id} //Marca: ${it.nombre}",Toast.LENGTH_SHORT).show()
            //Enviamos los valores de importancia al CRUD Marcas
            val idmarca = it.id.toString()
            val nombremarca = it.nombre
            mrc = it
            val opc = "editar"
            val intent = Intent(this, MarcasCRUD::class.java)
            intent.putExtra("opc",opc)
            intent.putExtra("idmarca",idmarca)
            intent.putExtra("nombremarca",nombremarca)
            startActivity(intent)
            recreate()
        }

        //Al agregar un valor
        btnAgregarMarca.setOnClickListener {
            //Le envio como "putExtra" la opcion de AGREGAR, porque al inicio de la actividad MarcasCRUD,
            //para que en la actividad MarcaCRUD solo le quito las opciones segun la "opc" recibida
            val opc = "agregar"
            val intent = Intent(this, MarcasCRUD::class.java)
            intent.putExtra("opc",opc)
            startActivity(intent)
        }

        //Al darle click a eliminar un valor
        adapter?.setOnClickDeleteItem {
            managerMarcas!!.deleteMarca(it.id)
            Toast.makeText(this, "Marca eliminada", Toast.LENGTH_LONG).show()
            recreate()
        }

        //Al darle click al boton regresar
        btnRegresarMarca.setOnClickListener{
            finish()
        }

    }

    private fun inicializarRecyclerView(){
        listMarcas.layoutManager = LinearLayoutManager(this)
        adapter = MarcaAdapter()
        listMarcas.adapter = adapter
    }
    private fun inicializarView(){
        //Declarando objetos en el formulario
        btnRegresarMarca = findViewById(R.id.btnRegresarMarca)
        btnAgregarMarca = findViewById(R.id.btnAgregarMarca)
        listMarcas = findViewById(R.id.listMarcas)
    }



}