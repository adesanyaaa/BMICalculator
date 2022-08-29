package com.adesoftware.bmicalculator

import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import java.text.DecimalFormat

class BMICalculatorActivity : AppCompatActivity() {
    
    private lateinit var textViewBmiCal: TextView
    private lateinit var textViewResult: TextView
    private lateinit var buttonCal: Button
    private lateinit var radioButtonMale: RadioButton
    private lateinit var radioButtonFemale: RadioButton
    private lateinit var editTextAge: EditText
    private lateinit var editTextFeet: EditText
    private lateinit var editTextInches: EditText
    private lateinit var editTextWeight: EditText


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bmi_calculator)
        findViews()
        setUpButtonClickListener()
    }

    private fun findViews() {
        textViewBmiCal = findViewById(R.id.tv_bmi_cal)
        textViewResult = findViewById(R.id.tv_result)

        radioButtonMale = findViewById(R.id.rb_male)
        radioButtonFemale = findViewById(R.id.rb_female)

        editTextAge = findViewById(R.id.et_age)
        editTextFeet = findViewById(R.id.et_feet)
        editTextInches = findViewById(R.id.et_inches)
        editTextWeight = findViewById(R.id.et_weight)

        buttonCal = findViewById(R.id.btn_cal)
    }

    private fun setUpButtonClickListener() {
        buttonCal.setOnClickListener {
            val bmiResult = calculateBmi()

            val ageText: String = editTextAge.text.toString()
            val age: Int = Integer.parseInt(ageText)

            if (age > 18) {
                displayResult(bmiResult)
            } else {
                displayGuidance(bmiResult)
            }
        }
    }

    private fun calculateBmi(): Double {
        val feetText: String = editTextFeet.text.toString()
        val inchesText: String = editTextInches.text.toString()
        val weightText: String = editTextWeight.text.toString()

        //Converting the number 'String' into 'Int' variables
        val feet: Int = Integer.parseInt(feetText)
        val inches: Int = Integer.parseInt(inchesText)
        val weight: Int = Integer.parseInt(weightText)

        val totalInches: Int = (feet * 12) + inches

        //Height in meters is inches multiplied by 0.0254
        val heightInMeters: Double = totalInches * 0.0254

        //BMI formula = weight in kg divided by height in meters squared
        return weight/(heightInMeters*heightInMeters)
    }

    private fun displayResult(bmi: Double) {
        val myDecimalFormatter = DecimalFormat("0.00")
        val bmiTextResult: String = myDecimalFormatter.format(bmi)

        val fullResultString: String
        if (bmi < 18.5) {
            //Display underweight
            fullResultString = "$bmiTextResult - You are underweight"
        } else if (bmi > 25) {
            //Display overweight
            fullResultString = "$bmiTextResult - You are overweight"
        } else {
            //Display healthy
            fullResultString = "$bmiTextResult - You are a healthy weight"
        }
        textViewResult.text = fullResultString
    }

    private fun displayGuidance(bmi: Double) {
        val myDecimalFormatter: DecimalFormat = DecimalFormat("0.00")
        val bmiTextResult: String = myDecimalFormatter.format(bmi)
        val fullResultString: String
        if(radioButtonMale.isChecked) {
            //Display boy guidance
            fullResultString = bmiTextResult + " As you are under 18, please consult your GP" +
                    "for healthy range for boys"
        } else if(radioButtonFemale.isChecked) {
            //Display girl guidance
            fullResultString = bmiTextResult + " As you are under 18, please consult your GP" +
                    "for healthy range for girls"
        } else {
            //Display general guidance
            fullResultString = "$bmiTextResult As you are under 18, please consult your GP"
        }
        textViewResult.text = fullResultString

    }

}