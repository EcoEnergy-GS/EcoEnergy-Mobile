package br.com.guto.ecoenergy

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.firebase.firestore.FirebaseFirestore

class RegisterResidenceActivity : AppCompatActivity() {

    private val db by lazy {
        FirebaseFirestore.getInstance()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_register_residence)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val headerText = findViewById<TextView>(R.id.headerText)
        headerText.text = "Nova Residência"

        val backArrow = findViewById<ImageView>(R.id.backArrow)
        backArrow.setOnClickListener {
            finish()
        }

        val registerButton = findViewById<TextView>(R.id.registerButton)
        registerButton.setOnClickListener {
            val newResidence = mapOf(
                "name" to findViewById<TextView>(R.id.nameEditText).text.toString(),
                "adress" to findViewById<TextView>(R.id.adressEditText).text.toString(),
                "residents" to findViewById<TextView>(R.id.residentsEditText).text.toString(),
                "tvs" to findViewById<TextView>(R.id.tvEditText).text.toString(),
                "refrigerators" to findViewById<TextView>(R.id.refrigeratorEditText).text.toString(),
                "acs" to findViewById<TextView>(R.id.acEditText).text.toString()
            )

            if (newResidence.values.contains("")) {
                Toast.makeText(this, "Preencha todos os campos!", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            } else {
                db.collection("residences")
                    .add(newResidence)
                    .addOnSuccessListener {
                        Toast.makeText(
                            this,
                            "Residência cadastrada com sucesso!",
                            Toast.LENGTH_SHORT
                        ).show()
                        val intent = Intent(this, HomeActivity::class.java)
                        startActivity(intent)
                    }
                    .addOnFailureListener { e ->
                        AlertDialog.Builder(this)
                            .setTitle("Erro ao cadastrar residência")
                            .setMessage(e.message)
                            .setPositiveButton("OK", null)
                            .create().show()
                    }
            }

        }
    }
}