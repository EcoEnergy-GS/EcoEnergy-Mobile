package br.com.guto.ecoenergy

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import br.com.guto.ecoenergy.model.Residence
import br.com.guto.ecoenergy.recyclerView.adapter.ResidenceAdapter
import com.google.firebase.firestore.FirebaseFirestore

class HomeActivity : AppCompatActivity() {

    private lateinit var residenceAdapter: ResidenceAdapter
    private val residenceList = mutableListOf<Residence>()

    private val db by lazy {
        FirebaseFirestore.getInstance()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_home)
        window.statusBarColor = getColor(R.color.sky_blue)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val logout = findViewById<ImageView>(R.id.logout)
        logout.setOnClickListener {
            finish()
        }

        val storeButton = findViewById<Button>(R.id.storeButton)
        storeButton.setOnClickListener {
            val intent = Intent(this, StoreActivity::class.java)
            startActivity(intent)
        }

        val registerButton = findViewById<Button>(R.id.registerButton)
        registerButton.setOnClickListener {
            val intent = Intent(this, RegisterResidenceActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onResume() {
        super.onResume()
        residenceList.clear()

        loadResidences()

        val recyclerView =
            findViewById<androidx.recyclerview.widget.RecyclerView>(R.id.recyclerView)

        residenceAdapter = ResidenceAdapter(residenceList)
        residenceAdapter.setOnItemClickListener(object : ResidenceAdapter.onItemClickListener {
            override fun onItemClick(position: Int) {
                val intent = Intent(this@HomeActivity, ResidenceActivity::class.java)
                intent.putExtra("id", residenceList[position].id)
                startActivity(intent)
            }
        })
        recyclerView.adapter = residenceAdapter

    }

    fun loadResidences() {
        db.collection("residences")
            .get()
            .addOnSuccessListener { results ->
                for (document in results) {
                    Log.i("teste", "${document.id} => ${document.data}")
                    val residence = Residence(
                        id = document.id,
                        name = document.data["name"].toString()
                    )
                    Log.i("teste", "${residence.name}")
                    residenceList.add(residence)
                    residenceAdapter.notifyDataSetChanged()

                }
            }.addOnFailureListener { exception ->
                Log.w("teste", "Error getting documents.", exception)
            }
    }
}