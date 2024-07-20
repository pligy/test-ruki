package com.example.test_ruki

import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import kotlin.random.Random

enum class CellState {
    ALIVE, DEAD, LIFE
}

class MainActivity : AppCompatActivity() {

    private val cells = mutableListOf<CellState>()
    private lateinit var cellContainer: LinearLayout
    private lateinit var addCellButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        cellContainer = findViewById(R.id.cellContainer)
        addCellButton = findViewById(R.id.addCellButton)

        addCellButton.setOnClickListener {
            addNewCell()
        }
    }

    private fun addNewCell() {
        val newCell = if (Random.nextBoolean()) CellState.ALIVE else CellState.DEAD
        cells.add(newCell)

        if (cells.size >= 3) {
            val lastThreeCells = cells.takeLast(3)
            if (lastThreeCells.all { it == CellState.ALIVE }) {
                cells.subList(cells.size - 3, cells.size).clear()
                cells.add(CellState.LIFE)
            } else if (lastThreeCells.all { it == CellState.DEAD } && cells.contains(CellState.LIFE)) {
                cells.subList(cells.size - 3, cells.size).clear()
                cells.lastOrNull { it == CellState.LIFE }?.let { cells.remove(it) }
            }
        }

        updateView()
    }

    private fun updateView() {
        cellContainer.removeAllViews()
        for (cell in cells) {
            val imageView = ImageView(this)
            val drawableId = when (cell) {
                CellState.ALIVE -> R.drawable.alive_cell
                CellState.DEAD -> R.drawable.dead_cell
                CellState.LIFE -> R.drawable.life_cell
            }
            imageView.setImageResource(drawableId)
            cellContainer.addView(imageView)
        }
    }
}
