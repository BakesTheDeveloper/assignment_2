package vcmsa.ci.assignment2

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

/**
 * QuestionEditorActivity - Activity for editing quiz questions
 * This activity allows users to create or modify quiz questions
 */
class QuestionEditorActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge() // Enable edge-to-edge display
        setContentView(R.layout.activity_question_editor)
        
        // Set up window insets listener for proper edge-to-edge display
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.question_editor_layout)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        
        // Initialize UI elements
        val questionEditText = findViewById<EditText>(R.id.question_edit_text)
        val option1EditText = findViewById<EditText>(R.id.option1_edit_text)
        val option2EditText = findViewById<EditText>(R.id.option2_edit_text)
        val option3EditText = findViewById<EditText>(R.id.option3_edit_text)
        val option4EditText = findViewById<EditText>(R.id.option4_edit_text)
        val correctAnswerEditText = findViewById<EditText>(R.id.correct_answer_edit_text)
        val saveButton = findViewById<Button>(R.id.save_question_button)
        val backButton = findViewById<Button>(R.id.back_button)
        
        // Set up Save button click listener
        saveButton.setOnClickListener {
            // Here you would implement the logic to save the question
            // For now, just show a toast message
            Toast.makeText(this, "Question saved (placeholder)", Toast.LENGTH_SHORT).show()
        }
        
        // Set up Back button click listener
        backButton.setOnClickListener {
            // Return to the previous activity
            finish()
        }
    }
}
