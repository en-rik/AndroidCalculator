package com.example.calculatorenrik

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity


class MainActivity : AppCompatActivity() {

    lateinit var restv : TextView
    lateinit var soltv : TextView
    lateinit var bbrac1: Button
    lateinit var bbrac2: Button
    lateinit var ac : Button
    lateinit var b0: Button
    lateinit var b9: Button
    lateinit var b8: Button
    lateinit var b7: Button
    lateinit var b6: Button
    lateinit var b5: Button
    lateinit var b4: Button
    lateinit var b3: Button
    lateinit var b2: Button
    lateinit var b1: Button
    lateinit var badd: Button
    lateinit var bequal: Button
    lateinit var bdot: Button
    lateinit var bdiv: Button
    lateinit var bmul: Button
    lateinit var bsqrt: Button
    lateinit var bsub: Button
    lateinit var bback: Button


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        restv = findViewById(R.id.res_tv)
        soltv = findViewById(R.id.sol_tv)
        ac = findViewById(R.id.button_c)
        bbrac1 = findViewById(R.id.button_bracket_1)
        bbrac2 = findViewById(R.id.button_bracket_2)
        b0 = findViewById(R.id.button_0)
        b9 = findViewById(R.id.button_9)
        b8 = findViewById(R.id.button_8)
        b7 = findViewById(R.id.button_7)
        b6 = findViewById(R.id.button_6)
        b5 = findViewById(R.id.button_5)
        b4 = findViewById(R.id.button_4)
        b3 = findViewById(R.id.button_3)
        b2 = findViewById(R.id.button_2)
        b1 = findViewById(R.id.button_1)
        bmul = findViewById(R.id.button_multi)
        bsub = findViewById(R.id.button_sub)
        badd = findViewById(R.id.button_add)
        bequal = findViewById(R.id.button_equal)
        bdot = findViewById(R.id.button_dot)
        bdiv = findViewById(R.id.button_divide)
        bsqrt = findViewById(R.id.button_sqrt)
        bback = findViewById(R.id.button_back)

        b1.setOnClickListener {
            soltv.text = (soltv.text.toString() + "1")
        }
        b2.setOnClickListener {
            soltv.text = (soltv.text.toString() + "2")
        }
        b3.setOnClickListener {
            soltv.text = (soltv.text.toString() + "3")
        }
        b4.setOnClickListener {
            soltv.text = (soltv.text.toString() + "4")
        }
        b5.setOnClickListener {
            soltv.text = (soltv.text.toString() + "5")
        }
        b6.setOnClickListener {
            soltv.text = (soltv.text.toString() + "6")
        }
        b7.setOnClickListener {
            soltv.text = (soltv.text.toString() + "7")
        }
        b8.setOnClickListener {
            soltv.text = (soltv.text.toString() + "8")
        }
        b9.setOnClickListener {
            soltv.text = (soltv.text.toString() + "9")
        }
        b0.setOnClickListener {
            soltv.text = (soltv.text.toString() + "0")
        }
        bdot.setOnClickListener {
            soltv.text = (soltv.text.toString() + ".")
        }
        badd.setOnClickListener {
            soltv.text = (soltv.text.toString() + "+")
        }
        bsub.setOnClickListener {
            soltv.text = (soltv.text.toString() + "-")
        }
        bbrac1.setOnClickListener {
            soltv.text = (soltv.text.toString() + "(")
        }
        bbrac2.setOnClickListener {
            soltv.text = (soltv.text.toString() + ")")
        }
        bdiv.setOnClickListener {
            val str: String = soltv.text.toString()
            if (!str.get(index = str.length - 1).equals("/")) {
                soltv.text = (soltv.text.toString() + "/")
            }
        }
        bmul.setOnClickListener {
            val str: String = soltv.text.toString()
            if (!str.get(index = str.length - 1).equals("*")) {
                soltv.text = (soltv.text.toString() + "*")
            }
        }
        bsqrt.setOnClickListener {
            if (soltv.text.toString().isEmpty()) {
                Toast.makeText(this, "Please enter a valid number..", Toast.LENGTH_SHORT).show()
            } else {
                val str: String = soltv.text.toString()
                soltv.text = Math.sqrt(str.toDouble()).toString()
            }
        }
        bmul.setOnClickListener {
            val str: String = soltv.text.toString()
            if (!str.get(index = str.length - 1).equals("*")) {
                soltv.text = (soltv.text.toString() + "*")
            }
        }
        bback.setOnClickListener(){
            val lenght = soltv.length()
            if (lenght > 0){
                soltv.text = soltv.text.subSequence(0, lenght - 1)
            }
        }
        bequal.setOnClickListener {
            val str: String = soltv.text.toString()
            val result: Double = evaluate(str)
            val r = result.toString()
            soltv.setText(r)
            restv.text = str
        }
        ac.setOnClickListener {
            soltv.setText("")
            restv.setText("")
        }

    }

    fun evaluate(str: String): Double {
        return object : Any() {
            var pos = -1
            var ch = 0

            fun nextChar() {
                ch = if (++pos < str.length) str[pos].toInt() else -1
            }

            fun eat(charToEat: Int): Boolean {
                while (ch == ' '.toInt()) nextChar()

                if (ch == charToEat) {
                    nextChar()
                    return true
                }
                return false
            }

            fun parse(): Double {
                nextChar()
                val x = parseExpression()
                if (pos < str.length) throw RuntimeException("Unexpected: " + ch.toChar())
                return x
            }

            fun parseExpression(): Double {
                var x = parseTerm()
                while (true) {
                    if (eat('+'.toInt())) x += parseTerm() // addition
                    else if (eat('-'.toInt())) x -= parseTerm() // subtraction
                    else return x
                }
            }

            fun parseTerm(): Double {
                var x = parseFactor()
                while (true) {
                    if (eat('*'.toInt())) x *= parseFactor() // multiplication
                    else if (eat('/'.toInt())) x /= parseFactor() // division
                    else return x
                }
            }


            fun parseFactor(): Double {
                //on below line we are checking for addition
                // and subtraction and performing unary operations.
                if (eat('+'.toInt())) return parseFactor() // unary plus
                if (eat('-'.toInt())) return -parseFactor() // unary minus
                // creating a double variable for ans.
                var x: Double
                // on below line we are creating
                // a variable for position.
                val startPos = pos
                // on below line we are checking
                // for opening and closing parenthesis.
                if (eat('('.toInt())) { // parentheses
                    x = parseExpression()
                    eat(')'.toInt())
                } else if (ch >= '0'.toInt() && ch <= '9'.toInt() || ch == '.'.toInt()) {
                    // numbers
                    while (ch >= '0'.toInt() && ch <= '9'.toInt() || ch == '.'.toInt()) nextChar()
                    // on below line we are getting sub string from our string using start and pos.
                    x = str.substring(startPos, pos).toDouble()
                } else {
                    // if the condition not satisfy then we are returning the exception
                    throw RuntimeException("Unexpected: " + ch.toChar())
                }
                // on below line we are calculating the power of the expression.
                if (eat('^'.toInt())) x = Math.pow(x, parseFactor()) // exponentiation
                return x
            }
            // at last calling a parse for our expression.
        }.parse()
    }

}