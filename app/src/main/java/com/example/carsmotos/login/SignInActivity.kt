package com.example.carsmotos.login

import android.content.Intent
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.carsmotos.MainActivity
import com.example.carsmotos.R
import com.example.carsmotos.db.HelperDB
import com.example.carsmotos.model.Usuarios

class SignInActivity : AppCompatActivity() {

    //Database variables
    private var managerUsuarios: Usuarios? = null
    private var dbHelper : HelperDB? = null
    private var db: SQLiteDatabase? = null
    private var cursorUsuarios: Cursor? = null

    //Login Activity Variables
    lateinit var txtEmailLogin : EditText
    lateinit var txtPasswordLogin : EditText
    lateinit var btnLogin : Button
    lateinit var btnlbRegister : TextView
    private val emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login_activity)

        //Declarando componentes del login
        txtEmailLogin = findViewById(R.id.txtEmailLogin)
        txtPasswordLogin = findViewById(R.id.txtPasswordLogin)
        btnLogin = findViewById(R.id.btnLogin)
        btnlbRegister = findViewById(R.id.btnlbRegister)

       //Declarando la base de datos
        dbHelper = HelperDB(this)
        db = dbHelper!!.writableDatabase

        //Al darle click al boton Ingresar
        btnLogin.setOnClickListener{
            ingresar()
        }

        //Al darle click al textView Registrarse
        btnlbRegister.setOnClickListener{
            intent = Intent(this, SignUpActivity::class.java)
            startActivity(intent)
        }

    }

    fun ingresar(){
        val email : String = txtEmailLogin.text.toString()
        val password : String = txtPasswordLogin.text.toString()

        //Validando que los campos no esten vacios
        if (email.isEmpty() == true){
            Toast.makeText(this,"El campo de email esta vacio", Toast.LENGTH_SHORT).show()
        }  else if(password.isEmpty() == true){
            Toast.makeText(this,"El campo de contraseña esta vacio", Toast.LENGTH_SHORT).show()
        } else if(!email.matches(emailPattern.toRegex())){ //Formato de correo valido
            Toast.makeText(this,"El email digitado no es válido",Toast.LENGTH_SHORT).show()
        } else {
            managerUsuarios = Usuarios(this)
            //Verificando si hay conexion a la bdd
            if (db != null) {

                //Revisando si existe el usuario que quiero registar
                cursorUsuarios = managerUsuarios!!.searchUsuarioEmail(email)

                if (cursorUsuarios != null && cursorUsuarios!!.count > 0) {
                    cursorUsuarios!!.moveToFirst()
                    Log.d("LOGIN",cursorUsuarios!!.getString(0)) //ID usuario
                    Log.d("LOGIN",cursorUsuarios!!.getString(1)) //Correo
                    Log.d("LOGIN",cursorUsuarios!!.getString(2)) //Tipo

                    //Guardando en variables la información del usuario con el que ingreso
                    var userIDLog: String = cursorUsuarios!!.getString(0)
                    val emailLog: String = cursorUsuarios!!.getString(1)
                    val tipoLog: String = cursorUsuarios!!.getString(2)

                    //Abrimos la actividad principal
                    Toast.makeText(this, "Bienvenido a CarsMotors", Toast.LENGTH_LONG).show()
                    val intent = Intent(this, MainActivity::class.java)
                    intent.putExtra("userIDLog",userIDLog) //Int
                    intent.putExtra("emailLog",emailLog)
                    intent.putExtra("tipoLog",tipoLog)
                    startActivity(intent)

                } else {
                    Toast.makeText(this, "Usuario o Contraseña son incorrectos", Toast.LENGTH_LONG).show()
                }

            } else {
                Toast.makeText(this, "No se puede conectar a la Base de Datos", Toast.LENGTH_LONG).show()
            }


        }


    }




}