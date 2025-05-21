package vcmsa.ci.assignment2

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity3 : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main3)

        val score = intent.getIntExtra("SCORE", 0)
        val total = intent.getIntExtra("TOTAL", 0)

        val ScoreText = findViewById<TextView>(R.id.FinalScoreView)
        ScoreText.text = "Your score: $score / $total"

        // Back button to return to main screen
        val backButton = findViewById<Button>(R.id.backButton)
        backButton.setOnClickListener {
            // Go back to main activity, clearing the activity stack
            val intent = Intent(this, MainActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
            startActivity(intent)
            finish()
        }
        
        // Exit button to completely exit the app
        val exitButton = findViewById<Button>(R.id.exitbutton)
        exitButton.setOnClickListener {
            // Exit the app completely
            finishAffinity()
            // For older Android versions, add this as well
            System.exit(0)
        }
        
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}