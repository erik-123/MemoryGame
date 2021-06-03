package com.erik123.memorygame

import android.animation.ArgbEvaluator
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.erik123.memorygame.models.BoardSize
import com.erik123.memorygame.models.MemoryCard
import com.erik123.memorygame.models.MemoryGame
import com.erik123.memorygame.utils.Default_Icons
import com.google.android.material.snackbar.Snackbar

class MainActivity : AppCompatActivity() {
        //lateinit because creation occurs in OnCreate method
    companion object{
        private const val TAG ="MainActivity"

    }


    private lateinit var clRoot : ConstraintLayout
    private lateinit var rvBoard : RecyclerView
    private lateinit var tvNumMoves : TextView
    private lateinit var tvNumPairs : TextView

    private lateinit var memoryGame: MemoryGame
    private lateinit var adapter: MemoryBoardAdapter
    private var boardSize : BoardSize = BoardSize.EASY


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        clRoot = findViewById(R.id.clRoot)
        rvBoard = findViewById(R.id.rvBoard)
        tvNumMoves = findViewById(R.id.tvNumMoves)
        tvNumPairs = findViewById(R.id.tvNumPairs)

        tvNumPairs.setTextColor(ContextCompat.getColor(this, R.color.color_progress_none))
        memoryGame = MemoryGame(boardSize)

       adapter = MemoryBoardAdapter(this, boardSize, memoryGame.cards, object: MemoryBoardAdapter.CardClickListener{
            override fun onCardClicked(position: Int) {
                updateGameWithFlip(position)
            }
        })
        rvBoard.adapter = adapter
        rvBoard.setHasFixedSize(true)
        rvBoard.layoutManager = GridLayoutManager( this, boardSize.getWidth())
    }

    private fun updateGameWithFlip(position: Int) {
        //Error handling and error cases

        if(memoryGame.haveWonGame())
        {
            Snackbar.make(clRoot, "You already won!", Snackbar.LENGTH_LONG).show()
            return
        }
        if (memoryGame.isCardFaceUp(position)){
            //Invalid move
            Snackbar.make(clRoot, "Invalid move!", Snackbar.LENGTH_SHORT).show()
            return
        }

        if(memoryGame.flipCard(position))
        {
            Log.i(TAG, "Found a match! Number of Pairs found ${memoryGame.numPairsFound}")

            val color = ArgbEvaluator().evaluate(
                memoryGame.numPairsFound.toFloat() / boardSize.getNumPairs(),
                ContextCompat.getColor(this, R.color.color_progress_none),
                ContextCompat.getColor(this, R.color.color_progress_full)
            ) as Int
            tvNumPairs.setTextColor(color)

            tvNumPairs.text = "Pairs: ${memoryGame.numPairsFound} / ${boardSize.getNumPairs()}"

            if(memoryGame.haveWonGame())
            {
                Snackbar.make(clRoot,"You won! Congratulations.", Snackbar.LENGTH_LONG).show()

            }

        }

        tvNumMoves.text = "Moves: ${memoryGame.getNumMoves()}"
        adapter.notifyDataSetChanged()
    }
}