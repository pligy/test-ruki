package com.example.test_ruki

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView

class CellAdapter(private val cells: List<CellState>) : RecyclerView.Adapter<CellAdapter.CellViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CellViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.cell_item, parent, false)
        return CellViewHolder(view)
    }

    override fun onBindViewHolder(holder: CellViewHolder, position: Int) {
        val cell = cells[position]
        val drawableId = when (cell) {
            CellState.ALIVE -> R.drawable.alive_cell
            CellState.DEAD -> R.drawable.dead_cell
            CellState.LIFE -> R.drawable.life_cell
        }
        holder.cellImageView.setImageResource(drawableId)
    }

    override fun getItemCount(): Int = cells.size

    class CellViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val cellImageView: ImageView = view.findViewById(R.id.cellImageView)
    }
}
