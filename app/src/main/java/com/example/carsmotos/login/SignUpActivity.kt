package com.example.carsmotos.login

import android.content.Intent
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.carsmotos.MainActivity
import com.example.carsmotos.R
import com.example.carsmotos.db.HelperDB
import com.example.carsmotos.model.Usuarios

class SignUpActivity : AppCompatActivity(){

    //Database variables
    private var managerUsuarios: Usuarios? = null
    private var dbHelper : HelperDB? = null
    private var db: SQLiteDatabase? = null
    private var cursor: Cursor? = null
    private var cursorU: Cursor? = null //Para buscar la informacion del usuario en la BDD despues de creado

    //Login Activity Variables
    lateinit var txtNombre: EditText
    lateinit var txtApellido: EditText
    lateinit var txtUserRegister: EditText
    lateinit var txtEmailRegister : EditText
    private val emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+"
    lateinit var txtPasswordRegister : EditText
    lateinit var txtPasswordRegisterConfirm : EditText
    lateinit var btnRegister : Button
    lateinit var btnBackRegister : TextView
    private var tipoSel: String = "CLIENTE"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.register_activity)

        //Creando los valores de la bdd
        dbHelper = HelperDB(this)
        db = dbHelper!!.writableDatabase

        //Inicializando componentes del Register
        txtNombre = findViewById(R.id.txtNombre)
        txtApellido = findViewById(R.id.txtApellido)
        txtUserRegister = findViewById(R.id.txtUserRegister)
        txtEmailRegister = findViewById(R.id.txtEmailRegister)
        txtPasswordRegister = findViewById(R.id.txtPasswordRegister)
        txtPasswordRegisterConfirm = findViewById(R.id.txtPasswordRegisterConfirm)
        btnRegister = findViewById(R.id.btnRegister)
        btnBackRegister = findViewById(R.id.btnBackRegister)






        //Al presionar el boton Registrar
        btnRegister.setOnClickListener {
            registerNewUser()
        }

        //Al presionar el boton de Cancelar
        btnBackRegister.setOnClickListener{
            finish()
        }

    }

    fun registerNewUser(){
        val nombre : String = txtNombre.text.toString()
        val apellido : String= txtApellido.text.toString()
        val email : String = txtEmailRegister.text.toString()
        val user : String = txtUserRegister.text.toString()
        val password : String = txtPasswordRegister.text.toString()
        val passwordConfirm :String = txtPasswordRegisterConfirm.text.toString()
        val tipo :String = tipoSel

        //Validando campos vacios
        if (nombre.isEmpty() == true){
            Toast.makeText(this,"El campo de nombre esta vacio",Toast.LENGTH_SHORT).show()
        } else if (apellido.isEmpty() == true){
            Toast.makeText(this,"El campo de apellido esta vacio",Toast.LENGTH_SHORT).show()
        } else if(user.isEmpty() == true){
            Toast.makeText(this,"El campo de usuario esta vacio",Toast.LENGTH_SHORT).show()
        } else if (email.isEmpty() == true){
            Toast.makeText(this,"El campo de email esta vacio",Toast.LENGTH_SHORT).show()
        }  else if(password.isEmpty() == true){
            Toast.makeText(this,"El campo de contraseña esta vacio",Toast.LENGTH_SHORT).show()
        } else if(passwordConfirm.isEmpty() == true){
            Toast.makeText(this,"El campo de confirmar contraseña esta vacio",Toast.LENGTH_SHORT).show()
        } else if(!email.matches(emailPattern.toRegex())){ //Formato de correo valido
            Toast.makeText(this,"El email digitado no es válido",Toast.LENGTH_SHORT).show()
        } else if(password != passwordConfirm){
            Toast.makeText(this,"Las contraseñas no coinciden",Toast.LENGTH_SHORT).show()
        } else {
            //Estan validos los campos escritos
            //Validar que no exista el usuario/correo que estoy intenando ingresar al sistema
            managerUsuarios = Usuarios(this)

            //Verificando si hay conexion a la bdd
            if (db != null) {

                //Revisando si existe el usuario que quiero registar
                cursor = managerUsuarios!!.searchUsuarioEmail(email)

                //Encontro algo
                if (cursor != null && cursor!!.count > 0) {
                    cursor!!.moveToFirst()

                    //Evaluando si existe el correo recien digitado ya existe
                    Log.d("SIGNUP",cursor!!.getString(1)) //Correo

                    if(!cursor!!.getString(1).isEmpty() || cursor!!.getString(1) != null){
                        Toast.makeText(this, "El correo que desea crear con este usuario ya existe en la aplicacion", Toast.LENGTH_LONG).show()
                    }

                } else {
                    //Creo el usuario
                    managerUsuarios!!.addNewUser(
                        nombre,
                        apellido,
                        email,
                        user,
                        password,
                        tipo
                    )
                    Toast.makeText(this, "Usuario Nuevo Registrado", Toast.LENGTH_LONG).show()
                    //Despues busco el ID de usuario que recien se acaba de crear
                    //Sobreescribimos el cursor y buscamos la respectiva informacion de nuestro nuevo usuario
                    cursorU = managerUsuarios!!.searchUsuarioEmail(email)
                    if (cursorU != null && cursorU!!.count > 0) {
                        cursorU!!.moveToFirst()
                        Log.d("SIGNUP",cursorU!!.getString(0)) //ID usuario
                        Log.d("SIGNUP",cursorU!!.getString(1)) //Correo
                        Log.d("SIGNUP",cursorU!!.getString(2)) //Tipo

                        //Guardando en variables la información del usuario con el que ingreso
                        var userIDLog: String = cursorU!!.getString(0)
                        val emailLog: String = cursorU!!.getString(1)
                        val tipoLog: String = cursorU!!.getString(2)

                        //Abrimos la actividad principal
                        Toast.makeText(this, "Bienvenido a CarsMotors", Toast.LENGTH_LONG).show()
                        val intent = Intent(this, MainActivity::class.java)
                        intent.putExtra("userIDLog",userIDLog) //Int
                        intent.putExtra("emailLog",emailLog)
                        intent.putExtra("tipoLog",tipoLog)
                        startActivity(intent)

                    } else {
                        //Si no abrio automaticamente ni modo, a probar desde el Login
                        Toast.makeText(this, "Ingrese con su correo: $email en el respectivo Log In", Toast.LENGTH_LONG).show()
                        val intent = Intent(this, SignInActivity::class.java)
                        startActivity(intent)
                    }

                }

            } else {
                Toast.makeText(this, "No se puede conectar a la Base de Datos", Toast.LENGTH_LONG).show()
            }

        }

    }



    


}