package vcmsa.ci.assignment2

import android.content.Intent
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity

/**
 * QuestionEditorActivity - The Question Editor Screen
 * This activity allows users to view, add, edit, and delete quiz questions
 */
class QuestionEditorActivity : AppCompatActivity() {
    // Manager for handling question operations
    private lateinit var questionManager: QuestionManager
    
    // UI components
    private lateinit var questionsList: ListView
    private lateinit var questionEditText: EditText
    private lateinit var answerSwitch: Switch
    private lateinit var addButton: Button
    private lateinit var saveButton: Button
    private lateinit var backButton: Button
    
    // Data storage
    private var questions = arrayOf<String>()
    private var answers = booleanArrayOf()
    private val QUESTIONS_FILENAME = "custom_questions.txt" // Filename for storing questions
    
    /**
     * Called when the activity is first created
     * Initializes UI components and sets up event listeners
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_question_editor)
        
        // Initialize QuestionManager
        questionManager = QuestionManager(this)
        
        // Initialize UI components
        questionsList = findViewById(R.id.questionsList)
        questionEditText = findViewById(R.id.questionEditText)
        answerSwitch = findViewById(R.id.answerSwitch)
        addButton = findViewById(R.id.addButton)
        saveButton = findViewById(R.id.saveButton)
        backButton = findViewById(R.id.backButton)
        
        // Load existing questions
        loadQuestions()
        
        // Set up the ListView adapter to display questions
        updateListView()
        
        // Set up Add button click listener
        addButton.setOnClickListener {
            addQuestion()
        }
        
        // Set up Save button click listener
        saveButton.setOnClickListener {
            saveQuestions()
            Toast.makeText(this, "Questions saved successfully!", Toast.LENGTH_SHORT).show()
        }
        
        // Set up Back button click listener with direct navigation to MainActivity
        backButton.setOnClickListener {
            // Save questions before leaving
            saveQuestions()
            // Create an explicit intent to the MainActivity
            val intent = Intent(this@QuestionEditorActivity, MainActivity::class.java)
            startActivity(intent)
            // Make sure to call finish() to close this activity
            finish()
        }
        
        // Set up click listener for the ListView to allow editing/deleting questions
        questionsList.setOnItemClickListener { _, _, position, _ ->
            showEditDeleteDialog(position)
        }
    }
    
    /**
     * Load questions from file or use defaults
     */
    private fun loadQuestions() {
        // Try to load from file first, if not available, load from assets
        val result = questionManager.loadQuestionsFromFile(QUESTIONS_FILENAME)
        questions = result.first
        answers = result.second
    }
    
    /**
     * Update the ListView to display current questions and answers
     */
    private fun updateListView() {
        // Create a custom adapter to display questions and their answers
        val items = Array(questions.size) { i -> "${i+1}. ${questions[i]} (${if (answers[i]) "True" else "False"})" }
        val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, items)
        questionsList.adapter = adapter
    }
    
    /**
     * Add a new question from the input fields
     */
    private fun addQuestion() {
        val questionText = questionEditText.text.toString().trim()
        if (questionText.isEmpty()) {
            Toast.makeText(this, "Please enter a question", Toast.LENGTH_SHORT).show()
            return
        }
        
        val answer = answerSwitch.isChecked
        
        // Add to current arrays
        questions = questions.plus(questionText)
        answers = answers.plus(answer)
        
        // Update the ListView
        updateListView()
        
        // Clear the input field
        questionEditText.text.clear()
        answerSwitch.isChecked = false
        
        Toast.makeText(this, "Question added", Toast.LENGTH_SHORT).show()
    }
    
    /**
     * Save all questions to the file
     */
    private fun saveQuestions() {
        questionManager.saveQuestionsToFile(QUESTIONS_FILENAME, questions, answers)
    }
    
    /**
     * Show a dialog with options to edit or delete a question
     * @param position The position of the question in the list
     */
    private fun showEditDeleteDialog(position: Int) {
        val options = arrayOf("Edit", "Delete")
        
        AlertDialog.Builder(this)
            .setTitle("Question Options")
            .setItems(options) { _, which ->
                when (which) {
                    0 -> showEditDialog(position)
                    1 -> deleteQuestion(position)
                }
            }
            .show()
    }
    
    /**
     * Show a dialog to edit a question
     * @param position The position of the question to edit
     */
    private fun showEditDialog(position: Int) {
        val dialogView = layoutInflater.inflate(R.layout.dialog_edit_question, null)
        val editQuestionText = dialogView.findViewById<EditText>(R.id.editQuestionText)
        val editAnswerSwitch = dialogView.findViewById<Switch>(R.id.editAnswerSwitch)
        
        // Set current values
        editQuestionText.setText(questions[position])
        editAnswerSwitch.isChecked = answers[position]
        
        AlertDialog.Builder(this)
            .setTitle("Edit Question")
            .setView(dialogView)
            .setPositiveButton("Save") { _, _ ->
                val updatedQuestion = editQuestionText.text.toString().trim()
                if (updatedQuestion.isNotEmpty()) {
                    questions[position] = updatedQuestion
                    answers[position] = editAnswerSwitch.isChecked
                    updateListView()
                }
            }
            .setNegativeButton("Cancel", null)
            .show()
    }
    
    /**
     * Delete a question from the list
     * @param position The position of the question to delete
     */
    private fun deleteQuestion(position: Int) {
        // Remove the question and answer at the specified position
        val newQuestions = questions.toMutableList()
        val newAnswers = answers.toMutableList()
        
        newQuestions.removeAt(position)
        newAnswers.removeAt(position)
        
        questions = newQuestions.toTypedArray()
        answers = newAnswers.toBooleanArray()
        
        updateListView()
        Toast.makeText(this, "Question deleted", Toast.LENGTH_SHORT).show()
    }
    
    /**
     * Handle the system back button press
     */
    override fun onBackPressed() {
        super.onBackPressed()
        // Save questions before leaving
        saveQuestions()
        // Create an explicit intent to the MainActivity
        val intent = Intent(this@QuestionEditorActivity, MainActivity::class.java)
        startActivity(intent)
        // Make sure to call finish() to close this activity
        finish()
    }
}
