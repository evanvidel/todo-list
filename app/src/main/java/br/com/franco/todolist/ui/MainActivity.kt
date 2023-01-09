package br.com.franco.todolist.ui

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import br.com.franco.todolist.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        window.statusBarColor = Color.parseColor("#FFFFFF")

        val btnMensagem = binding.fab
        btnMensagem.setOnClickListener {
            addMessage()
        }
    }

    private fun addMessage() {
        val intent = Intent(this, AddTask::class.java)
        startActivity(intent)
        finish()
    }
}