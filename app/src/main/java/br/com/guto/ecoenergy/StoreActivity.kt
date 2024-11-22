package br.com.guto.ecoenergy

import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import br.com.guto.ecoenergy.model.Product
import br.com.guto.ecoenergy.model.Residence
import br.com.guto.ecoenergy.recyclerView.adapter.ProductAdapter
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class StoreActivity : AppCompatActivity() {

    private lateinit var productAdapter: ProductAdapter
    private val productList = mutableListOf<Product>()

    private val auth by lazy {
        FirebaseAuth.getInstance()
    }

    private val db by lazy {
        FirebaseFirestore.getInstance()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_store)
        window.statusBarColor = getColor(R.color.sky_blue)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val headerText = findViewById<TextView>(R.id.headerText)
        headerText.text = "Loja"

        val backArrow = findViewById<ImageView>(R.id.backArrow)
        backArrow.setOnClickListener {
            finish()
        }
    }

    override fun onResume() {
        super.onResume()
        productList.clear()

        loadPoints()
        loadProducts()

        val recyclerView =
            findViewById<androidx.recyclerview.widget.RecyclerView>(R.id.recyclerView)
        productAdapter = ProductAdapter(productList)
        productAdapter.setOnItemClickListener(object : ProductAdapter.onItemClickListener {
            override fun onItemClick(position: Int) {
                AlertDialog.Builder(this@StoreActivity).setTitle("Deseja comprar este produto?")
                    .setPositiveButton("Sim", {dialog, id ->
                        val product = productList[position]
                        Log.i("StoreActivity - onItemClick", "product: $product")
                    })
                    .setNegativeButton("Não", null).create().show()
            }
        })

        recyclerView.adapter = productAdapter
    }

    fun loadProducts() {
        db.collection("products")
            .get()
            .addOnSuccessListener { results ->
                for (document in results) {
                    Log.i("teste", "${document.id} => ${document.data}")
                    val product = Product(
                        id = document.id,
                        name = document.data["name"].toString(),
                        price = document.data["price"].toString()
                    )
                    Log.i("StoreActivity - loadProducts", "${product.name}")
                    productList.add(product)
                    productAdapter.notifyDataSetChanged()

                }
            }.addOnFailureListener { exception ->
                Log.w("StoreActivity", "Error getting documents.", exception)
            }
    }

    fun loadPoints() {
        val currentUser = auth.currentUser?.uid?: ""
        val points = findViewById<TextView>(R.id.points)
        Log.i("StoreActivity - loadPoints", "currentUser: $currentUser")
        db.collection("users")
            .document(currentUser)
            .get()
            .addOnSuccessListener { document ->
                points.text = document.data?.get("points").toString()

            }
    }

}