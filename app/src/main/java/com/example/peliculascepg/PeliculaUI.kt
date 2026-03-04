package com.example.peliculascepg

// Con id (key) para saber cuál editar/eliminar
data class PeliculaUI(
    var id: String = "",
    var nombre: String = "",
    var genero: String = "",
    var anio: String = ""
)