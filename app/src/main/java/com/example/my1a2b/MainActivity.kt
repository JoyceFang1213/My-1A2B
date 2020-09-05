package com.example.my1a2b

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var firstDiguits = findViewById<EditText>(R.id.first_number)
        var secondDiguits = findViewById<EditText>(R.id.second_number)
        var thirdDiguits = findViewById<EditText>(R.id.third_number)
        var fourthDiguits = findViewById<EditText>(R.id.fourth_number)

        fun restart() {
            firstDiguits.setText("")
            secondDiguits.setText("")
            thirdDiguits.setText("")
            fourthDiguits.setText("")
        }

        var infoList = mutableListOf(
            Info("Round", "Input", "Result")
        )

        val adapter = InfoAdapter(infoList)
        infoView.adapter = adapter
        infoView.layoutManager = LinearLayoutManager(this)

        val start = findViewById<Button>(R.id.start)
        val name = findViewById<EditText>(R.id.name)
        val tvtext = findViewById<TextView>(R.id.tv_text)

        val enter = findViewById<Button>(R.id.enter)

        val response = findViewById<TextView>(R.id.response)

        val chooseround = findViewById<RadioGroup>(R.id.choose_round)
        val round6 = findViewById<RadioButton>(R.id.round6)
        val round8 = findViewById<RadioButton>(R.id.round8)
        val round10 = findViewById<RadioButton>(R.id.round10)

        val clear = findViewById<Button>(R.id.clear)

        val newgame = findViewById<Button>(R.id.newgame)

        val computer = IntArray (4)
        val myguess = IntArray (4)
        var gamer_round = 0
        var count = 0



        start.setOnClickListener{
            restart()
            response.setText("")
            if(name.length() < 1) {
                tvtext.text = "Please input your name!"
            }
            else {
                count = 0
                tvtext.text = "Welcome! ${name.text}! Have fun!"

                var computerDigit = 0
                val judgment : MutableSet<Int> = mutableSetOf()

                for(i in 0..3) {
                    computerDigit = (Math.random() * 10).toInt()
                    while (judgment.contains(computerDigit))
                        computerDigit = (Math.random() * 10).toInt()
                    judgment.add(computerDigit)
                    computer[i] = computerDigit
                }


                if (round6.isChecked) {
                    gamer_round = 6
                }
                if (round8.isChecked) {
                    gamer_round = 8
                }
                if (round10.isChecked) {
                    gamer_round = 10
                }
            }
        }


        enter.setOnClickListener {

            var numbera = 0
            var numberb = 0

            var number = 0
            var correct = 0


            myguess[0] = if (firstDiguits.text != null && firstDiguits.text.toString()
                    .isNotEmpty()) {
                firstDiguits.text.toString().toInt()
            } else 0

            myguess[1] = if (secondDiguits.text != null && secondDiguits.text.toString()
                    .isNotEmpty()) {
                secondDiguits.text.toString().toInt()
            } else 0
            myguess[2] = if (thirdDiguits.text != null && thirdDiguits.text.toString()
                    .isNotEmpty()) {
                thirdDiguits.text.toString().toInt()
            } else 0
            myguess[3] = if (fourthDiguits.text != null && fourthDiguits.text.toString()
                    .isNotEmpty()
            ) {
                fourthDiguits.text.toString().toInt()
            } else 0

            val temp = mutableSetOf<Int>()
            for ( i in myguess) {
                temp.add(i)
            }

            if (name.text.toString().isEmpty()) {
                response.text = "Please enter your name!"
            }
            else if (firstDiguits.getText().toString().trim().length != 1 ||
                    secondDiguits.getText().toString().trim().length != 1 ||
                    thirdDiguits.getText().toString().trim().length != 1 ||
                    fourthDiguits.getText().toString().trim().length != 1 ) {
                response.text = "Should input 4 digits."
            }
            else if (temp.size != 4) {
                response.text = "Wrong input! Digits cannot be repeated!"
            }
            else {
                count += 1
                for (i in 0..3) {
                    number *= 10
                    number += myguess[i]
                    correct *= 10
                    correct += computer[i]
                }
                numbera = 0
                numberb = 0
                for (i in 0..3) {
                    if (computer.contains(myguess[i])) {
                        if (myguess[i] == computer[i])
                            numbera += 1
                        else
                            numberb += 1
                    }
                }

                val nowRound = count.toString().trim()
                val nowInput = number.toString().trim()
                val nowRes = "${numbera}a${numberb}b".trim()
                infoList.add(Info(nowRound, nowInput, nowRes))

                adapter.notifyItemInserted(infoList.size - 1)
                restart()

                if (numbera == 4) {
                    response.text = "Congratulations!!"
                }
                else if (gamer_round <= count) {
                    response.text = "So sad! The answer is ${correct}."
                } else
                    response.text = "Remain ${gamer_round - count} chances"
            }
        }

        chooseround.setOnCheckedChangeListener{ group, checkedId ->
            when (checkedId) {
                R.id.round6 -> gamer_round = 6
                R.id.round8 -> gamer_round = 8
                R.id.round10 -> gamer_round = 10
            }

            restart()
        }

        clear.setOnClickListener {
            restart()
            adapter.notifyItemRangeRemoved(1, infoList.size - 1)
            infoList.clear()
            infoList.add(Info("Round", "Input", "Result"))
        }

        newgame.setOnClickListener {
            restart()
            response.setText("")
            name.setText("")
            tvtext.setText("Welcome!")
            count = 0

            adapter.notifyItemRangeRemoved(1, infoList.size)
            infoList.clear()
            infoList.clear()
            infoList.add(Info("Round", "Input", "Result"))
        }

    }




}