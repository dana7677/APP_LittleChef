package com.example.app_littlechef

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.app_littlechef.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        setClickers()


    }
    public fun setClickers()
    {
        binding.searchReceipeBtn.setOnClickListener {
            val intent: Intent = Intent(this, SearchRecipesActivity::class.java)
            startActivity(intent)
        }
        binding.MyReceipesBtn.setOnClickListener {
            val intent: Intent = Intent(this, MyRecipesActivity::class.java)
            startActivity(intent)
        }

    }
}