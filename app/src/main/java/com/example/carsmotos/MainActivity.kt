package com.example.carsmotos

import android.content.Intent
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.core.view.isVisible
import com.example.carsmotos.db.HelperDB
import com.example.carsmotos.login.SignInActivity
import com.example.carsmotos.model.Colores
import com.example.carsmotos.model.Marcas
import com.example.carsmotos.model.TiposAutomoviles
import com.example.carsmotos.model.Usuarios


class MainActivity : ComponentActivity() {

    //Database variables
    private var dbHelper : HelperDB? = null
    private var db: SQLiteDatabase? = null
    private var managerMarcas: Marcas? = null
    private var managerColores: Colores? = null
    private var managerTiposAutomoviles: TiposAutomoviles? = null
    private var managerUsuarios: Usuarios? = null
    private var cursor: Cursor? = null
    private var cursorMarcas: Cursor? = null
    private var cursorColores: Cursor? = null
    private var cursorTiposAutomoviles: Cursor? = null

    //Main Activity variables
    private lateinit var lbEmailLoggedIn: TextView
    private lateinit var lbTipoLoggedIn: TextView
    private lateinit var imgFavAutomoviles: ImageView
    private lateinit var imgAutomoviles: ImageView
    private lateinit var imgLogOut: ImageView
    private lateinit var lyAdminsOnly: LinearLayout
    private lateinit var imgMarcasCRUD: ImageView
    private lateinit var imgColoresCRUD: ImageView
    private lateinit var imgTipoautomovilCRUD: ImageView
    private lateinit var imgAutomovilCRUD: ImageView
    private lateinit var imgUsuariosCRUD: ImageView
    //Variables que vienen desde el login
    private var userIDLog: String? = null
    private var emailLog: String? = null
    private var tipoLog: String? = null





    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)

        //Declarando la base de datos
        dbHelper = HelperDB(this)
        db = dbHelper!!.writableDatabase

        //Declarando componentes de la actividad Main
        lbEmailLoggedIn = findViewById(R.id.lbEmailLoggedIn)
        lbTipoLoggedIn = findViewById(R.id.lbTipoLoggedIn)
        imgFavAutomoviles = findViewById(R.id.imgFavAutomoviles)
        imgAutomoviles = findViewById(R.id.imgAutomoviles)
        imgLogOut = findViewById(R.id.imgLogOut)
        lyAdminsOnly = findViewById(R.id.lyAdminsOnly)
        imgAutomovilCRUD = findViewById(R.id.imgAutomovilCRUD)
        imgUsuariosCRUD = findViewById(R.id.imgUsuariosCRUD)
        imgMarcasCRUD = findViewById(R.id.imgMarcasCRUD)
        imgColoresCRUD = findViewById(R.id.imgColoresCRUD)
        imgTipoautomovilCRUD = findViewById(R.id.imgTipoautomovilCRUD)

        //Recogiendo los datos traidos de la actividad anterior
        val datos: Bundle? = intent.getExtras()
        if (datos != null) {
            //Recibimos los datos de la actividad anterior
            userIDLog = intent.getStringExtra("userIDLog").toString()
            emailLog = intent.getStringExtra("emailLog").toString()
            tipoLog = intent.getStringExtra("tipoLog").toString()
            Log.d("MAIN",userIDLog.toString())
            Log.d("MAIN",emailLog.toString())
            Log.d("MAIN",tipoLog.toString())
            //Los asignamos a sus respectivos labels en pantalla
            lbEmailLoggedIn.text = emailLog
            lbTipoLoggedIn.text = tipoLog

        } else { //Hubo un error encontrando los datos del usuario
            Toast.makeText(this, "Hubo un error cargando los datos del usuario en el MAIN", Toast.LENGTH_SHORT).show()
            finish()
        }


        //Filtrar las opciones del menu segun el tipo de usuario
        if(tipoLog == "CLIENTE"){
            lyAdminsOnly.isVisible = false
            lyAdminsOnly.isEnabled = false
            lyAdminsOnly.isClickable = false
        }

        //Revisando si existe informacion por defecto previamente creada
        if (db != null) {

            //Validamos MARCAS
            managerMarcas = Marcas(this)
            cursorMarcas = managerMarcas!!.showAllMarcas()

            //Si no encuentra nada
            if (cursorMarcas == null || cursorMarcas!!.count == 0) {
                //Creando los valores por defecto
                managerMarcas!!.insertValuesDefault()
                cursorMarcas!!.moveToFirst()
            }



            //Validamos COLORES
            managerColores = Colores(this)
            cursorColores = managerColores!!.showAllColores()

            //Si no encuentra nada
            if (cursorColores == null || cursorColores!!.count == 0) {
                //Creando los valores por defecto
                managerColores!!.insertValuesDefault()
                cursorColores!!.moveToFirst()
            }


            //Validamos TIPOS DE AUTOMOVILES
            managerTiposAutomoviles = TiposAutomoviles(this)
            cursorTiposAutomoviles = managerTiposAutomoviles!!.showAllTiposAutomoviles()

            //Si no encuentra nada
            if (cursorTiposAutomoviles == null || cursorTiposAutomoviles!!.count == 0) {
                //Creando los valores por defecto
                managerTiposAutomoviles!!.insertValuesDefault()
                cursorTiposAutomoviles!!.moveToFirst()
            }

            //VALIDANDO SI YA EXISTEN DATOS EN LAS MARCAS
            cursor = managerMarcas!!.showAllMarcas()

            if (cursor != null && cursor!!.count > 0) { //Si encuentra registros que haga lo siguiente

                //Imprimiendo todas las MARCAS de la bdd en el LOG
                if (cursor!!.moveToFirst()) {
                    do {
                        val id = cursor!!.getString(0)
                        val nombre = cursor!!.getString(1)

                        // Do something with the data
                        // For example, print it to the console
                        Log.d("MARCA-ID", id)
                        Log.d("MARCA-NAME",nombre)
                    } while (cursor!!.moveToNext())
                }
                cursor!!.close()


            } else {
                Toast.makeText(this, "No se encontraron Marcas", Toast.LENGTH_LONG).show()
            }

        }

        //HACER EL USUARIO FIJO EN TODA LA APLICACION

        //DECLARANDO EL CLICK A LOS BOTONS
        //AUTOMOVILES FAVORITOS


        //LISTADO DE AUTOMOVILES



        //CERRAR SESION
        imgLogOut.setOnClickListener {
            Toast.makeText(this,"Cerrando sesión",Toast.LENGTH_SHORT).show()
            finish()
        }


        //ADMIN. MARCAS
        imgMarcasCRUD.setOnClickListener {
            intent = Intent(this, MarcasActivity::class.java)
            startActivity(intent)
        }

        //ADMIN. COLORES
        imgColoresCRUD.setOnClickListener {
            intent = Intent(this, ColoresActivity::class.java)
            startActivity(intent)
        }

        //ADMIN. TIPO AUTO
        imgTipoautomovilCRUD.setOnClickListener {
            intent = Intent(this, TipoAutoActivity:: class.java)
            startActivity(intent)
        }


    }










}
