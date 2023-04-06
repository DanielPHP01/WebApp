package com.example.app7.error

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.app7.R

class ErrorActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_internet)
        supportActionBar?.hide()
        getData()
    }

    private fun getData() {
        if (intent.getStringExtra("error") != null
            && intent.getStringExtra("error")!!.isNotEmpty()
        ) {
            findViewById<TextView>(R.id.error_text_view).text = intent.getStringExtra("error")
        }
    }

    override fun onBackPressed() {
        finishAffinity()
    }
}