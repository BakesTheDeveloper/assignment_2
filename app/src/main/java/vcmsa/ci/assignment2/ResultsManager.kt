package vcmsa.ci.assignment2

import android.content.Context
import java.io.File

/**
 * ResultsManager - Utility class to manage quiz results
 * This class provides methods to save, load, and manage quiz results
 */
class ResultsManager(private val context: Context) {
    
    // Constants
    private val RESULTS_FILENAME = "quiz_results.txt"
    private val MAX_RESULTS = 5 // Maximum number of results to store
    
    /**
     * Save a new quiz result
     * @param score The user's score
     * @param totalQuestions The total number of questions
     * @return True if saving was successful
     */
    fun saveResult(score: Int, totalQuestions: Int): Boolean {
        val result = QuizResult(score, totalQuestions)
        
        try {
            // Load existing results
            val results = loadResults().toMutableList()
            
            // Add new result at the beginning
            results.add(0, result)
            
            // Keep only the most recent MAX_RESULTS
            while (results.size > MAX_RESULTS) {
                results.removeAt(results.size - 1)
            }
            
            // Save all results back to file
            val file = File(context.filesDir, RESULTS_FILENAME)
            file.bufferedWriter().use { writer ->
                for (r in results) {
                    writer.write(r.toStorageString())
                    writer.newLine()
                }
            }
            
            return true
        } catch (e: Exception) {
            e.printStackTrace()
            return false
        }
    }
    
    /**
     * Load all saved quiz results
     * @return List of QuizResult objects, most recent first
     */
    fun loadResults(): List<QuizResult> {
        val results = mutableListOf<QuizResult>()
        
        try {
            val file = File(context.filesDir, RESULTS_FILENAME)
            if (file.exists()) {
                file.bufferedReader().useLines { lines ->
                    lines.forEach { line ->
                        QuizResult.fromStorageString(line)?.let {
                            results.add(it)
                        }
                    }
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        
        return results
    }
    
    /**
     * Clear all saved results
     * @return True if clearing was successful
     */
    fun clearResults(): Boolean {
        try {
            val file = File(context.filesDir, RESULTS_FILENAME)
            if (file.exists()) {
                return file.delete()
            }
            return true
        } catch (e: Exception) {
            e.printStackTrace()
            return false
        }
    }
}
