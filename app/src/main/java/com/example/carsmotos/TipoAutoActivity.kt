package com.example.carsmotos

import android.content.Intent
import android.database.sqlite.SQLiteDatabase
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.carsmotos.adapters.TipoAutoAdapter
import com.example.carsmotos.classes.TipoAutomovilModel
import com.example.carsmotos.db.HelperDB
import com.example.carsmotos.model.TiposAutomoviles
import com.google.android.material.floatingactionbutton.FloatingActionButton

class TipoAutoActivity : AppCompatActivity() {
    //Variables de la Base de Datos
    private var managerTipoAuto: TiposAutomoviles? = null
    private var dbHelper : HelperDB? = null
    private var db: SQLiteDatabase? = null

    //Variables del formulario
    private lateinit var btnRegresarTipoAuto: FloatingActionButton
    private lateinit var btnAgregarTipoAuto: FloatingActionButton
    private lateinit var listTipoAuto: RecyclerView
    private var adapter: TipoAutoAdapter? = null
    private var tpa: TipoAutomovilModel? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.tipoauto_activity)

        //Inicializando componentes y la lista
        inicializarView()
        inicializarRecyclerView()

        //Declarando la base de datos
        dbHelper = HelperDB(this)
        db = dbHelper!!.writableDatabase
        //Buscando los valores para la lista de todos los TIPOS DE AUTO
        managerTipoAuto = TiposAutomoviles(this)
        val tpaList = managerTipoAuto!!.showAllList()

        //Desplegando la informacion en el RecyclerView
        adapter?.addItems(tpaList)

        //Al darle click a algun boton de la lista para editar datos
        adapter?.setOnClickItem {
            //Enviamos los valores de importancia al CRUD
            val id = it.id.toString()
            val descripcion = it.descripcion
            tpa = it
            val opc = "editar"
            val intent = Intent(this, TipoAutoCRUD::class.java)
            intent.putExtra("opc",opc)
            intent.putExtra("id",id)
            intent.putExtra("descripcion",descripcion)
            startActivity(intent)

        }

        //Al agregar un valor
        btnAgregarTipoAuto.setOnClickListener {
            //Le envio como "putExtra" la opcion de AGREGAR, porque al inicio de la actividad TipoAutoCRUD,
            //para que en la actividad TipoAutoCRUD solo le quito las opciones segun la "opc" recibida
            val opc = "agregar"
            val intent = Intent(this, TipoAutoCRUD::class.java)
            intent.putExtra("opc",opc)
            startActivity(intent)
            recreate()
        }

        //Al darle click a eliminar un valor
        adapter?.setOnClickDeleteItem {
            managerTipoAuto!!.deleteTipoAutomovil(it.id)
            Toast.makeText(this, "Tipo de Automovil eliminado", Toast.LENGTH_LONG).show()
            recreate()
        }

        //Al darle click al boton de regresar
        btnRegresarTipoAuto.setOnClickListener{
            finish()
        }
    }

    private fun inicializarRecyclerView(){
        listTipoAuto.layoutManager = LinearLayoutManager(this)
        adapter = TipoAutoAdapter()
        listTipoAuto.adapter = adapter
    }
    private fun inicializarView(){
        //Declarando objetos en el formulario
        btnRegresarTipoAuto = findViewById(R.id.btnRegresarTipoAuto)
        btnAgregarTipoAuto = findViewById(R.id.btnAgregarTipoAuto)
        listTipoAuto = findViewById(R.id.listTipoAuto)
    }






}