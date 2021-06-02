package com.erik123.memorygame

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.erik123.memorygame.models.BoardSize
import com.erik123.memorygame.models.MemoryCard
import com.erik123.memorygame.models.MemoryGame
import com.erik123.memorygame.utils.Default_Icons

class MainActivity : AppCompatActivity() {
        //lateinit because creation occurs in OnCreate method
    companion object{
        private const val TAG ="MainActivity"

    }
    private lateinit var rvBoard : RecyclerView
    private lateinit var tvNumMoves : TextView
    private lateinit var tvNumPairs : TextView

    private var boardSize : BoardSize = BoardSize.HARD


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        rvBoard = findViewById(R.id.rvBoard)
        tvNumMoves = findViewById(R.id.tvNumMoves)
        tvNumPairs = findViewById(R.id.tvNumPairs)

        val memoryGame = MemoryGame(boardSize)

        rvBoard.adapter = MemoryBoardAdapter(this, boardSize, memoryGame.cards, object: MemoryBoardAdapter.CardClickListener{
            override fun onCardClicked(position: Int) {
                Log.i(TAG, "card clicked $position")
            }
        })
        rvBoard.setHasFixedSize(true)
        rvBoard.layoutManager = GridLayoutManager( this, boardSize.getWidth())
    }
}