package vcmsa.ci.assignment2

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

/**
 * MainActivity - The Main Menu Activity
 * This is the entry point of the application that provides navigation to other activities
 */
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge() // Enable edge-to-edge display
        setContentView(R.layout.activity_main)
        
        // Set up window insets listener for proper edge-to-edge display
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        
        // Initialize UI buttons
        val startButton = findViewById<Button>(R.id.StartButton) // Button to start the quiz
        val resultsButton = findViewById<Button>(R.id.ResultsButton) // Button to view results
        val editQuestionsButton = findViewById<Button>(R.id.EditQuestionsButton) // Button to edit questions

        // Set up Start button click listener
        startButton.setOnClickListener {
            // Navigate to the quiz activity (MainActivity2)
            val toStartButton = Intent(this, MainActivity2::class.java)
            startActivity(toStartButton)
        }
        
        // Set up Results button click listener
        resultsButton.setOnClickListener {
            // Navigate to the results activity (MainActivity3)
            // Note: This will show a blank result if no quiz has been completed
            val toResultsButton = Intent(this, MainActivity3::class.java)
            startActivity(toResultsButton)
        }
        
        // Set up Edit Questions button click listener
        editQuestionsButton?.setOnClickListener {
            // Navigate to the question editor activity
            val toEditorIntent = Intent(this, QuestionEditorActivity::class.java)
            startActivity(toEditorIntent)
        }
    }
}