package com.example.carsmotos

import android.content.Intent
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import com.example.carsmotos.db.HelperDB
import com.example.carsmotos.model.Automoviles
import com.example.carsmotos.model.Colores
import com.example.carsmotos.model.Marcas
import com.example.carsmotos.model.TiposAutomoviles
import java.text.DecimalFormat

class AutomovilCARD : AppCompatActivity(){

    //Database variables
    private var dbHelper: HelperDB? = null
    private var db: SQLiteDatabase? = null
    private var managerAutomoviles: Automoviles? = null
    private var managerMarcas: Marcas? = null
    private var cursorMarcas: Cursor? = null
    private var managerTipoAuto: TiposAutomoviles? = null
    private var cursorTipoAuto: Cursor? = null
    private var managerColor: Colores? = null
    private var cursorColor: Cursor? = null
    private var cursorAutomovilesFound: Cursor? = null

    //AutomovilesCRUD Activity Variables
    private lateinit var txtModelo: TextView
    private lateinit var txtNumeroVin: TextView
    private lateinit var txtNumeroChasis: TextView
    private lateinit var txtNumeroMotor: TextView
    private lateinit var txtNumeroAsientos: TextView
    private lateinit var txtAnio: TextView
    private lateinit var txtCapacidadAsientos: TextView
    private lateinit var txtPrecio: TextView
    private lateinit var txtDescripcion: TextView
    private lateinit var cmbMarcas: Spinner
    private lateinit var cmbColor: Spinner
    private lateinit var cmbTipoAuto: Spinner
    private lateinit var btnAddAutomovil: Button
    private lateinit var btnUpdateAutomovil: Button
    private lateinit var btnCancelAutomovil: Button

    //Enviados desde otra actividad
    private var opc: String? = null
    private var id: String? = null //El resto de valores los voy a encontrar con el SEARCHID

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.automovil_card)
        /*
        //Declarando la base de datos
        dbHelper = HelperDB(this)
        db = dbHelper!!.writableDatabase
        managerAutomoviles = Automoviles(this) //ESTE ES DE VITAL IMPORTANCIA NO OLVIDAR SEGUN LA LECTURA A LA TABLA QUE SE HARA

        //Declarando componentes de la actividad Main
        txtModelo = findViewById(R.id.txtModelo)
        txtNumeroVin = findViewById(R.id.txtNumeroVin)
        txtNumeroChasis = findViewById(R.id.txtNumeroChasis)
        txtNumeroMotor = findViewById(R.id.txtNumeroMotor)
        txtNumeroAsientos = findViewById(R.id.txtNumeroAsientos)
        txtAnio = findViewById(R.id.txtAnio)
        txtCapacidadAsientos = findViewById(R.id.txtCapacidadAsientos)
        txtPrecio = findViewById(R.id.txtPrecio)
        txtDescripcion = findViewById(R.id.txtDescripcion)
        cmbMarcas = findViewById(R.id.cmbMarcas)
        cmbColor = findViewById(R.id.cmbColor)
        cmbTipoAuto = findViewById(R.id.cmbTipoAuto)
        btnAddAutomovil = findViewById(R.id.btnAddAutomovil)
        btnUpdateAutomovil = findViewById(R.id.btnUpdateAutomovil)
        btnCancelAutomovil = findViewById(R.id.btnCancelAutomovil)

        //Declarando los valores de los combobox

        //Recogiendo los datos traidos de la actividad anterior
        val datos: Bundle? = intent.getExtras()
        if (datos != null) {
            //Recibimos los datos de la actividad anterior
            opc = intent.getStringExtra("opc").toString()

            //Activamos y desactivamos botones segun el valor que haya sido enviado como "opc"
            if (opc == "agregar") {
                //No se puede actualizar un valor que no existe aun
                btnUpdateAutomovil.isEnabled = false
                btnUpdateAutomovil.isVisible = false
                btnUpdateAutomovil.isClickable = false
            } else if (opc == "editar") {
                //No se puede agregar un nuevo valor del que se esta actualizando
                btnAddAutomovil.isEnabled = false
                btnAddAutomovil.isVisible = false
                btnAddAutomovil.isClickable = false
                //Donde guardaremos los valores enviados desde la bdd
                var modelo: String = ""
                var numero_vin: String = ""
                var numero_chasis: String = ""
                var numero_motor: String = ""
                var numero_asientos: String = ""
                var anio: Int = 0
                var capacidad_asientos: Int = 0
                var precio: Double = 0.0//Decimal de 2 decimales
                var URI_IMG: String = ""
                var descripcion: String = ""
                var idmarcas : Int = 0
                var idtipoautomovil: Int = 0
                var idcolores: Int = 0

                //Importamos lo que se envia desde la otra actividad
                id = intent.getStringExtra("id").toString()
                var idFound: String? = "1"
                //Buscamos toda la informacion que este relacionada a este usuario en la BDD
                if (db != null) {
                    //Buscamos todos los valores que esten relacionados al ID del usuario compartido
                    cursorAutomovilesFound = managerAutomoviles!!.searchID(id!!.toInt())

                    if (cursorAutomovilesFound != null && cursorAutomovilesFound!!.count > 0) {
                        cursorAutomovilesFound!!.moveToFirst()
                        idFound = cursorAutomovilesFound!!.getString(0).toString()
                        modelo = cursorAutomovilesFound!!.getString(1).toString()
                        numero_vin = cursorAutomovilesFound!!.getString(2).toString()
                        numero_chasis = cursorAutomovilesFound!!.getString(3).toString()
                        numero_motor = cursorAutomovilesFound!!.getString(4).toString()
                        numero_asientos = cursorAutomovilesFound!!.getString(5).toString()
                        anio = cursorAutomovilesFound!!.getInt(6)
                        capacidad_asientos = cursorAutomovilesFound!!.getInt(7)
                        precio = cursorAutomovilesFound!!.getDouble(8)
                        URI_IMG = cursorAutomovilesFound!!.getString(9).toString()
                        descripcion = cursorAutomovilesFound!!.getString(10).toString()
                        idmarcas = cursorAutomovilesFound!!.getInt(11)
                        idtipoautomovil = cursorAutomovilesFound!!.getInt(12)
                        idcolores = cursorAutomovilesFound!!.getInt(13)


                    } else {
                        Toast.makeText(
                            this,
                            "No se logro encontrar un automovil con esa ID en la bdd",
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
                txtModelo.text = modelo
                txtNumeroVin.text = numero_vin
                txtNumeroChasis.text = numero_chasis
                txtNumeroMotor.text = numero_motor
                txtNumeroAsientos.text = numero_asientos
                txtAnio.text = anio.toString()
                txtCapacidadAsientos.text = capacidad_asientos.toString()
                txtPrecio.text = precio.toString()
                txtDescripcion.text = descripcion.toString()
                //Por cuestiones de tiempo lo voy a dejar en el primer valor encontrado, no porque aqui se podria buscar que ID tiene cada uno y asignarla
                cmbMarcas.setSelection(0)
                cmbTipoAuto.setSelection(0)
                cmbColor.setSelection(0)
            }

        } else { //Hubo un error encontrando los datos del usuario
            Toast.makeText(
                this,
                "Hubo un error cargando la OPC enviada en el UsersActivity",
                Toast.LENGTH_SHORT
            ).show()
            finish()
        }


        //CANCELAR
        btnCancelAutomovil.setOnClickListener {
            finish()
        }

         */
    }


}