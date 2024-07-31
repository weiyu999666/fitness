package com.example.fitness

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.commit
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : AppCompatActivity() {
    private val cardioLogs = mutableListOf<String>()
    private val strengthLogs = mutableListOf<String>()
    private var startTime: String? = null
    private var endTime: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val buttonStartWorkout: Button = findViewById(R.id.button_start_workout)
        val buttonEndWorkout: Button = findViewById(R.id.button_end_workout)
        val textViewWorkoutDetails: TextView = findViewById(R.id.textView_workout_details)
        val editTextExerciseType: EditText = findViewById(R.id.editText_exercise_type)
        val spinnerIntensity: Spinner = findViewById(R.id.spinner_intensity)
        val radioGroupExerciseType: RadioGroup = findViewById(R.id.radioGroup_exercise_type)


        ArrayAdapter.createFromResource(
            this,
            R.array.intensity_levels,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinnerIntensity.adapter = adapter
        }



        buttonStartWorkout.setOnClickListener {
            startTime = getCurrentTime()
            textViewWorkoutDetails.text = "Workout Started\nTime: $startTime"
            endTime = null
        }

        buttonEndWorkout.setOnClickListener {
            endTime = getCurrentTime()
            val exerciseType = editTextExerciseType.text.toString()
            val intensity = spinnerIntensity.selectedItem.toString()
            val selectedRadioButtonId = radioGroupExerciseType.checkedRadioButtonId
            val exerciseCategory = findViewById<RadioButton>(selectedRadioButtonId).text.toString()

            val workoutDetails = "Exercise: $exerciseType, Intensity: $intensity, Category: $exerciseCategory\nTime: $endTime"
            textViewWorkoutDetails.text = workoutDetails

            when (exerciseCategory) {
                "Cardio" -> cardioLogs.add(workoutDetails)
                "Strength" -> strengthLogs.add(workoutDetails)
            }

            startTime = null
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_cardio -> {
                supportFragmentManager.commit {
                    replace(R.id.fragment_container, CardioFragment.newInstance(ArrayList(cardioLogs)))
                    addToBackStack(null)
                }
                true
            }
            R.id.action_strength -> {
                supportFragmentManager.commit {
                    replace(R.id.fragment_container, StrengthFragment.newInstance(ArrayList(strengthLogs)))
                    addToBackStack(null)
                }
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun getCurrentTime(): String {
        val dateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
        return dateFormat.format(Date())
    }
}





