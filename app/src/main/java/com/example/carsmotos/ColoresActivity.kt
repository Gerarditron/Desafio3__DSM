package com.example.carsmotos

import android.content.Intent
import android.database.sqlite.SQLiteDatabase
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.carsmotos.adapters.ColoresAdapter
import com.example.carsmotos.classes.ColoresModel
import com.example.carsmotos.db.HelperDB
import com.example.carsmotos.model.Colores
import com.google.android.material.floatingactionbutton.FloatingActionButton

class ColoresActivity : AppCompatActivity() {

    //Variables de la Base de Datos
    private var managerColores: Colores? = null
    private var dbHelper : HelperDB? = null
    private var db: SQLiteDatabase? = null

    //Variables del formulario
    private lateinit var btnRegresarColor: FloatingActionButton
    private lateinit var btnAgregarColor: FloatingActionButton
    private lateinit var listColores: RecyclerView
    private var adapter: ColoresAdapter? = null
    private var clr: ColoresModel? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.colores_activity)

        //Inicializando componentes y la lista
        inicializarView()
        inicializarRecyclerView()

        //Declarando la base de datos
        dbHelper = HelperDB(this)
        db = dbHelper!!.writableDatabase
        //Buscando los valores para la lista de todos los COLORES
        managerColores = Colores(this)
        val clrList = managerColores!!.showAllList()

        //Desplegando la informacion en el RecyclerView
        adapter?.addItems(clrList)

        //Al darle click a algun boton de la lista para editar datos
        adapter?.setOnClickItem {
            //Enviamos los valores de importancia al CRUD COLORES
            val id = it.id.toString()
            val descripcion = it.descripcion
            clr = it
            val opc = "editar"
            val intent = Intent(this, ColoresCRUD::class.java)
            intent.putExtra("opc",opc)
            intent.putExtra("id",id)
            intent.putExtra("descripcion",descripcion)
            startActivity(intent)

        }

        //Al agregar un valor
        btnAgregarColor.setOnClickListener {
            //Le envio como "putExtra" la opcion de AGREGAR, porque al inicio de la actividad ColoresCRUD,
            //para que en la actividad ColoresCRUD solo le quito las opciones segun la "opc" recibida
            val opc = "agregar"
            val intent = Intent(this, ColoresCRUD::class.java)
            intent.putExtra("opc",opc)
            startActivity(intent)
            recreate()
        }

        //Al darle click a eliminar un valor
        adapter?.setOnClickDeleteItem {
            managerColores!!.deleteColor(it.id)
            Toast.makeText(this, "Color eliminado", Toast.LENGTH_LONG).show()
            recreate()
        }

        //Al darle click al boton de regresar
        btnRegresarColor.setOnClickListener{
            finish()
        }
    }

    private fun inicializarRecyclerView(){
        listColores.layoutManager = LinearLayoutManager(this)
        adapter = ColoresAdapter()
        listColores.adapter = adapter
    }
    private fun inicializarView(){
        //Declarando objetos en el formulario
        btnRegresarColor = findViewById(R.id.btnRegresarColor)
        btnAgregarColor = findViewById(R.id.btnAgregarColor)
        listColores = findViewById(R.id.listColores)
    }



}

