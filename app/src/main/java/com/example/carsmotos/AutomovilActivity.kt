package com.example.carsmotos

import android.content.Intent
import android.database.sqlite.SQLiteDatabase
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.carsmotos.adapters.AutomovilAdapter
import com.example.carsmotos.classes.AutomovilModel
import com.example.carsmotos.db.HelperDB
import com.example.carsmotos.model.Automoviles
import com.google.android.material.floatingactionbutton.FloatingActionButton

class AutomovilActivity: AppCompatActivity() {
    //Variables de la Base de Datos
    private var managerAuto: Automoviles? = null
    private var dbHelper : HelperDB? = null
    private var db: SQLiteDatabase? = null

    //Variables del formulario
    private lateinit var btnAgregarAuto: FloatingActionButton
    private lateinit var btnRegresarAuto: FloatingActionButton
    private lateinit var listAutos: RecyclerView
    private var adapter: AutomovilAdapter? = null
    private var aut: AutomovilModel? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.automovil_activity)

        //Inicializando componentes y la lista
        inicializarView()
        inicializarRecyclerView()

        //Declarando la base de datos
        dbHelper = HelperDB(this)
        db = dbHelper!!.writableDatabase
        //Buscando los valores para la lista de todos los AUTOMOVILES
        managerAuto = Automoviles(this)
        val autList = managerAuto!!.showAllList()

        //Desplegando la informacion en el RecyclerView
        adapter?.addItems(autList)

        //Al darle click a algun boton de la lista para editar datos
        adapter?.setOnClickItem {
            //Enviamos los valores de importancia al CRUD Automoviles
            val id = it.id.toString()
            val modelo = it.modelo //El resto de campos los voy a buscar con el SEARCHID en el AutomovilesCRUD
            aut = it
            val opc = "editar"
            val intent = Intent(this, AutomovilCRUD::class.java)
            intent.putExtra("opc",opc)
            intent.putExtra("id",id)
            startActivity(intent)
            recreate()
        }

        //Al agregar un valor
        btnAgregarAuto.setOnClickListener {
            //Le envio como "putExtra" la opcion de AGREGAR, porque al inicio de la actividad AutomovilesCRUD,
            //para que en la actividad AutomovilesCRUD solo le quito las opciones segun la "opc" recibida
            val opc = "agregar"
            val intent = Intent(this, AutomovilCRUD::class.java)
            intent.putExtra("opc",opc)
            startActivity(intent)
        }

        //Al darle click a eliminar un valor
        adapter?.setOnClickDeleteItem {
            managerAuto!!.deleteAuto(it.id)
            Toast.makeText(this, "Automovil eliminado", Toast.LENGTH_LONG).show()
            recreate()
        }

        //Al darle click al boton regresar
        btnRegresarAuto.setOnClickListener{
            finish()
        }

    }

    private fun inicializarRecyclerView(){
        listAutos.layoutManager = LinearLayoutManager(this)
        adapter = AutomovilAdapter()
        listAutos.adapter = adapter
    }
    private fun inicializarView(){
        //Declarando objetos en el formulario
        btnAgregarAuto = findViewById(R.id.btnAgregarAuto)
        btnRegresarAuto = findViewById(R.id.btnRegresarAuto)
        listAutos = findViewById(R.id.listAutos)
    }







}