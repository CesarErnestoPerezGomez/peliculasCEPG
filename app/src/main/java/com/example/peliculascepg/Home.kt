package com.example.peliculascepg

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.google.firebase.database.*

class Home : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth

    private val database = Firebase.database
    private val myRef = database.getReference("peliculas")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_home)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)

        auth = Firebase.auth

        val usuarioActual = auth.currentUser
        if (usuarioActual == null) {
            irALogin()
        } else {
            Toast.makeText(this, "Bienvenido ${usuarioActual.email}", Toast.LENGTH_LONG).show()
        }

        myRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val value = snapshot.value
                Log.d("base-tiempo-real", "Value is: $value")
            }

            override fun onCancelled(error: DatabaseError) {
                Log.w("base-tiempo-real", "Failed to read value.", error.toException())
            }
        })
    }

    private fun irALogin() {
        val intent = Intent(this, MainActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
        finish()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        return when (item.itemId) {

            R.id.logout -> {
                auth.signOut()
                Toast.makeText(this, "Sesión cerrada", Toast.LENGTH_LONG).show()
                irALogin()
                true
            }

            R.id.perfil -> {
                Toast.makeText(this, "Perfil", Toast.LENGTH_SHORT).show()
                true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }
}
