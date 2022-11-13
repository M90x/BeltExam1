package com.example.beltexam1


import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.*
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    //SnackBar element
    private lateinit var clRoot: ConstraintLayout

    //deposit element declaration
    private lateinit var depositField: EditText
    private lateinit var depositBtn: Button

    //withdraw element declaration
    private lateinit var withdrawField: EditText
    private lateinit var withdrawBtn: Button

    //Recycler View declaration
    private var rvList: kotlin.collections.ArrayList<String> = ArrayList()

    //Current View
    private lateinit var currentView: TextView

    private var currentBalance = 0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        rvList = ArrayList()

        rvMain.adapter = RecyclerViewAdabter(rvList)
        rvMain.layoutManager = LinearLayoutManager(this)


        //deposit variables
        depositField = findViewById(R.id.inputDepo)
        depositBtn = findViewById(R.id.depoBtn)

        //withdraw variables
        withdrawField = findViewById(R.id.inputWithdraw)
        withdrawBtn = findViewById(R.id.withdrawBtn)

        //Current balances view
        currentView = findViewById(R.id.currView)
        currentView.text = ("Current Balance: $currentBalance")

        //SnackBar variable
        clRoot = findViewById(R.id.clRoot)

        depositBtn.setOnClickListener{ deposit() }
        withdrawBtn.setOnClickListener{ withdraw() }

    }

    private fun deposit(){
        val depositNum = depositField.text.toString()
        if(depositNum.isNotEmpty()){

            rvList.add("Deposit: $depositNum")
            autoScroll() // To make Automatically scroll to the bottom of the Recycler View

            currentBalance += depositNum.toInt()
            currentView.text = ("Current Balance: $currentBalance")

            if (currentBalance < 0) { // To change a negative color
                currentView.setTextColor(android.graphics.Color.RED)

            }else if (currentBalance > 0){ // To change a positive color
                currentView.setTextColor(android.graphics.Color.BLACK)

            }else{ // To change a Zero color
                currentView.setTextColor(android.graphics.Color.WHITE)
            }

            depositField.text.clear()
            depositField.clearFocus()
            rvMain.adapter?.notifyDataSetChanged()
        }else{
            Snackbar.make(clRoot, "Please enter a deposit number", Snackbar.LENGTH_LONG).show()
        }
    }

    private fun withdraw(){
        val withdrawNum = withdrawField.text.toString()
        if(withdrawNum.isNotEmpty()){

            if(currentBalance > 0){

                rvList.add("Withdrawal: $withdrawNum")
                autoScroll() // To make Automatically scroll to the bottom of the Recycler View

                currentBalance -= withdrawNum.toInt()
                currentView.text = ("Current Balance: $currentBalance")

                if (currentBalance < 0){
                    currentView.setTextColor(android.graphics.Color.RED) // To change a negative color

                    rvList.add("Negative Balance Fee: 20") // To Calculate balance fee
                    autoScroll() // To make Automatically scroll to the bottom of the Recycler View

                    currentBalance -= 20
                    currentView.text = ("Current Balance: $currentBalance")

                }else if (currentBalance > 0){ // To change a positive color
                    currentView.setTextColor(android.graphics.Color.BLACK)

                }else{
                    currentView.setTextColor(android.graphics.Color.WHITE)
                }

                withdrawField.text.clear()
                withdrawField.clearFocus()
                rvMain.adapter?.notifyDataSetChanged()

            }else{
                Snackbar.make(clRoot, "Sorry, There is not enough balance", Snackbar.LENGTH_LONG).show()
            }

        }else{
            Snackbar.make(clRoot, "Please enter a withdrawals number", Snackbar.LENGTH_LONG).show()
        }
    }

    private fun autoScroll(){ // To make Automatically scroll to the bottom of the Recycler View
        rvMain.smoothScrollToPosition(rvList.size-1)

    }

    private fun taost(){ //
        rvList.clear()

    }


}