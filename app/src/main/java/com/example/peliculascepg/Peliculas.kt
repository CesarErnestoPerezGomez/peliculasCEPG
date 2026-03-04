package com.example.peliculascepg

// Sin id (para push().setValue() y setValue() del nodo)
data class Peliculas(
    var nombre: String = "",
    var genero: String = "",
    var anio: String = ""
)