package com.example.carsmotos

import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.carsmotos.db.HelperDB
import com.example.carsmotos.model.Usuarios

class Login : AppCompatActivity() {

    //Database variables
    private var managerUsuarios: Usuarios? = null
    private var dbHelper : HelperDB? = null
    private var db: SQLiteDatabase? = null
    private var cursor: Cursor? = null

    //Login Activity Variables

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login_activity)


        //Declarando componentes del login



       //Declarando la base de datos
        dbHelper = HelperDB(this)
        db = dbHelper!!.writableDatabase


    }
}