package br.com.guto.ecoenergy

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.firebase.auth.FirebaseAuth

class LoginActivity : AppCompatActivity() {

    private val auth by lazy {
        FirebaseAuth.getInstance()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_login)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val loginButton = findViewById<Button>(R.id.loginButton)
        loginButton.setOnClickListener {
            try {
                val email = findViewById<EditText>(R.id.emailEditText).text.toString()
                val password = findViewById<EditText>(R.id.passwordEditText).text.toString()
                auth.signInWithEmailAndPassword(email, password)
                    .addOnSuccessListener {
                        Toast.makeText(this, "Logado com sucesso!", Toast.LENGTH_SHORT).show()
                        val intent = Intent(this, HomeActivity::class.java)
                        startActivity(intent)
                    }
                    .addOnFailureListener {
                        AlertDialog.Builder(this)
                            .setTitle("Erro")
                            .setMessage("Verifique o email ou a senha")
                            .setPositiveButton("OK", null)
                            .create().show()
                    }
            } catch (e: Exception) {
                AlertDialog.Builder(this)
                    .setTitle("Erro")
                    .setMessage("Preencha os campos de email e senha!")
                    .setPositiveButton("OK", null)
                    .create().show()
            }
        }

        val createAccountTextView = findViewById<TextView>(R.id.createAccountTextView)
        createAccountTextView.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }
    }
}