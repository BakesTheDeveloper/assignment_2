package vcmsa.ci.assignment2

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

/**
 * MainActivity3 - The Results Activity
 * This activity displays the final quiz results and provides navigation options
 */
class MainActivity3 : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge() // Enable edge-to-edge display
        setContentView(R.layout.activity_main3)

        // Retrieve score and total questions from the intent
        val score = intent.getIntExtra("SCORE", 0)
        val total = intent.getIntExtra("TOTAL", 0)

        // Display the score in the UI
        val ScoreText = findViewById<TextView>(R.id.FinalScoreView)
        ScoreText.text = "Your score: $score / $total"

        // Set up Back button to return to main screen
        val backButton = findViewById<Button>(R.id.backButton)
        backButton.setOnClickListener {
            // Go back to main activity, clearing the activity stack
            val intent = Intent(this, MainActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP // Clear activities above MainActivity in the stack
            startActivity(intent)
            finish() // Close this activity
        }
        
        // Set up Exit button to completely exit the app
        val exitButton = findViewById<Button>(R.id.exitbutton)
        exitButton.setOnClickListener {
            // Exit the app completely
            finishAffinity() // Close all activities in the app
            // For older Android versions, add this as well
            System.exit(0) // Force system to terminate the process
        }
        
        // Set up window insets listener for proper edge-to-edge display
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