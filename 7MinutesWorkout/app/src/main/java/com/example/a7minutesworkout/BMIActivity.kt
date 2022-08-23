package com.example.a7minutesworkout

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.example.a7minutesworkout.databinding.ActivityBmiBinding
import kotlin.math.pow

class BMIActivity : AppCompatActivity() {

    private var binding: ActivityBmiBinding? =null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBmiBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        setSupportActionBar(binding?.toolbarBmiActivity)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "CALCULATE BMI"

        binding?.toolbarBmiActivity?.setNavigationOnClickListener {
            onBackPressed()
        }

        binding?.btnCalculateUnits?.setOnClickListener{
            if(validateMetric()){
                val height: Float = binding?.etMetricUnitHeight?.text.toString().toFloat() / 10000
                val weight: Float = binding?.etMetricUnitWeight?.text.toString().toFloat() / 10000

                val bmi = weight / (height.pow(2))

                displayBMIResult(bmi)
            }
            else{
                Toast.makeText(this@BMIActivity,
                    "enter valid value",
                    Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun displayBMIResult(bmi:Float){
        val bmiLabel: String
        val bmiDescription: String

        if(bmi.compareTo(15f) <= 0){
            bmiLabel = "Very severely underweight!"
            bmiDescription = "Eat More!"
        }
        else if(bmi.compareTo(15f) > 0 && bmi.compareTo(16f) <=0){
            bmiLabel = "severely underweight!"
            bmiDescription = "Eat More!"
        }
        else if(bmi.compareTo(16f) > 0 && bmi.compareTo(18.5f) <=0){
            bmiLabel = "underweight!"
            bmiDescription = "Eat More!"
        }
        else if(bmi.compareTo(18.5f) > 0 && bmi.compareTo(25f) <=0){
            bmiLabel = "normal!"
            bmiDescription = "good!"
        }
        else{
            bmiLabel = "obese!"
            bmiDescription = "stop eating!"
        }
        binding?.llDisplayBMIResult?.visibility = View.VISIBLE
        binding?.tvBMIValue?.text = bmi.toString()
        binding?.tvBMIType?.text = bmiLabel
        binding?.tvBMIDescription?.text = bmiDescription
    }

    private fun validateMetric(): Boolean {
        var isValid =true

        if(binding?.etMetricUnitWeight?.text.toString().isEmpty()){
            isValid = false
        }
        else if(binding?.etMetricUnitHeight?.text.toString().isEmpty()){
            isValid = false
        }

        return isValid

    }
}