package com.example.m_tictactoe

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast

class MainActivity : AppCompatActivity(), View.OnClickListener {
    override fun onClick(v: View?) {
        btnSelected(v as Button)
    }
    private var cells = mutableMapOf<Int,String>()
    private var isX=true
    private var winner:String=""
    private val totalCell=9
    private lateinit var txtResult: TextView
    private val x =  "ㅈ"
    private val o =  "ㅎ"
    private var btns= arrayOfNulls<Button>(totalCell)
    private val combinations:Array<IntArray> = arrayOf(
        intArrayOf(0,1,2),
        intArrayOf(3,4,5),
        intArrayOf(6,7,8),
        intArrayOf(0,3,6),
        intArrayOf(1,4,7),
        intArrayOf(2,5,8),
        intArrayOf(0,4,8),
        intArrayOf(2,4,6)
    )
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        txtResult=findViewById(R.id.txtResult)

        for (i in 1..totalCell){
            var button = findViewById<Button>(resources.getIdentifier("button$i","id",packageName))
            button.setOnClickListener(this)
            btns[i-1]=button
        }
    }
    private fun btnSelected(button:Button){
        var index=0
        when(button.id){
            R.id.button1->index=0
            R.id.button2->index=1
            R.id.button3->index=2

            R.id.button4->index=3
            R.id.button5->index=4
            R.id.button6->index=5

            R.id.button7->index=6
            R.id.button8->index=7
            R.id.button9->index=8
        }
        playGame(index, button)
        checkWinner()
        update()
    }
    private fun checkWinner(){
        if(cells.isNotEmpty()){
            for(combination in combinations){
                var(a,b,c) = combination
                if(cells[a]!=null && cells[a]==cells[b] &&  cells[a]==cells[c]){
                    this.winner=cells[a].toString()
                }
            }
        }
    }
    private fun update(){
        when{
            winner.isNotEmpty()->{
                txtResult.text=resources.getString(R.string.winner,winner)
                txtResult.setTextColor(Color.GREEN)
            }
            cells.size==totalCell->{
                txtResult.text = "Empate"
            }
            else->{
                txtResult.text=resources.getString(R.string.next_player, if(isX) x else o)
            }
        }
    }
    private fun playGame(index: Int, button:Button){
        if(!winner.isNullOrEmpty()){
            Toast.makeText(this,"Juego finalizado", Toast.LENGTH_LONG).show()
            return
        }
        when{
            isX->cells[index]=x
            else->cells[index]=o
        }
        button.text=cells[index]
        button.isEnabled=false
        isX=!isX
    }
    fun resetButton(){
        for (i in 1..totalCell){
            var button = btns[i-1]
            button?.text=""
            button?.isEnabled=true
        }
    }
    fun newGame(){
        cells= mutableMapOf()
        isX=true
        winner=""
        txtResult.text=resources.getString(R.string.next_player,x)
        resetButton()
    }
    fun reset(View: View){
        newGame()
    }
}
