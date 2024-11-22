package br.com.guto.ecoenergy

import android.os.Bundle
import android.text.Editable
import android.util.Log
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.firebase.firestore.FirebaseFirestore

class EditResidenceActivity : AppCompatActivity() {

    private val db by lazy {
        FirebaseFirestore.getInstance()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_register_residence)

        val headerText = findViewById<TextView>(R.id.headerText)
        headerText.text = "Atualizar residência"

        val idResidence = intent.getStringExtra("id").toString()
        Log.d("id", idResidence)

        db.collection("residences").document(idResidence).get().addOnSuccessListener { residence ->
            Log.d("residence", residence.data.toString())
            val name = residence.data?.get("name").toString()
            val address = residence.data?.get("adress").toString()
            val residents = residence.data?.get("residents").toString()
            val refrigerator = residence.data?.get("refrigerators").toString()
            val tvs = residence.data?.get("tvs").toString()
            val acs = residence.data?.get("acs").toString()

            findViewById<EditText>(R.id.nameEditText).setText(name)
            findViewById<EditText>(R.id.adressEditText).setText(address)
            findViewById<EditText>(R.id.residentsEditText).setText(residents)
            findViewById<EditText>(R.id.refrigeratorEditText).setText(refrigerator)
            findViewById<EditText>(R.id.tvEditText).setText(tvs)
            findViewById<EditText>(R.id.acEditText).setText(acs)

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
            db.collection("residences").document(idResidence).set(newResidence)
                .addOnSuccessListener {
                    Toast.makeText(this, "Residência atualizada com sucesso!", Toast.LENGTH_SHORT)
                        .show()
                    finish()
                }

        }
    }
}