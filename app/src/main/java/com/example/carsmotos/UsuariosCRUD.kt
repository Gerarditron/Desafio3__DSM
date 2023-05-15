package com.example.carsmotos

import android.content.Intent
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.RadioButton
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import com.example.carsmotos.db.HelperDB
import com.example.carsmotos.model.Usuarios

class UsuariosCRUD : AppCompatActivity() {

    //Database variables
    private var dbHelper: HelperDB? = null
    private var db: SQLiteDatabase? = null
    private var managerUsuarios: Usuarios? = null
    private var cursorUserFound: Cursor? = null

    //UsuariosCRUD Activity Variables
    private lateinit var txtNombreUserAdmin: TextView
    private lateinit var txtApellidoUserAdmin: TextView
    private lateinit var txtEmailUserAdmin: TextView
    private val emailAdminPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+"
    private lateinit var txtUserAdmin: TextView
    private lateinit var txtPassworUserAdmin: TextView
    private lateinit var rdAdministradorAdmin: RadioButton
    private lateinit var rdClienteAdmin: RadioButton
    private var tipoSel: String = "ADMIN"
    private lateinit var btnAddUser: Button
    private lateinit var btnUpdateUser: Button
    private lateinit var btnCancelUser: Button

    //Enviados desde otra actividad
    private var opc: String? = null
    private var id: String? = null //El resto de valores los voy a encontrar con el SEARCHID

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.admin_usuarios_activity)

        //Declarando la base de datos
        dbHelper = HelperDB(this)
        db = dbHelper!!.writableDatabase
        managerUsuarios = Usuarios(this) //ESTE ES DE VITAL IMPORTANCIA NO OLVIDAR SEGUN LA LECTURA A LA TABLA QUE SE HARA
        //Declarando componentes de la actividad Main
        txtNombreUserAdmin = findViewById(R.id.txtNombreUserAdmin)
        txtApellidoUserAdmin = findViewById(R.id.txtApellidoUserAdmin)
        txtEmailUserAdmin = findViewById(R.id.txtEmailUserAdmin)
        txtUserAdmin = findViewById(R.id.txtUserAdmin)
        txtPassworUserAdmin = findViewById(R.id.txtPassworUserAdmin)
        rdAdministradorAdmin = findViewById(R.id.rdAdministradorAdmin)
        rdClienteAdmin = findViewById(R.id.rdClienteAdmin)
        btnAddUser = findViewById(R.id.btnAddUser)
        btnUpdateUser = findViewById(R.id.btnUpdateUser)
        btnCancelUser = findViewById(R.id.btnCancelUser)

        //Recogiendo los datos traidos de la actividad anterior
        val datos: Bundle? = intent.getExtras()
        if (datos != null) {
            //Recibimos los datos de la actividad anterior
            opc = intent.getStringExtra("opc").toString()

            //Activamos y desactivamos botones segun el valor que haya sido enviado como "opc"
            if (opc == "agregar") {
                //No se puede actualizar un valor que no existe aun
                btnUpdateUser.isEnabled = false
                btnUpdateUser.isVisible = false
                btnUpdateUser.isClickable = false
            } else if (opc == "editar") {
                //No se puede agregar un nuevo valor del que se esta actualizando
                btnAddUser.isEnabled = false
                btnAddUser.isVisible = false
                btnAddUser.isClickable = false
                //Donde guardaremos los valores enviados desde la bdd
                var nombre: String? = ""
                var apellido: String? = ""
                var email: String? = ""
                var user: String? = ""
                var password: String? = ""
                var tipo: String? = ""

                //Importamos lo que se envia desde la otra actividad
                id = intent.getStringExtra("id").toString()
                var idFound: String? = "1"
                //Buscamos toda la informacion que este relacionada a este usuario en la BDD
                if (db != null) {
                    //Buscamos todos los valores que esten relacionados al ID del usuario compartido
                    cursorUserFound = managerUsuarios!!.searchID(id!!.toInt())

                    if (cursorUserFound != null && cursorUserFound!!.count > 0) {
                        cursorUserFound!!.moveToFirst()
                        idFound = cursorUserFound!!.getString(0).toString()
                        nombre = cursorUserFound!!.getString(1).toString()
                        apellido = cursorUserFound!!.getString(2).toString()
                        email = cursorUserFound!!.getString(3).toString()
                        user = cursorUserFound!!.getString(4).toString()
                        password = cursorUserFound!!.getString(5).toString()
                        tipo = cursorUserFound!!.getString(6).toString()

                    } else {
                        Toast.makeText(
                            this,
                            "No se logro encontrar un usuario con esa ID en la bdd",
                            Toast.LENGTH_SHORT
                        ).show()
                        finish()
                    }
                } else {
                    Toast.makeText(this, "No se logro conectar con la bdd", Toast.LENGTH_SHORT)
                        .show()
                    finish()
                }

                //Actualizamos el valor mostrado en el textbox
                txtNombreUserAdmin.text = nombre
                txtApellidoUserAdmin.text = apellido
                txtEmailUserAdmin.text = email
                txtUserAdmin.text = user
                txtPassworUserAdmin.text = password
                if (tipo == "ADMIN") {
                    tipoSel = "ADMIN"
                    rdAdministradorAdmin.isChecked = true
                    rdClienteAdmin.isChecked = false
                } else {
                    tipoSel = "CLIENTE"
                    rdAdministradorAdmin.isChecked = false
                    rdClienteAdmin.isChecked = true
                }

            }

        } else { //Hubo un error encontrando los datos del usuario
            Toast.makeText(
                this,
                "Hubo un error cargando la OPC enviada en el UsersActivity",
                Toast.LENGTH_SHORT
            ).show()
            finish()
        }


        //AGREGAR
        btnAddUser.setOnClickListener {
            //Guardamos en una variables lo que este en txt

            var nombre = txtNombreUserAdmin.text.toString()
            var apellido = txtApellidoUserAdmin.text.toString()
            var email = txtEmailUserAdmin.text.toString()
            var user = txtUserAdmin.text.toString()
            var password = txtPassworUserAdmin.text.toString()
            var tipo : String
            if (tipoSel == "CLIENTE"){
                tipo = "CLIENTE"
            } else {
                tipo = "ADMIN"
            }

            //Campos que si estan vacios se llenen con valor por defecto
            if(tipo.isEmpty() || tipo == null) {
                tipo = "ADMIN"
            }
            //Validamos que el campo no este vacio
            if(nombre.isEmpty() || nombre == null){
                Toast.makeText(this, "Digite el nombre del usuario por favor", Toast.LENGTH_LONG).show()
            } else if(apellido.isEmpty() || apellido == null){
                Toast.makeText(this, "Digite el apellido del usuario por favor", Toast.LENGTH_LONG).show()
            } else if(email.isEmpty() || email == null){
                Toast.makeText(this, "Digite el correo del usuario por favor", Toast.LENGTH_LONG).show()
            } else if(!email.matches(emailAdminPattern.toRegex())){ //Formato de correo valido
                Toast.makeText(this,"El email digitado no es válido",Toast.LENGTH_SHORT).show()
            } else if(user.isEmpty() || user == null){
                Toast.makeText(this, "Digite el usuario por favor", Toast.LENGTH_LONG).show()
            } else if(password.isEmpty() || password == null){
                Toast.makeText(this, "Digite la contraseña del usuario por favor", Toast.LENGTH_LONG).show()
            } else {
                managerUsuarios!!.addNewUser(
                    nombre,
                    apellido,
                    email,
                    user,
                    password,
                    tipo
                )
                Toast.makeText(this, "Usuario agregado", Toast.LENGTH_LONG).show()
                intent = Intent(this, UsuariosActivity::class.java)
                startActivity(intent)
            }
        }

        //EDITAR
        btnUpdateUser.setOnClickListener {
            //Guardamos en variables lo que esta en txt
            var nombre = txtNombreUserAdmin.text.toString()
            var apellido = txtApellidoUserAdmin.text.toString()
            var email = txtEmailUserAdmin.text.toString()
            var user = txtUserAdmin.text.toString()
            var password = txtPassworUserAdmin.text.toString()
            var tipo : String
            if (tipoSel == "CLIENTE"){
                tipo = "CLIENTE"
            } else {
                tipo = "ADMIN"
            }
            //Validamos que el campo no este vacio
            if(nombre.isEmpty() || nombre == null){
                Toast.makeText(this, "Digite el nombre del usuario por favor", Toast.LENGTH_LONG).show()
            } else if(apellido.isEmpty() || apellido == null){
                Toast.makeText(this, "Digite el apellido del usuario por favor", Toast.LENGTH_LONG).show()
            } else if(email.isEmpty() || email == null){
                Toast.makeText(this, "Digite el correo del usuario por favor", Toast.LENGTH_LONG).show()
            } else if(!email.matches(emailAdminPattern.toRegex())){ //Formato de correo valido
                Toast.makeText(this,"El email digitado no es válido",Toast.LENGTH_SHORT).show()
            } else if(user.isEmpty() || user == null){
                Toast.makeText(this, "Digite el usuario por favor", Toast.LENGTH_LONG).show()
            } else if(password.isEmpty() || password == null){
                Toast.makeText(this, "Digite la contraseña del usuario por favor", Toast.LENGTH_LONG).show()
            } else if(tipo.isEmpty() || tipo == null){
                tipo = "ADMIN"
            } else {
                managerUsuarios!!.updateUser(
                    id!!.toInt(),
                    nombre,
                    apellido,
                    email,
                    user,
                    password,
                    tipo
                )
                Toast.makeText(this, "Usuario actualizado", Toast.LENGTH_LONG).show()
                intent = Intent(this, UsuariosActivity::class.java)
                startActivity(intent)
            }
        }

        //CANCELAR
        btnCancelUser.setOnClickListener {
            finish()
        }
    }

    fun rdAdministradorAdmin_click(view: View?){
        tipoSel = "ADMIN"
    }
    fun rdClienteAdmin_click(view: View?){
        tipoSel = "CLIENTE"
    }

}