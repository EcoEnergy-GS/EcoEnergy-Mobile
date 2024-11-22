package br.com.guto.ecoenergy

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class RegisterActivity : AppCompatActivity() {

    private val auth by lazy {
        FirebaseAuth.getInstance()
    }

    private val db by lazy {
        FirebaseFirestore.getInstance()
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_register)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val headerText = findViewById<TextView>(R.id.headerText)
        headerText.text = "Cadastro"

        val backArrow = findViewById<ImageView>(R.id.backArrow)
        backArrow.setOnClickListener {
            finish()
        }

        val registerButton = findViewById<TextView>(R.id.registerButton)
        registerButton.setOnClickListener {

            val username = findViewById<TextView>(R.id.usernameEditText).text.toString()
            val email = findViewById<TextView>(R.id.emailEditText).text.toString()
            val password = findViewById<TextView>(R.id.passwordEditText).text.toString()
            val passwordConfirmation =
                findViewById<TextView>(R.id.passwordConfirmationEditText).text.toString()
            if (password != passwordConfirmation) {
                Toast.makeText(this, "As senhas não conferem!", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            } else {
                auth.createUserWithEmailAndPassword(email, password)
                    .addOnSuccessListener {
                        val userID = auth.currentUser?.uid ?: ""
                        val newUser = mapOf("username" to username)
                        db.collection("users")
                            .document(userID)
                            .set(newUser)
                            .addOnSuccessListener {
                                Log.i("cadastro", userID)
                                Toast.makeText(
                                    this,
                                    "Cadastro realizado com sucesso!",
                                    Toast.LENGTH_SHORT
                                )
                                    .show()
                                val intent = Intent(this, RegisterResidenceActivity::class.java)
                                startActivity(intent)
                            }


                    }
            }

        }
    }
}