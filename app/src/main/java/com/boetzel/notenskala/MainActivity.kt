package com.boetzel.notenskala

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.widget.*

private const val LOGTAG = "MainActivity"

class MainActivity : AppCompatActivity() {
    val notenSkala = NotenSkala()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        this.initSpinners()

        this.initPoints()

        this.updateGrade()
    }

    private fun updateGrade()   {
        Log.d(LOGTAG, "entering private fun updateGrade()")

        val myGradeTextView: TextView = findViewById(R.id.myGrade)

        myGradeTextView.setText(notenSkala.calcGrade().toString())

        Log.d(LOGTAG, "leaving private fun updateGrade()")
    }

    private fun initPoints() {
        Log.d(LOGTAG, "entering private fun initPoints()")

        val textViews = mapOf("maxPointsNumberDecimal" to "maxPoints",
            "suffPointsNumberDecimal" to "suffPoints",
            "myPointsNumberDecimal" to "myPoints"
            )

        for (textView in textViews) {
            val actualId: Int = getResources().getIdentifier(textView.key, "id",getPackageName())
            val actualTextView: EditText = findViewById(actualId)

            val gradeProperty = textView.value.toString()
            val gradePropertyValue = actualTextView.text.toString().toDouble()

            notenSkala.set(gradeProperty, gradePropertyValue)

            var onTextChanged = {}

            if (gradeProperty == "maxPoints") {
                onTextChanged = {
                    notenSkala.set(gradeProperty, gradePropertyValue)
                    this@MainActivity.updateGrade()
                }
            }

            actualTextView.addTextChangedListener(object : TextWatcher {
                override fun afterTextChanged(s: Editable) {}
                override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
                override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) { onTextChanged() }
            })
        }

        Log.d(LOGTAG, "leaving private fun initPoints()")
    }

    // method to init the 2 spinners on the main activity
    private fun initSpinners() {
        Log.d(LOGTAG, "entering private fun initSpinners()")
        // Create the Spinners for point path and grade round
        val pointsSpinner: Spinner = findViewById(R.id.pointsPathSpinner)
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter.createFromResource(
            this,
            R.array.points_path_array,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            // Specify the layout to use when the list of choices appears
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            // Apply the adapter to the spinner
            pointsSpinner.adapter = adapter
        }

        val gradeSpinner: Spinner = findViewById(R.id.gradeRoundSpinner)
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter.createFromResource(
            this,
            R.array.grade_round_array,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            // Specify the layout to use when the list of choices appears
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            // Apply the adapter to the spinner
            gradeSpinner.adapter = adapter
        }
        Log.d(LOGTAG, "leaving private fun initSpinners()")
    }


}