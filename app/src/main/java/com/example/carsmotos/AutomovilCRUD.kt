package com.example.carsmotos

import android.content.Intent
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.RadioButton
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
import org.w3c.dom.Text
import java.text.DecimalFormat
import java.time.Year

class AutomovilCRUD : AppCompatActivity() {

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
        setContentView(R.layout.admin_automovil_activity)

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
        setSpinnerMarcas()
        setSpinnerTipoAuto()
        setSpinnerColores()

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


        //AGREGAR
        btnAddAutomovil.setOnClickListener {
            //Guardamos en una variables lo que este en txt
            var modelo = txtModelo.text.toString()
            var numero_vin = txtNumeroVin.text.toString()
            var numero_chasis = txtNumeroChasis.text.toString()
            var numero_motor = txtNumeroMotor.text.toString()
            var numero_asientosStr = txtNumeroAsientos.text?.toString()
            var numero_asientos: Int = 0
            if (numero_asientosStr != "" || numero_asientosStr != null) {
                numero_asientos = numero_asientosStr!!.toInt()
            } else {
                numero_asientos = 0
            }
            var anioStr = txtAnio.text?.toString()
            var anio : Int = 0
            if (anioStr != "" || anioStr != null) {
                anio = anio!!.toInt()
            } else {
                anio = 0
            }
            var capacidad_asientosStr = txtCapacidadAsientos.text?.toString()
            var capacidad_asientos: Int = 0
            if (capacidad_asientosStr != "" || capacidad_asientosStr != null) {
                capacidad_asientos == capacidad_asientosStr!!.toInt()
            } else {
                capacidad_asientos = 0
            }
            var precioStr = txtPrecio.text?.toString()
            var precioDbl = precioStr?.toDouble()
            //Redondeando a 2 decimales
                val decimalFormat = DecimalFormat("#.##")
                val precio: Double = decimalFormat.format(precioDbl).toDouble()
            var URI_IMG = ""
            var descripcion = txtDescripcion.text.toString()
            //Buscando los valores de los cmb
            //cmbMarca
            val marca: String = cmbMarcas!!.selectedItem.toString().trim() //Primero guardamos la marca seleccionada
            val idmarcas = managerMarcas!!.searchID(marca) //Ahora buscamos el ID por medio del nombre de la marca REGRESA INT
            //cmbTipoAutomovil
            val tipoAuto: String = cmbTipoAuto!!.selectedItem.toString().trim() //Primero guardamos la marca seleccionada
            val idtipoautomovil = managerTipoAuto!!.searchID(tipoAuto) //Ahora buscamos el ID por medio del nombre del tipo de automovil REGRESA INT
            //cmbColor
            val color: String = cmbColor!!.selectedItem.toString().trim() //Primero guardamos la marca seleccionada
            val idcolores = managerColor!!.searchID(color) //Ahora buscamos el ID por medio del nombre de la marca REGRESA INT

            //Campos que si estan vacios se llenen con valor por defecto
            if(numero_vin.isEmpty() || numero_vin == null) {
                numero_vin = ""
            }
            if(numero_chasis.isEmpty() || numero_chasis == null) {
                numero_chasis = ""
            }
            if(numero_motor.isEmpty() || numero_motor == null) {
                numero_motor = ""
            }
            if(numero_asientos == null) {
                numero_asientos = 0
            }
            if(anio == null) {
                anio = 0
            }
            if(capacidad_asientos == null) {
                capacidad_asientos = 0
            }
            //Validamos que el campo no este vacio SOLO PARA LAS COLUMNAS OBLIGATORIAS
            if(modelo.isEmpty() || modelo == null){
                Toast.makeText(this, "Digite el modelo del automovil por favor", Toast.LENGTH_LONG).show()
            } else if(precio == null || precio == 0.0){
                Toast.makeText(this, "Digite el precio del automovil por favor", Toast.LENGTH_LONG).show()
            } else if(descripcion.isEmpty() || descripcion == null){
                Toast.makeText(this, "Digite una descripcion del automovil por favor", Toast.LENGTH_LONG).show()
            } else if(idmarcas == null){
                Toast.makeText(this, "OCURRIO UN ERROR ENCONTRANDO EL VALOR DE LA MARCA", Toast.LENGTH_LONG).show()
                finish()
            } else if(idtipoautomovil == null){
                Toast.makeText(this, "OCURRIO UN ERROR ENCONTRANDO EL VALOR DEL TIPO DE AUTOMOVIL", Toast.LENGTH_LONG).show()
                finish()
            } else if(idcolores == null){
                Toast.makeText(this, "OCURRIO UN ERROR ENCONTRANDO EL VALOR DEL COLOR", Toast.LENGTH_LONG).show()
                finish()
            } else {
                managerAutomoviles!!.addNewAuto(
                    modelo,
                    numero_vin,
                    numero_chasis,
                    numero_motor,
                    numero_asientos,
                    anio,
                    capacidad_asientos,
                    precio,
                    URI_IMG,
                    descripcion,
                    idmarcas,
                    idtipoautomovil,
                    idcolores
                )
                Toast.makeText(this, "Automovil agregado", Toast.LENGTH_LONG).show()
                intent = Intent(this, AutomovilActivity::class.java)
                startActivity(intent)
            }
        }

        //EDITAR
        btnUpdateAutomovil.setOnClickListener {
            //Guardamos en una variables lo que este en txt
            var modelo = txtModelo.text.toString()
            var numero_vin = txtNumeroVin.text.toString()
            var numero_chasis = txtNumeroChasis.text.toString()
            var numero_motor = txtNumeroMotor.text.toString()
            var numero_asientosStr = txtNumeroAsientos.text.toString()
            var numero_asientos: Int = numero_asientosStr.toInt()
            var anioStr = txtAnio.text.toString()
            var anio : Int = anioStr.toInt()
            var capacidad_asientosStr = txtCapacidadAsientos.text.toString()
            var capacidad_asientos: Int = capacidad_asientosStr.toInt()
            var precioStr = txtPrecio.text.toString()
            var precioDbl : Double = precioStr.toDouble()
            //Redondeando a 2 decimales
            val decimalFormat = DecimalFormat("#.##")
            val precio: Double = decimalFormat.format(precioDbl).toDouble()
            var URI_IMG = ""
            var descripcion = txtDescripcion.text.toString()
            //Buscando los valores de los cmb
            //cmbMarca
            val marca: String = cmbMarcas!!.selectedItem.toString().trim() //Primero guardamos la marca seleccionada
            val idmarcas = managerMarcas!!.searchID(marca) //Ahora buscamos el ID por medio del nombre de la marca REGRESA INT
            //cmbTipoAutomovil
            val tipoAuto: String = cmbTipoAuto!!.selectedItem.toString().trim() //Primero guardamos la marca seleccionada
            val idtipoautomovil = managerTipoAuto!!.searchID(tipoAuto) //Ahora buscamos el ID por medio del nombre del tipo de automovil REGRESA INT
            //cmbColor
            val color: String = cmbColor!!.selectedItem.toString().trim() //Primero guardamos la marca seleccionada
            val idcolores = managerColor!!.searchID(color) //Ahora buscamos el ID por medio del nombre de la marca REGRESA INT

            //Campos que si estan vacios se llenen con valor por defecto
            if(numero_vin.isEmpty() || numero_vin == null) {
                numero_vin = ""
            }
            if(numero_chasis.isEmpty() || numero_chasis == null) {
                numero_chasis = ""
            }
            if(numero_motor.isEmpty() || numero_motor == null) {
                numero_motor = ""
            }
            if(numero_asientos == null) {
                numero_asientos = 0
            }
            if(anio == null) {
                anio = 0
            }
            if(capacidad_asientos == null) {
                capacidad_asientos = 0
            }
            //Validamos que el campo no este vacio SOLO PARA LAS COLUMNAS OBLIGATORIAS
            if(modelo.isEmpty() || modelo == null){
                Toast.makeText(this, "Digite el modelo del automovil por favor", Toast.LENGTH_LONG).show()
            } else if(precio == null || precio == 0.0){
                Toast.makeText(this, "Digite el precio del automovil por favor", Toast.LENGTH_LONG).show()
            } else if(descripcion.isEmpty() || descripcion == null){
                Toast.makeText(this, "Digite una descripcion del automovil por favor", Toast.LENGTH_LONG).show()
            } else if(idmarcas == null){
                Toast.makeText(this, "OCURRIO UN ERROR ENCONTRANDO EL VALOR DE LA MARCA", Toast.LENGTH_LONG).show()
                finish()
            } else if(idtipoautomovil == null){
                Toast.makeText(this, "OCURRIO UN ERROR ENCONTRANDO EL VALOR DEL TIPO DE AUTOMOVIL", Toast.LENGTH_LONG).show()
                finish()
            } else if(idcolores == null){
                Toast.makeText(this, "OCURRIO UN ERROR ENCONTRANDO EL VALOR DEL COLOR", Toast.LENGTH_LONG).show()
                finish()
            } else {
                managerAutomoviles!!.updateAuto(
                    id!!.toInt(),
                    modelo,
                    numero_vin,
                    numero_chasis,
                    numero_motor,
                    numero_asientos,
                    anio,
                    capacidad_asientos,
                    precio,
                    URI_IMG,
                    descripcion,
                    idmarcas,
                    idtipoautomovil,
                    idcolores
                )
                Toast.makeText(this, "Automovil actualizado", Toast.LENGTH_LONG).show()
                intent = Intent(this, AutomovilActivity::class.java)
                startActivity(intent)
            }
        }

        //CANCELAR
        btnCancelAutomovil.setOnClickListener {
            finish()
        }
    }

    private fun setSpinnerMarcas() {
        managerMarcas = Marcas(this)
        cursorMarcas = managerMarcas!!.showAllMarcas()

        var marc = ArrayList<String>()
        if (cursorMarcas != null && cursorMarcas!!.count > 0) {
            cursorMarcas!!.moveToFirst()
            marc.add(cursorMarcas!!.getString(1))
            do {
                marc.add(cursorMarcas!!.getString(1))
            } while (cursorMarcas!!.moveToNext())
        }

        var adaptador = ArrayAdapter(this, android.R.layout.simple_spinner_item, marc)
        adaptador.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        cmbMarcas!!.adapter = adaptador
    }

    private fun setSpinnerTipoAuto() {
        managerTipoAuto = TiposAutomoviles(this)
        cursorTipoAuto = managerTipoAuto!!.showAllTiposAutomoviles()

        var tipaut = ArrayList<String>()
        if (cursorTipoAuto != null && cursorTipoAuto!!.count > 0) {
            cursorTipoAuto!!.moveToFirst()
            tipaut.add(cursorTipoAuto!!.getString(1))
            do {
                tipaut.add(cursorTipoAuto!!.getString(1))
            } while (cursorTipoAuto!!.moveToNext())
        }

        var adaptador = ArrayAdapter(this, android.R.layout.simple_spinner_item, tipaut)
        adaptador.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        cmbTipoAuto!!.adapter = adaptador

    }

    private fun setSpinnerColores() {
        managerColor = Colores(this)
        cursorColor = managerColor!!.showAllColores()

        var clr = ArrayList<String>()
        if (cursorColor != null && cursorColor!!.count > 0) {
            cursorColor!!.moveToFirst()
            clr.add(cursorColor!!.getString(1))
            do {
                clr.add(cursorColor!!.getString(1))
            } while (cursorColor!!.moveToNext())
        }

        var adaptador = ArrayAdapter(this, android.R.layout.simple_spinner_item, clr)
        adaptador.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        cmbColor!!.adapter = adaptador
    }
}