package com.example.emmaleegomezguicalculator

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import android.widget.Button
import android.view.View
import com.google.android.material.snackbar.Snackbar
import kotlin.math.sqrt

class MainActivity : AppCompatActivity() {
    private lateinit var buttons: Array<Button>
    private lateinit var display : EditText
    private var expression: StringBuilder = StringBuilder()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        display = findViewById(R.id.display)
        expression = StringBuilder(display.text)


        buttons = arrayOf(
            findViewById(R.id.zero),
            findViewById(R.id.one),
            findViewById(R.id.two),
            findViewById(R.id.three),
            findViewById(R.id.four),
            findViewById(R.id.five),
            findViewById(R.id.six),
            findViewById(R.id.seven),
            findViewById(R.id.eight),
            findViewById(R.id.nine),
            findViewById(R.id.add),
            findViewById(R.id.subtract),
            findViewById(R.id.multiply),
            findViewById(R.id.divide),
            findViewById(R.id.sqrt),
            findViewById(R.id.decimal),
            findViewById(R.id.equal)
        )

        for (button in buttons) {
            button.setOnClickListener { view: View ->
                numPadClick(view)
            }
        }
    }

    private fun updateEditText() {
        display.append(expression.last().toString())
    }

    private fun evaluateExpression(view: View) {
        val eq: String = display.text.toString()
        val arrayNumOperators: ArrayList<String> = ArrayList(eq.split("(?<=[√*+/-])|(?=[√*+/-])".toRegex()))

        if (arrayNumOperators.isEmpty()) {
            return
        }

        while (arrayNumOperators.indexOf("√") != -1) {
            val sqrtIndex = arrayNumOperators.indexOf("√")
            try {
                val operand = arrayNumOperators[sqrtIndex + 1].toDouble()
                if (operand < 0) {
                    Snackbar.make(
                        view,
                        R.string.negative_sqrt,
                        Snackbar.LENGTH_SHORT
                    ).show()
                    return
                }
                val result = sqrt(operand)
                arrayNumOperators.add(sqrtIndex, result.toString())
                arrayNumOperators.removeAt(sqrtIndex + 1)
                arrayNumOperators.removeAt(sqrtIndex + 1) // Remove the processed operand
            } catch (e: NumberFormatException) {
                Snackbar.make(
                    view,
                    R.string.invalid_sqrt,
                    Snackbar.LENGTH_SHORT
                ).show()
                return
            }
        }

        while (arrayNumOperators.indexOf("*") != -1 && arrayNumOperators.indexOf("/") != -1) {
            val multIndex = arrayNumOperators.indexOf("*")
            val divIndex = arrayNumOperators.indexOf("/")
            if (multIndex < divIndex) {
                try {
                    val num1 = arrayNumOperators[multIndex - 1]
                    val num2 = arrayNumOperators[multIndex + 1]
                    val result = num1.toDouble() * num2.toDouble()

                    arrayNumOperators[multIndex - 1] = result.toString()
                    arrayNumOperators.removeAt(multIndex)
                    arrayNumOperators.removeAt(multIndex)

                } catch (e: NumberFormatException) {
                    Snackbar.make(
                        view,
                        R.string.invalid_multiplication,
                        Snackbar.LENGTH_SHORT
                    ).show()
                    return
                }

            } else {
                try {
                    val num1 = arrayNumOperators[divIndex - 1].toDouble()
                    val num2 = arrayNumOperators[divIndex + 1].toDouble()
                    if (num2 == 0.0) {
                        Snackbar.make(
                            view,
                            R.string.divide_by_zero,
                            Snackbar.LENGTH_SHORT
                        ).show()
                        return
                    }

                    val result = num1 / num2
                    arrayNumOperators[divIndex - 1] = result.toString()
                    arrayNumOperators.removeAt(divIndex)
                    arrayNumOperators.removeAt(divIndex)

                } catch (e: NumberFormatException) {
                    Snackbar.make(
                        view,
                        R.string.invalid_division,
                        Snackbar.LENGTH_SHORT
                    ).show()
                    return
                }

            }
        }

        while (arrayNumOperators.indexOf("*") != -1) {
            val multIndex = arrayNumOperators.indexOf("*")
            try {
                val num1 = arrayNumOperators[multIndex - 1]
                val num2 = arrayNumOperators[multIndex + 1]
                val result = num1.toDouble() * num2.toDouble()

                arrayNumOperators[multIndex - 1] = result.toString()
                arrayNumOperators.removeAt(multIndex)
                arrayNumOperators.removeAt(multIndex)

            } catch (e: NumberFormatException) {
                Snackbar.make(
                    view,
                    R.string.invalid_multiplication,
                    Snackbar.LENGTH_SHORT
                ).show()
                return
            }
        }

        while(arrayNumOperators.indexOf("/") != -1) {
            val divIndex = arrayNumOperators.indexOf("/")
            try {
                val num1 = arrayNumOperators[divIndex - 1].toDouble()
                val num2 = arrayNumOperators[divIndex + 1].toDouble()
                if (num2 == 0.0) {
                    Snackbar.make(
                        view,
                        R.string.divide_by_zero,
                        Snackbar.LENGTH_SHORT
                    ).show()
                    return
                }

                val result = num1 / num2
                arrayNumOperators[divIndex - 1] = result.toString()
                arrayNumOperators.removeAt(divIndex)
                arrayNumOperators.removeAt(divIndex)

            } catch (e: NumberFormatException) {
                Snackbar.make(
                    view,
                    R.string.invalid_division,
                    Snackbar.LENGTH_SHORT
                ).show()
                return
            }

        }

        while (arrayNumOperators.indexOf("+") != -1 && arrayNumOperators.indexOf("-") != -1) {
            val addIndex = arrayNumOperators.indexOf("*")
            val subIndex = arrayNumOperators.indexOf("-")
            if (addIndex < subIndex) {
                try {
                    val num1 = arrayNumOperators[addIndex - 1]
                    val num2 = arrayNumOperators[addIndex + 1]
                    val result = num1.toDouble() + num2.toDouble()

                    arrayNumOperators[addIndex - 1] = result.toString()
                    arrayNumOperators.removeAt(addIndex)
                    arrayNumOperators.removeAt(addIndex)

                } catch (e: NumberFormatException) {
                    Snackbar.make(
                        view,
                        R.string.invalid_addition,
                        Snackbar.LENGTH_SHORT
                    ).show()
                    return
                }

            } else {
                try {
                    val num1 = arrayNumOperators[subIndex - 1]
                    val num2 = arrayNumOperators[subIndex + 1]
                    val result = num1.toDouble() - num2.toDouble()

                    arrayNumOperators[subIndex - 1] = result.toString()
                    arrayNumOperators.removeAt(subIndex)
                    arrayNumOperators.removeAt(subIndex)

                } catch (e: NumberFormatException) {
                    Snackbar.make(
                        view,
                        R.string.invalid_subtraction,
                        Snackbar.LENGTH_SHORT
                    ).show()
                    return
                }

            }
        }

        while (arrayNumOperators.indexOf("+") != -1) {
            val addIndex = arrayNumOperators.indexOf("+")
            try {
                val num1 = arrayNumOperators[addIndex - 1]
                val num2 = arrayNumOperators[addIndex + 1]
                val result = num1.toDouble() + num2.toDouble()

                arrayNumOperators[addIndex - 1] = result.toString()
                arrayNumOperators.removeAt(addIndex)
                arrayNumOperators.removeAt(addIndex)

            } catch (e: NumberFormatException) {
                Snackbar.make(
                    view,
                    R.string.invalid_addition,
                    Snackbar.LENGTH_SHORT
                ).show()
                return
            }
        }

        while (arrayNumOperators.indexOf("-") != -1) {
            val subIndex = arrayNumOperators.indexOf("-")
            try {
                val num1 = arrayNumOperators[subIndex - 1]
                val num2 = arrayNumOperators[subIndex + 1]
                val result = num1.toDouble() - num2.toDouble()

                arrayNumOperators[subIndex - 1] = result.toString()
                arrayNumOperators.removeAt(subIndex)
                arrayNumOperators.removeAt(subIndex)

            } catch (e: NumberFormatException) {
                Snackbar.make(
                    view,
                    R.string.invalid_subtraction,
                    Snackbar.LENGTH_SHORT
                ).show()
                return
            }
        }

        if (arrayNumOperators.size == 1) {
            display.setText(arrayNumOperators[0])
        } else if (arrayNumOperators.size == 2) {
            display.setText(arrayNumOperators[1])
        } else {
            display.setText(arrayNumOperators.toString())
            Snackbar.make(
                view,
                R.string.invalid_operand,
                Snackbar.LENGTH_SHORT
            ).show()
        }

    }

    private fun numPadClick(view: View) {
        when (view.id) {
            R.id.equal -> evaluateExpression(view)
            R.id.sqrt ->{
                expression.append("√")
                updateEditText()
            }
            else -> {
                expression.append((view as Button).text)
                updateEditText()
            }
        }
    }

}