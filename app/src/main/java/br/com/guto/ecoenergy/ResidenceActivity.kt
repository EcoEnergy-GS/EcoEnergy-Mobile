package br.com.guto.ecoenergy

import android.content.Intent
import android.graphics.Typeface
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import android.widget.TableLayout
import android.widget.TableRow
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.firebase.firestore.FirebaseFirestore

class ResidenceActivity : AppCompatActivity() {

    private val db by lazy {
        FirebaseFirestore.getInstance()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_residence)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val backArrow = findViewById<ImageView>(R.id.backArrow)
        backArrow.setOnClickListener {
            finish()
        }

        val idResidence = intent.getStringExtra("id").toString()

        loadConsumption(idResidence)

        val editButton = findViewById<Button>(R.id.editButton)
        editButton.setOnClickListener {
            val intent = Intent(this, EditResidenceActivity::class.java)
            intent.putExtra("id", idResidence)
            startActivity(intent)
        }

        val deleteButton = findViewById<Button>(R.id.deleteButton)
        deleteButton.setOnClickListener {
            AlertDialog.Builder(this).setTitle("Tem certeza que deseja apagar essa residência?")
                .setPositiveButton("Sim", { dialog, id ->
                    db.collection("residence").document(idResidence).delete()
                    finish()
                }).setNegativeButton("Não", null).create().show()
        }
    }

    private fun loadConsumption(idResidence: String) {
        val tableLayout = findViewById<TableLayout>(R.id.tableLayout)
        db.collection("consumption").document(idResidence)
            .get()
            .addOnSuccessListener { document ->
                Log.d("ResidenceActivity", "Get bem sucedido")
                if (document != null) {
                    Log.d("ResidenceActivity", "Document data: ${document.data}")

                    val consumptionList = document.data?.map {
                        Pair(it.key, it.value.toString().toInt())
                    }?.sortedByDescending { it.second } ?: emptyList()

                    val headerRow = TableRow(this)
                    val monthHeader = TextView(this).apply {
                        text = "Mês"
                        textSize = 16f
                        setPadding(8, 8, 8, 8)
                        setTypeface(null, Typeface.BOLD)
                    }
                    val consumptionHeader = TextView(this).apply {
                        text = "Consumo (kWh)"
                        textSize = 16f
                        setPadding(8, 8, 8, 8)
                        setTypeface(null, Typeface.BOLD)
                    }
                    headerRow.addView(monthHeader)
                    headerRow.addView(consumptionHeader)
                    tableLayout.addView(headerRow)

                    for ((month, consumption) in consumptionList) {
                        val row = TableRow(this)
                        val monthText = TextView(this).apply {
                            text = month
                            setPadding(8, 8, 8, 8)
                        }
                        val consumptionText = TextView(this).apply {
                            text = consumption.toString()
                            setPadding(8, 8, 8, 8)
                        }
                        Log.d("ResidenceActivity", "Month: $month, Consumption: $consumption")
                        row.addView(monthText)
                        row.addView(consumptionText)
                        tableLayout.addView(row)
                    }
                }
            }
            .addOnFailureListener { e ->
                e.printStackTrace()
            }
    }
}