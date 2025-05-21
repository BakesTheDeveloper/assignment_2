package vcmsa.ci.assignment2

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.os.Parcel
import android.os.Parcelable
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity2() : AppCompatActivity(), Parcelable {
    val questions = arrayOf(
        "Was Columbus the first european to sail to america?",
        "Were the pyramids built by aliens?",
        "Imperialism of the 19th century caused the concentration of power in the early 20th century?",
        "Imperialist nations created feelings of pride among colonized people?",
        "Nelson Mandela was president for 10+ years?"
    )
    val answers = booleanArrayOf(true, false, true, false, false)

    var currentIndex = 0
    var score = 0

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

        updateQuestion()

        Falsebutton.setOnClickListener {
            check(true)
        }
        Truebutton.setOnClickListener {
            check(false)
        }
        Nextbutton.setOnClickListener {
            currentIndex++
            if (currentIndex < questions.size) {
                updateQuestion()
            } else {
                val intent = Intent(this, MainActivity3::class.java)
                intent.putExtra("SCORE", score)
                intent.putExtra("TOTAL", questions.size)
                startActivity(intent)
                finish()
            }
            fun updateQuestion() {
                QuestionView.text = questions[currentIndex]
                FeedbackView.text = ""
            }

            fun checkAnswer(userAnswer: Boolean) {
                val correct = answers[currentIndex]
                if (userAnswer == correct) {
                    FeedbackView.text = "Well Done! Thats Right!"
                    score++
                } else {
                    FeedbackView.text = "Sorry :( Thats Wrong."
                }

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
        }
    }}