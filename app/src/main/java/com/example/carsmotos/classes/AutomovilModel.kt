package com.example.carsmotos.classes

import java.time.Year
data class AutomovilModel(
   var id: Int,
   var modelo: String,
   var numero_vin: String,
   var numero_chasis: String,
   var numero_motor: String,
   var numero_asientos: Int,
   var anio: Int,
   var capacidad_asientos: Int,
   var precio: Double, //Decimal de 2 decimales
   var URI_IMG: String,
   var descripcion: String,
   var idmarcas : Int,
   var idtipoautomovil: Int,
   var idcolores: Int
){}