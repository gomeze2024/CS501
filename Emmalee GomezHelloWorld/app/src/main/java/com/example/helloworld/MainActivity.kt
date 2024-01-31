package com.example.helloworld

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.view.View
import android.widget.TextView
import android.widget.Toast

class MainActivity : AppCompatActivity() {
    private lateinit var helloButton: Button
    private lateinit var textShow: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        helloButton = findViewById(R.id.hello_button)

        textShow = findViewById(R.id.text_show)

        helloButton.setOnClickListener { view: View ->
            textShow.text = getString(R.string.Hello_World)
        }
    }
}