package br.com.guto.ecoenergy.recyclerView.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import br.com.guto.ecoenergy.R
import br.com.guto.ecoenergy.model.Residence

class ResidenceAdapter(private val residences: List<Residence>) :
    RecyclerView.Adapter<ResidenceAdapter.ViewHolder>() {

    private lateinit var mlistener: onItemClickListener

    interface onItemClickListener {
        fun onItemClick(position: Int)
    }

    fun setOnItemClickListener(listener: onItemClickListener) {
        mlistener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ResidenceAdapter.ViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_residence, parent, false)
        return ViewHolder(view, mlistener)
    }

    override fun onBindViewHolder(holder: ResidenceAdapter.ViewHolder, position: Int) {
        val residence = residences[position]
        holder.residenceName.text = residence.name
        holder.residenceId.text = residence.id
    }

    override fun getItemCount(): Int {
        return residences.size
    }

    class ViewHolder(itemView: View, listener: onItemClickListener) : RecyclerView.ViewHolder(itemView) {
        val residenceName: TextView = itemView.findViewById(R.id.nameResidence)
        val residenceId: TextView = itemView.findViewById(R.id.idResidence)

        init {
            itemView.setOnClickListener {
                listener.onItemClick(adapterPosition)
            }
        }
    }


}