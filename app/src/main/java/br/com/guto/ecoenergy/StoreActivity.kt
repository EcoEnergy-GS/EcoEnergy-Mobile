package br.com.guto.ecoenergy

import android.os.Bundle
import android.widget.ImageView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import br.com.guto.ecoenergy.model.Product
import br.com.guto.ecoenergy.recyclerView.adapter.ProductAdapter

class StoreActivity : AppCompatActivity() {

    private lateinit var productAdapter: ProductAdapter
    private val productList = mutableListOf<Product>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_store)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val backArrow = findViewById<ImageView>(R.id.backArrow)
        backArrow.setOnClickListener {
            finish()
        }
    }

    override fun onResume() {
        super.onResume()
        productList.clear()

        productList.add(Product("1","produto 1", "20"))

        val recyclerView =
            findViewById<androidx.recyclerview.widget.RecyclerView>(R.id.recyclerView)
        productAdapter = ProductAdapter(productList)
        productAdapter.setOnItemClickListener(object : ProductAdapter.onItemClickListener {
            override fun onItemClick(position: Int) {
                AlertDialog.Builder(this@StoreActivity).setTitle("Deseja comprar este produto?")
                    .setPositiveButton("Sim", null)
                    .setNegativeButton("Não", null).
                    create().show()
            }
        })

        recyclerView.adapter = productAdapter
    }
}