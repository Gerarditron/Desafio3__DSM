package com.example.carsmotos

import android.content.Intent
import android.database.sqlite.SQLiteDatabase
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import com.example.carsmotos.db.HelperDB
import com.example.carsmotos.model.TiposAutomoviles

class TipoAutoCRUD : AppCompatActivity() {

    //Database variables
    private var dbHelper : HelperDB? = null
    private var db: SQLiteDatabase? = null
    private var managerTipoAutomoviles: TiposAutomoviles? = null

    //TipoAutoCRUD Activity Variables
    private lateinit var txtTipoAutoAdmin: TextView
    private lateinit var btnAddTipoAuto: Button
    private lateinit var btnUpdateTipoAuto: Button
    private lateinit var btnCancelTipo: Button
    private var opc: String? = null
    private var id: String? = null
    private var descripcion: String? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.admin_tipoauto_activity)

        //Declarando la base de datos
        dbHelper = HelperDB(this)
        db = dbHelper!!.writableDatabase
        managerTipoAutomoviles = TiposAutomoviles(this) //ESTE ES DE VITAL IMPORTANCIA NO OLVIDAR SEGUN LA LECTURA A LA TABLA QUE SE HARA
        //Declarando componentes de la actividad Main
        txtTipoAutoAdmin = findViewById(R.id.txtTipoAutoAdmin)
        btnAddTipoAuto = findViewById(R.id.btnAddTipoAuto)
        btnUpdateTipoAuto = findViewById(R.id.btnUpdateTipoAuto)
        btnCancelTipo = findViewById(R.id.btnCancelTipo)

        //Recogiendo los datos traidos de la actividad anterior
        val datos: Bundle? = intent.getExtras()
        if (datos != null) {
            //Recibimos los datos de la actividad anterior
            opc = intent.getStringExtra("opc").toString()

            //Activamos y desactivamos botones segun el valor que haya sido enviado como "opc"
            if(opc == "agregar"){
                //No se puede actualizar un valor que no existe aun
                btnUpdateTipoAuto.isEnabled = false
                btnUpdateTipoAuto.isVisible = false
                btnUpdateTipoAuto.isClickable = false
            } else if (opc == "editar"){
                //No se puede agregar un nuevo valor del que se esta actualizando
                btnAddTipoAuto.isEnabled = false
                btnAddTipoAuto.isVisible = false
                btnAddTipoAuto.isClickable = false
                //Importamos lo que se envia desde la otra actividad
                id = intent.getStringExtra("id").toString()
                descripcion = intent.getStringExtra("descripcion").toString()
                //Actualizamos el valor mostrado en el textbox
                txtTipoAutoAdmin.text = descripcion

            }

        } else { //Hubo un error encontrando los datos del usuario
            Toast.makeText(this, "Hubo un error cargando la OPC enviada en el TiposAutosActivity", Toast.LENGTH_SHORT).show()
            finish()
        }


        //AGREGAR
        btnAddTipoAuto.setOnClickListener{
            //Guardamos en una variables lo que este en txtTipoAuto
            val descripcion : String = txtTipoAutoAdmin.text.toString()

            //Validamos que el campo no este vacio
            if(descripcion.isEmpty() || descripcion == null){
                Toast.makeText(this, "Digite el Tipo de automovil por favor", Toast.LENGTH_LONG).show()
            } else {
                managerTipoAutomoviles!!.addNewTipoAutomovil(
                    descripcion
                )
                Toast.makeText(this, "Tipo de Automovil agregado", Toast.LENGTH_LONG).show()
                val intent = Intent(this,TipoAutoActivity::class.java)
                startActivity(intent)
            }
        }

        //EDITAR
        btnUpdateTipoAuto.setOnClickListener{
            //Guardamos en una variables lo que este en txtTipoAuto
            val descripcion : String = txtTipoAutoAdmin.text.toString()
            val idStr : String = id.toString()
            val idInt : Int = idStr.toInt()
            //Validamos que el campo no este vacio
            if(descripcion.isEmpty() || descripcion == null){
                Toast.makeText(this, "Digite el Tipo de Automovil por favor", Toast.LENGTH_LONG).show()
            } else {
                managerTipoAutomoviles!!.updateTipoAutomovil(
                    id!!.toInt(),
                    descripcion
                )

                Toast.makeText(this, "Tipo de Automovil actualizado", Toast.LENGTH_LONG).show()
                intent = Intent(this, TipoAutoActivity::class.java)
                startActivity(intent)
            }
        }

        //CANCELAR
        btnCancelTipo.setOnClickListener{
            finish()
        }

    }

}