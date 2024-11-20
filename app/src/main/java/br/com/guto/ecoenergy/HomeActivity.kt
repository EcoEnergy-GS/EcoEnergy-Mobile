package br.com.guto.ecoenergy

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import br.com.guto.ecoenergy.model.Residence
import br.com.guto.ecoenergy.recyclerView.adapter.ResidenceAdapter

class HomeActivity : AppCompatActivity() {

    private lateinit var residenceAdapter: ResidenceAdapter
    private val residenceList = mutableListOf<Residence>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_home)
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

        residenceList.add(Residence("1", "Casa1"))

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
}