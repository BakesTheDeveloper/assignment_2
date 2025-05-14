package vcmsa.ci.assignment2

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        //takes you to questions
        val startButton = findViewById<Button>(R.id.StartButton)
        //takes you to results
        val resultsButton = findViewById<Button>(R.id.ResultsButton)

        startButton.setOnClickListener {
            val toStartButton = Intent(this, StartActivity::class.java)
            startActivity(toStartButton)
        }
        resultsButton.setOnClickListener {
            val toResultsButton = Intent(this, ResultsActivity::class.java)
            startActivity(toResultsButton)
        }

    }
}