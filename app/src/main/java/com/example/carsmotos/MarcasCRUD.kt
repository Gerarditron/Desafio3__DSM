package com.example.carsmotos

import android.content.Intent
import android.database.Cursor
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
import com.example.carsmotos.model.Marcas
import com.example.carsmotos.model.TiposAutomoviles
import com.example.carsmotos.model.Usuarios
import org.w3c.dom.Text

class MarcasCRUD : AppCompatActivity() {

    //Database variables
    private var dbHelper : HelperDB? = null
    private var db: SQLiteDatabase? = null
    private var managerMarcas: Marcas? = null

    //MarcasCRUD Activity Variables
    private lateinit var txtMarcaAdmin: TextView
    private lateinit var btnAddMarca: Button
    private lateinit var btnUpdateMarca: Button
    private lateinit var btnCancelMarca: Button
    private var opc: String? = null
    private var id: String? = null
    private var nombre: String? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.admin_marcas_activity)

        //Declarando la base de datos
        dbHelper = HelperDB(this)
        db = dbHelper!!.writableDatabase
        managerMarcas = Marcas(this) //ESTE ES DE VITAL IMPORTANCIA NO OLVIDAR SEGUN LA LECTURA A LA TABLA QUE SE HARA
        //Declarando componentes de la actividad Main
        txtMarcaAdmin = findViewById(R.id.txtMarcaAdmin)
        btnAddMarca = findViewById(R.id.btnAddMarca)
        btnUpdateMarca = findViewById(R.id.btnUpdateMarca)
        btnCancelMarca = findViewById(R.id.btnCancelMarca)

        //Recogiendo los datos traidos de la actividad anterior
        val datos: Bundle? = intent.getExtras()
        if (datos != null) {
            //Recibimos los datos de la actividad anterior
            opc = intent.getStringExtra("opc").toString()
            Log.d("MARCAS-CRUD",opc.toString())

            //Activamos y desactivamos botones segun el valor que haya sido enviado como "opc"
            if(opc == "agregar"){
                //No se puede actualizar un valor que no existe aun
                btnUpdateMarca.isEnabled = false
                btnUpdateMarca.isVisible = false
                btnUpdateMarca.isClickable = false
            } else if (opc == "editar"){
                //No se puede agregar un nuevo valor del que se esta actualizando
                btnAddMarca.isEnabled = false
                btnAddMarca.isVisible = false
                btnAddMarca.isClickable = false
                //Importamos lo que se envia desde la otra actividad
                id = intent.getStringExtra("idmarca").toString()
                nombre = intent.getStringExtra("nombremarca").toString()
                //Actualizamos el valor mostrado en el textbox
                txtMarcaAdmin.text = nombre

                //Toast.makeText(this, "ID: $id // Marca: $nombre", Toast.LENGTH_LONG).show()

            }

        } else { //Hubo un error encontrando los datos del usuario
            Toast.makeText(this, "Hubo un error cargando la OPC enviada en el MarcasActivity", Toast.LENGTH_SHORT).show()
            finish()
        }


        //AGREGAR MARCA
        btnAddMarca.setOnClickListener{
            //Guardamos en una variables lo que este en txtMarca
            val nombreMarca : String = txtMarcaAdmin.text.toString()

            //Validamos que el campo no este vacio
            if(nombreMarca.isEmpty() || nombreMarca == null){
                Toast.makeText(this, "Digite el nombre de la marca por favor", Toast.LENGTH_LONG).show()
            } else {
                managerMarcas!!.addNewMarca(
                    nombreMarca
                )
                Toast.makeText(this, "Marca agregada", Toast.LENGTH_LONG).show()
                finish()
            }
        }

        //EDITAR MARCA
        btnUpdateMarca.setOnClickListener{
            //Guardamos en una variables lo que este en txtMarca
            val nombreMarca : String = txtMarcaAdmin.text.toString()
            val idStr : String = id.toString()
            val idInt : Int = idStr.toInt()
            Log.d("UPDATE-MARCA",nombreMarca)
            Log.d("UP" +
                    "DATE-MARCA",idInt.toString())
            //Validamos que el campo no este vacio
            if(nombreMarca.isEmpty() || nombreMarca == null){
                Toast.makeText(this, "Digite el nombre de la marca por favor", Toast.LENGTH_LONG).show()
            } else {
                managerMarcas!!.updateMarca(
                    id!!.toInt(),
                    nombreMarca
                )

                Toast.makeText(this, "Marca actualizada", Toast.LENGTH_LONG).show()
                intent = Intent(this, MarcasActivity::class.java)
                startActivity(intent)
            }
        }

        //CANCELAR
        btnCancelMarca.setOnClickListener{
            finish()
        }

    }

}