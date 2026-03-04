package com.example.peliculascepg

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.firebase.Firebase
import com.google.firebase.database.database

class AddEditMovieActivity : AppCompatActivity() {

    private val database = Firebase.database
    private val myRef = database.getReference("peliculas")

    private lateinit var tvTitulo: TextView
    private lateinit var etNombre: EditText
    private lateinit var etGenero: EditText
    private lateinit var etAnio: EditText
    private lateinit var btnGuardar: Button
    private lateinit var btnEliminar: Button
    private lateinit var btnCancelar: Button

    private var modo: String = "agregar"
    private var peliId: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_add_edit_movie)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        tvTitulo = findViewById(R.id.tvTitulo)
        etNombre = findViewById(R.id.etNombre)
        etGenero = findViewById(R.id.etGenero)
        etAnio = findViewById(R.id.etAnio)
        btnGuardar = findViewById(R.id.btnGuardar)
        btnEliminar = findViewById(R.id.btnEliminar)
        btnCancelar = findViewById(R.id.btnCancelar)

        modo = intent.getStringExtra("modo") ?: "agregar"

        if (modo == "editar") {
            tvTitulo.text = "Editar / Eliminar"
            peliId = intent.getStringExtra("id") ?: ""
            etNombre.setText(intent.getStringExtra("nombre") ?: "")
            etGenero.setText(intent.getStringExtra("genero") ?: "")
            etAnio.setText(intent.getStringExtra("anio") ?: "")
            btnEliminar.visibility = View.VISIBLE
        } else {
            tvTitulo.text = "Agregar película"
            btnEliminar.visibility = View.GONE
        }

        btnGuardar.setOnClickListener {
            val nombre = etNombre.text.toString().trim()
            val genero = etGenero.text.toString().trim()
            val anio = etAnio.text.toString().trim()

            if (nombre.isEmpty() || genero.isEmpty() || anio.isEmpty()) {
                Toast.makeText(this, "Completa todos los campos", Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }

            val peli = Peliculas(nombre, genero, anio) // clase SIN id (manual) :contentReference[oaicite:5]{index=5}

            if (modo == "agregar") {
                // AGREGAR: push() + setValue() (manual) :contentReference[oaicite:6]{index=6}
                myRef.push().setValue(peli)
                    .addOnSuccessListener {
                        Toast.makeText(this, "Película agregada", Toast.LENGTH_LONG).show()
                        finish()
                    }
                    .addOnFailureListener {
                        Toast.makeText(this, "Error: ${it.message}", Toast.LENGTH_LONG).show()
                    }

            } else {
                // EDITAR TODO EL NODO: child(id).setValue(objeto) (manual) :contentReference[oaicite:7]{index=7}
                if (peliId.isEmpty()) {
                    Toast.makeText(this, "ID inválido", Toast.LENGTH_LONG).show()
                    return@setOnClickListener
                }

                myRef.child(peliId).setValue(peli)
                    .addOnSuccessListener {
                        Toast.makeText(this, "Película actualizada", Toast.LENGTH_LONG).show()
                        finish()
                    }
                    .addOnFailureListener {
                        Toast.makeText(this, "Error: ${it.message}", Toast.LENGTH_LONG).show()
                    }
            }
        }

        btnEliminar.setOnClickListener {
            if (peliId.isEmpty()) {
                Toast.makeText(this, "ID inválido", Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }

            AlertDialog.Builder(this)
                .setTitle("Eliminar")
                .setMessage("¿Seguro que quieres eliminar esta película?")
                .setPositiveButton("Sí") { _, _ ->
                    // ELIMINAR: removeValue() (manual) :contentReference[oaicite:8]{index=8}
                    myRef.child(peliId).removeValue()
                        .addOnSuccessListener {
                            Toast.makeText(this, "Película eliminada", Toast.LENGTH_LONG).show()
                            finish()
                        }
                        .addOnFailureListener {
                            Toast.makeText(this, "Error: ${it.message}", Toast.LENGTH_LONG).show()
                        }
                }
                .setNegativeButton("No", null)
                .show()
        }

        btnCancelar.setOnClickListener { finish() }
    }
}