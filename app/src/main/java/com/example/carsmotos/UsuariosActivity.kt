package com.example.carsmotos

import android.content.Intent
import android.database.sqlite.SQLiteDatabase
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.carsmotos.adapters.UsuariosAdapter
import com.example.carsmotos.classes.UsuarioModel
import com.example.carsmotos.db.HelperDB
import com.example.carsmotos.model.Usuarios
import com.google.android.material.floatingactionbutton.FloatingActionButton

class UsuariosActivity: AppCompatActivity()  {

    //Variables de la Base de Datos
    private var managerUsuarios: Usuarios? = null
    private var dbHelper : HelperDB? = null
    private var db: SQLiteDatabase? = null

    //Variables del formulario
    private lateinit var btnAgregarUser: FloatingActionButton
    private lateinit var btnRegresarUser: FloatingActionButton
    private lateinit var listUsers: RecyclerView
    private var adapter: UsuariosAdapter? = null
    private var usr: UsuarioModel? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.user_activity)

        //Inicializando componentes y la lista
        inicializarView()
        inicializarRecyclerView()

        //Declarando la base de datos
        dbHelper = HelperDB(this)
        db = dbHelper!!.writableDatabase
        //Buscando los valores para la lista de todos los USUARIOS
        managerUsuarios = Usuarios(this)
        val usrList = managerUsuarios!!.showAllList()

        //Desplegando la informacion en el RecyclerView
        adapter?.addItems(usrList)

        //Al darle click a algun boton de la lista para editar datos
        adapter?.setOnClickItem {
            //Enviamos los valores de importancia al CRUD Usuarios
            val id = it.id.toString()
            val nombres = it.nombres //El resto de campos los voy a buscar con el SEARCHID en el UsuariosCRUD
            usr = it
            val opc = "editar"
            val intent = Intent(this, UsuariosCRUD::class.java)
            intent.putExtra("opc",opc)
            intent.putExtra("id",id)
            startActivity(intent)
            recreate()
        }

        //Al agregar un valor
        btnAgregarUser.setOnClickListener {
            //Le envio como "putExtra" la opcion de AGREGAR, porque al inicio de la actividad UsuariosCRUD,
            //para que en la actividad UsuariuosCRUD solo le quito las opciones segun la "opc" recibida
            val opc = "agregar"
            val intent = Intent(this, UsuariosCRUD::class.java)
            intent.putExtra("opc",opc)
            startActivity(intent)
        }

        //Al darle click a eliminar un valor
        adapter?.setOnClickDeleteItem {
            managerUsuarios!!.deleteUser(it.id)
            Toast.makeText(this, "Usuario eliminado", Toast.LENGTH_LONG).show()
            recreate()
        }

        //Al darle click al boton regresar
        btnRegresarUser.setOnClickListener{
            finish()
        }

    }

    private fun inicializarRecyclerView(){
        listUsers.layoutManager = LinearLayoutManager(this)
        adapter = UsuariosAdapter()
        listUsers.adapter = adapter
    }
    private fun inicializarView(){
        //Declarando objetos en el formulario
        btnRegresarUser = findViewById(R.id.btnRegresarUser)
        btnAgregarUser = findViewById(R.id.btnAgregarUser)
        listUsers = findViewById(R.id.listUsers)
    }




}