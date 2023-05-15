package com.example.carsmotos.classes

data class UsuarioModel(
    var id: Int,
    var nombres: String,
    var apellidos: String,
    var email: String,
    var user: String,
    var password: String,
    var tipo: String
) {}