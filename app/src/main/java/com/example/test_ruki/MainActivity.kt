// MainActivity.kt
package com.example.test_ruki

import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlin.random.Random

enum class CellState {
    ALIVE, DEAD, LIFE
}

class MainActivity : AppCompatActivity() {

    private val cells = mutableListOf<CellState>()
    private lateinit var cellRecyclerView: RecyclerView
    private lateinit var cellAdapter: CellAdapter
    private lateinit var addCellButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        cellRecyclerView = findViewById(R.id.cellRecyclerView)
        addCellButton = findViewById(R.id.addCellButton)

        if (savedInstanceState != null) {
            val savedCells = savedInstanceState.getStringArrayList("cells")
            savedCells?.let {
                cells.clear()
                cells.addAll(it.map { cell -> CellState.valueOf(cell) })
            }
        }

        cellAdapter = CellAdapter(cells)
        cellRecyclerView.adapter = cellAdapter
        cellRecyclerView.layoutManager = LinearLayoutManager(this)

        addCellButton.setOnClickListener {
            addNewCell()
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putStringArrayList("cells", ArrayList(cells.map { it.name }))
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

        cellAdapter.notifyDataSetChanged()
    }
}
