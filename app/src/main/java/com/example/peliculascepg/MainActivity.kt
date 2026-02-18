package com.example.peliculascepg

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.auth

class MainActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        auth = Firebase.auth
    }

    fun login(view: View){

        val email = findViewById<android.widget.EditText>(R.id.editTextTextEmailAddress)
            .text.toString().trim()

        val password = findViewById<android.widget.EditText>(R.id.editTextTextPassword)
            .text.toString().trim()

        if(email.isEmpty() || password.isEmpty()){
            Toast.makeText(this,"Complete all fields",Toast.LENGTH_LONG).show()
            return
        }

        auth.signInWithEmailAndPassword(email,password)
            .addOnCompleteListener { task ->

                if(task.isSuccessful){

                    Toast.makeText(this,"Login successful",Toast.LENGTH_LONG).show()

                    val intent = Intent(this,Home::class.java)
                    intent.putExtra("correo",email)
                    startActivity(intent)
                    finish()

                }else{
                    Toast.makeText(this,
                        task.exception?.message ?: "Authentication failed",
                        Toast.LENGTH_LONG).show()
                }
            }
    }

    override fun onStart() {
        super.onStart()

        var usuarioActual = auth.currentUser
        verificaUsuario(usuarioActual)
    }

    fun verificaUsuario(usuario : FirebaseUser?){

        if(usuario!=null)
        {
            Toast.makeText(this,"Usuario previamente autenticado", Toast.LENGTH_LONG).show()
            startActivity(Intent(this,Home ::class.java))
            finish()
        }
    }
}