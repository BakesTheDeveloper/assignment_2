package vcmsa.ci.assignment2

import android.content.Intent
import android.os.Bundle
import android.view.Gravity
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

/**
 * MainActivity3 - The Results Activity
 * This activity displays the final quiz results and provides navigation options
 */
class MainActivity3 : AppCompatActivity() {
    
    // Results manager to handle saving and loading quiz results
    private lateinit var resultsManager: ResultsManager
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge() // Enable edge-to-edge display
        setContentView(R.layout.activity_main3)
        
        // Initialize results manager
        resultsManager = ResultsManager(this)

        // Retrieve score and total questions from the intent
        val score = intent.getIntExtra("SCORE", 0)
        val total = intent.getIntExtra("TOTAL", 0)
        
        // If we have a new score (not just viewing previous results), save it
        if (score > 0 || total > 0) {
            resultsManager.saveResult(score, total)
        }

        // Display the current score in the UI
        val scoreText = findViewById<TextView>(R.id.FinalScoreView)
        scoreText.text = "Your score: $score / $total"
        
        // Display previous results
        displayPreviousResults()

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
    
    /**
     * Display previous quiz results in the UI
     */
    private fun displayPreviousResults() {
        val resultsContainer = findViewById<LinearLayout>(R.id.previousResultsContainer)
        resultsContainer.removeAllViews() // Clear existing views
        
        // Load previous results
        val results = resultsManager.loadResults()
        
        if (results.isEmpty()) {
            // If no previous results, show a message
            val noResultsText = TextView(this)
            noResultsText.text = "No previous results found"
            noResultsText.textSize = 16f
            noResultsText.gravity = Gravity.CENTER
            noResultsText.setPadding(16, 16, 16, 16)
            resultsContainer.addView(noResultsText)
        } else {
            // Display each result
            for (result in results) {
                val resultView = TextView(this)
                resultView.text = "${result.getFormattedDate()}: ${result.getScoreString()} (${result.getPercentage()}%)"
                resultView.textSize = 16f
                resultView.setPadding(16, 12, 16, 12)
                
                // Add a bottom border to each result except the last one
                if (results.indexOf(result) < results.size - 1) {
                    resultView.setBackgroundResource(android.R.drawable.divider_horizontal_bright)
                }
                
                resultsContainer.addView(resultView)
            }
        }
    }
}