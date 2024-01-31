package com.example.emmaleegomezsimplecalculator

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.RadioGroup
import android.widget.RadioButton

class MainActivity : AppCompatActivity() {
    private lateinit var calculateButton: Button
    private lateinit var firstNumber : EditText
    private lateinit var secondNumber: EditText

    private lateinit var radioGroup: RadioGroup
    private var currentOperator: RadioButton? = null

    private lateinit var output : TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        radioGroup = findViewById(R.id.radio_group)
        calculateButton = findViewById(R.id.calculate_button)
        firstNumber = findViewById(R.id.first)
        secondNumber = findViewById(R.id.second)
        output = findViewById(R.id.output)

        radioGroup.setOnCheckedChangeListener { _, checkedId ->
            currentOperator = findViewById(checkedId)
        }

        calculateButton.setOnClickListener {view: View ->
            try {
                val num1: Double = firstNumber.text.toString().toDouble()
                val num2: Double = secondNumber.text.toString().toDouble()

                calculateResult(view, num1, num2)
            } catch (e: NumberFormatException) {
                output.text = getString(R.string.missing_input)
            }
        }
    }

    private fun calculateResult(view: View, num1: Double, num2: Double) {
        val result: String = when (currentOperator?.id) {
            R.id.addition -> (num1 + num2).toString()
            R.id.subtraction -> (num1 - num2).toString()
            R.id.multiplication -> (num1 * num2).toString()
            R.id.division -> {
                if (num2 != 0.0) {
                    (num1 / num2).toString()
                } else {
                    getString(R.string.divide_by_zero_error)
                }
            }
            else -> {
                getString(R.string.invalid_operand)
            }
        }

        output.text = result
    }
}