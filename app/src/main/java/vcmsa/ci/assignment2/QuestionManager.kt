package vcmsa.ci.assignment2

import android.content.Context
import java.io.BufferedReader
import java.io.File
import java.io.FileWriter
import java.io.InputStreamReader

/**
 * QuestionManager - Utility class to manage quiz questions
 * This class provides methods to load questions from assets or files,
 * save questions to files, and update questions programmatically.
 * 
 * @param context The application context used to access assets and files
 */
class QuestionManager(private val context: Context) {
    
    /**
     * Loads questions from the assets folder
     * @return Pair of questions array and answers boolean array
     */
    fun loadQuestionsFromAssets(): Pair<Array<String>, BooleanArray> {
        try {
            // Open the questions file from assets
            val inputStream = context.assets.open("questions.txt")
            val reader = BufferedReader(InputStreamReader(inputStream))
            val questionsList = mutableListOf<String>()
            val answersList = mutableListOf<Boolean>()
            
            // Read each line from the file
            var line: String?
            while (reader.readLine().also { line = it } != null) {
                line?.let {
                    // Split the line by the delimiter "|"
                    val parts = it.split("|")
                    if (parts.size >= 2) {
                        // Add the question and answer to their respective lists
                        questionsList.add(parts[0].trim())
                        answersList.add(parts[1].trim().equals("true", ignoreCase = true))
                    }
                }
            }
            
            reader.close()
            
            // Return the questions and answers if any were loaded
            if (questionsList.isNotEmpty()) {
                return Pair(questionsList.toTypedArray(), answersList.toBooleanArray())
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        
        // Return default questions if loading fails
        return getDefaultQuestions()
    }
    
    /**
     * Loads questions from a file in the app's private storage
     * @param filename The name of the file to load from
     * @return Pair of questions array and answers boolean array
     */
    fun loadQuestionsFromFile(filename: String): Pair<Array<String>, BooleanArray> {
        try {
            // Check if the file exists in private storage
            val file = File(context.filesDir, filename)
            if (file.exists()) {
                val questionsList = mutableListOf<String>()
                val answersList = mutableListOf<Boolean>()
                
                // Read each line from the file
                file.bufferedReader().useLines { lines ->
                    lines.forEach { line ->
                        val parts = line.split("|")
                        if (parts.size >= 2) {
                            questionsList.add(parts[0].trim())
                            answersList.add(parts[1].trim().equals("true", ignoreCase = true))
                        }
                    }
                }
                
                // Return the questions and answers if any were loaded
                if (questionsList.isNotEmpty()) {
                    return Pair(questionsList.toTypedArray(), answersList.toBooleanArray())
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        
        // If loading from file fails, try loading from assets
        return loadQuestionsFromAssets()
    }
    
    /**
     * Saves questions to a file in the app's private storage
     * @param filename The name of the file to save to
     * @param questions Array of question strings
     * @param answers Array of boolean answers
     * @return true if saving was successful, false otherwise
     */
    fun saveQuestionsToFile(filename: String, questions: Array<String>, answers: BooleanArray): Boolean {
        // Validate that questions and answers arrays have the same length
        if (questions.size != answers.size) {
            return false
        }
        
        try {
            // Create or open the file for writing
            val file = File(context.filesDir, filename)
            val writer = FileWriter(file)
            
            // Write each question and answer to the file
            for (i in questions.indices) {
                writer.append("${questions[i]}|${answers[i]}\n")
            }
            
            writer.flush()
            writer.close()
            return true
        } catch (e: Exception) {
            e.printStackTrace()
            return false
        }
    }
    
    /**
     * Adds a new question to the existing questions file
     * @param filename The name of the file to update
     * @param question The question text
     * @param answer The answer (true/false)
     * @return true if adding was successful, false otherwise
     */
    fun addQuestion(filename: String, question: String, answer: Boolean): Boolean {
        try {
            // Open the file in append mode
            val file = File(context.filesDir, filename)
            val writer = FileWriter(file, true) // Append mode
            
            // Write the new question and answer
            writer.append("$question|$answer\n")
            writer.flush()
            writer.close()
            return true
        } catch (e: Exception) {
            e.printStackTrace()
            return false
        }
    }
    
    /**
     * Returns the default set of questions and answers
     * @return Pair of questions array and answers boolean array
     */
    fun getDefaultQuestions(): Pair<Array<String>, BooleanArray> {
        val questions = arrayOf(
            "Was Columbus the first european to sail to america?",
            "Were the pyramids built by aliens?",
            "Imperialism of the 19th century caused the concentration of power in the early 20th century?",
            "Imperialist nations created feelings of pride among colonized people?",
            "Nelson Mandela was president for 10+ years?"
        )
        val answers = booleanArrayOf(true, false, true, false, false)
        
        return Pair(questions, answers)
    }
}
