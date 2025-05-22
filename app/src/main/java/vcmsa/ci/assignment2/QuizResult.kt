package vcmsa.ci.assignment2

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

/**
 * QuizResult - Data class to store quiz result information
 * @param score The user's score
 * @param totalQuestions The total number of questions in the quiz
 * @param timestamp When the quiz was completed
 */
data class QuizResult(
    val score: Int,
    val totalQuestions: Int,
    val timestamp: Long = System.currentTimeMillis()
) {
    /**
     * Get a formatted date string from the timestamp
     * @return Formatted date string
     */
    fun getFormattedDate(): String {
        val dateFormat = SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault())
        return dateFormat.format(Date(timestamp))
    }
    
    /**
     * Get a string representation of the score as a fraction
     * @return Score as a string (e.g., "3/5")
     */
    fun getScoreString(): String {
        return "$score/$totalQuestions"
    }
    
    /**
     * Get the percentage score
     * @return Score as a percentage
     */
    fun getPercentage(): Int {
        return if (totalQuestions > 0) (score * 100) / totalQuestions else 0
    }
    
    /**
     * Convert the result to a string for storage
     * @return String representation of the result
     */
    fun toStorageString(): String {
        return "$score|$totalQuestions|$timestamp"
    }
    
    companion object {
        /**
         * Create a QuizResult from a storage string
         * @param storageString String representation of a QuizResult
         * @return QuizResult object or null if parsing fails
         */
        fun fromStorageString(storageString: String): QuizResult? {
            try {
                val parts = storageString.split("|")
                if (parts.size >= 3) {
                    val score = parts[0].toInt()
                    val total = parts[1].toInt()
                    val timestamp = parts[2].toLong()
                    return QuizResult(score, total, timestamp)
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
            return null
        }
    }
}
