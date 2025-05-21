package vcmsa.ci.assignment2

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.os.Parcel
import android.os.Parcelable
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import java.io.BufferedReader
import java.io.InputStreamReader

class MainActivity2() : AppCompatActivity(), Parcelable {
    // Questions and answers will be loaded from a file or can be updated programmatically
    var questions = arrayOf<String>()
    var answers = booleanArrayOf()

    var currentIndex = 0
    var score = 0
    private var answered = false

    private lateinit var QuestionView: TextView
    private lateinit var FeedbackView: TextView
    private lateinit var Falsebutton: Button
    private lateinit var Truebutton: Button
    private lateinit var Nextbutton: Button

    constructor(parcel: Parcel) : this() {
        currentIndex = parcel.readInt()
        score = parcel.readInt()
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(currentIndex)
        parcel.writeInt(score)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<MainActivity2> {
        override fun createFromParcel(parcel: Parcel): MainActivity2 {
            return MainActivity2(parcel)
        }

        override fun newArray(size: Int): Array<MainActivity2?> {
            return arrayOfNulls(size)
        }
    }

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main2)
        QuestionView = findViewById<TextView>(R.id.QView)
        FeedbackView = findViewById<TextView>(R.id.feedbackView)
        Falsebutton = findViewById<Button>(R.id.Falsebutton)
        Truebutton = findViewById<Button>(R.id.Truebutton)
        Nextbutton = findViewById<Button>(R.id.Nextbutton)

        // Load questions from file or use default questions
        loadQuestions()
        
        updateQuestion()

        Falsebutton.setOnClickListener {
            if (!answered) {
                answered = true
                checkAnswer(false)
                
                // If this is the last question, automatically go to results after a delay
                if (currentIndex == questions.size - 1) {
                    Nextbutton.text = "See Results"
                    Nextbutton.visibility = View.VISIBLE
                } else {
                    // Enable the Next button after answering
                    Nextbutton.visibility = View.VISIBLE
                }
            }
        }
        
        Truebutton.setOnClickListener {
            if (!answered) {
                answered = true
                checkAnswer(true)
                
                // If this is the last question, automatically go to results after a delay
                if (currentIndex == questions.size - 1) {
                    Nextbutton.text = "See Results"
                    Nextbutton.visibility = View.VISIBLE
                } else {
                    // Enable the Next button after answering
                    Nextbutton.visibility = View.VISIBLE
                }
            }
        }
        
        Nextbutton.setOnClickListener {
            if (answered) {
                if (currentIndex == questions.size - 1) {
                    // If this was the last question, go to results
                    goToResults()
                } else {
                    // Move to next question
                    currentIndex++
                    updateQuestion()
                    answered = false
                    Nextbutton.visibility = View.INVISIBLE
                }
            }
        }

        // Initially hide the Next button until an answer is selected
        Nextbutton.visibility = View.INVISIBLE

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(
                systemBars.left,
                systemBars.top,
                systemBars.right,
                systemBars.bottom
            )
            insets
        }
    }
    
    // Function to navigate to results screen
    private fun goToResults() {
        val intent = Intent(this, MainActivity3::class.java)
        intent.putExtra("SCORE", score)
        intent.putExtra("TOTAL", questions.size)
        startActivity(intent)
        finish()
    }
    
    // Function to load questions from a file in assets or use default questions
    private fun loadQuestions() {
        try {
            val inputStream = assets.open("questions.txt")
            val reader = BufferedReader(InputStreamReader(inputStream))
            val questionsList = mutableListOf<String>()
            val answersList = mutableListOf<Boolean>()
            
            var line: String?
            while (reader.readLine().also { line = it } != null) {
                line?.let {
                    val parts = it.split("|")
                    if (parts.size >= 2) {
                        questionsList.add(parts[0].trim())
                        answersList.add(parts[1].trim().equals("true", ignoreCase = true))
                    }
                }
            }
            
            if (questionsList.isNotEmpty()) {
                questions = questionsList.toTypedArray()
                answers = answersList.toBooleanArray()
            } else {
                // Use default questions if file is empty
                setDefaultQuestions()
            }
            
            reader.close()
        } catch (e: Exception) {
            // If there's an error reading the file, use default questions
            setDefaultQuestions()
        }
    }
    
    // Set default questions if file loading fails
    private fun setDefaultQuestions() {
        questions = arrayOf(
            "Was Columbus the first european to sail to america?",
            "Were the pyramids built by aliens?",
            "Imperialism of the 19th century caused the concentration of power in the early 20th century?",
            "Imperialist nations created feelings of pride among colonized people?",
            "Nelson Mandela was president for 10+ years?"
        )
        answers = booleanArrayOf(true, false, true, false, false)
    }
    
    // Function to update questions programmatically
    fun updateQuestionsAndAnswers(newQuestions: Array<String>, newAnswers: BooleanArray) {
        if (newQuestions.size == newAnswers.size) {
            questions = newQuestions
            answers = newAnswers
            currentIndex = 0
            score = 0
            updateQuestion()
        }
    }
    
    // Update the question displayed on screen
    private fun updateQuestion() {
        QuestionView.text = questions[currentIndex]
        FeedbackView.text = ""
    }

    // Check if the user's answer is correct
    private fun checkAnswer(userAnswer: Boolean) {
        val correct = answers[currentIndex]
        if (userAnswer == correct) {
            FeedbackView.text = "Well Done! Thats Right!"
            score++
        } else {
            FeedbackView.text = "Sorry :( Thats Wrong."
        }
    }
}