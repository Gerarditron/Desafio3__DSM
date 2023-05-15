package com.example.carsmotos

import android.content.Intent
import android.database.sqlite.SQLiteDatabase
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import com.example.carsmotos.db.HelperDB
import com.example.carsmotos.model.Colores

class ColoresCRUD : AppCompatActivity() {
    //Database variables
    private var dbHelper : HelperDB? = null
    private var db: SQLiteDatabase? = null
    private var managerColores: Colores? = null

    //ColoresCRUD Activity Variables
    private lateinit var txtColorAdmin: TextView
    private lateinit var btnAddColor: Button
    private lateinit var btnUpdateColor: Button
    private lateinit var btnCancelColor: Button
    private var opc: String? = null
    private var id: String? = null
    private var descripcion: String? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.admin_colores_activity)

        //Declarando la base de datos
        dbHelper = HelperDB(this)
        db = dbHelper!!.writableDatabase
        managerColores = Colores(this) //ESTE ES DE VITAL IMPORTANCIA NO OLVIDAR SEGUN LA LECTURA A LA TABLA QUE SE HARA
        //Declarando componentes de la actividad Main
        txtColorAdmin = findViewById(R.id.txtColorAdmin)
        btnAddColor = findViewById(R.id.btnAddColor)
        btnUpdateColor = findViewById(R.id.btnUpdateColor)
        btnCancelColor = findViewById(R.id.btnCancelColor)

        //Recogiendo los datos traidos de la actividad anterior
        val datos: Bundle? = intent.getExtras()
        if (datos != null) {
            //Recibimos los datos de la actividad anterior
            opc = intent.getStringExtra("opc").toString()

            //Activamos y desactivamos botones segun el valor que haya sido enviado como "opc"
            if(opc == "agregar"){
                //No se puede actualizar un valor que no existe aun
                btnUpdateColor.isEnabled = false
                btnUpdateColor.isVisible = false
                btnUpdateColor.isClickable = false
            } else if (opc == "editar"){
                //No se puede agregar un nuevo valor del que se esta actualizando
                btnAddColor.isEnabled = false
                btnAddColor.isVisible = false
                btnAddColor.isClickable = false
                //Importamos lo que se envia desde la otra actividad
                id = intent.getStringExtra("id").toString()
                descripcion = intent.getStringExtra("descripcion").toString()
                //Actualizamos el valor mostrado en el textbox
                txtColorAdmin.text = descripcion

            }

        } else { //Hubo un error encontrando los datos del usuario
            Toast.makeText(this, "Hubo un error cargando la OPC enviada en el ColoresActivity", Toast.LENGTH_SHORT).show()
            finish()
        }


        //AGREGAR COLOR
        btnAddColor.setOnClickListener{
            //Guardamos en una variables lo que este en txtColor
            val descripcion : String = txtColorAdmin.text.toString()

            //Validamos que el campo no este vacio
            if(descripcion.isEmpty() || descripcion == null){
                Toast.makeText(this, "Digite el color del automovil por favor", Toast.LENGTH_LONG).show()
            } else {
                managerColores!!.addNewColor(
                    descripcion
                )
                Toast.makeText(this, "Color agregado", Toast.LENGTH_LONG).show()
                intent = Intent(this, ColoresActivity::class.java)
                startActivity(intent)
            }
        }

        //EDITAR COLOR
        btnUpdateColor.setOnClickListener{
            //Guardamos en una variables lo que este en txtColor
            val descripcion : String = txtColorAdmin.text.toString()
            val idStr : String = id.toString()
            val idInt : Int = idStr.toInt()
            //Validamos que el campo no este vacio
            if(descripcion.isEmpty() || descripcion == null){
                Toast.makeText(this, "Digite el nombre del Color por favor", Toast.LENGTH_LONG).show()
            } else {
                managerColores!!.updateColor(
                    id!!.toInt(),
                    descripcion
                )

                Toast.makeText(this, "Color actualizado", Toast.LENGTH_LONG).show()
                finish()
            }
        }

        //CANCELAR
        btnCancelColor.setOnClickListener{
            finish()
        }

    }

}