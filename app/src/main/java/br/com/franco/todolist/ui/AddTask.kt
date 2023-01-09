package br.com.franco.todolist.ui

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import br.com.franco.todolist.databinding.ActivityAddTaskBinding

class AddTask : AppCompatActivity() {

    private lateinit var binding: ActivityAddTaskBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddTaskBinding.inflate(layoutInflater)
        setContentView(binding.root)

        window.statusBarColor = Color.parseColor("#000000")

        val btnAddMessage = binding.btnTask
        btnAddMessage.setOnClickListener {
            tarefaSalva()
            //TODO fazer a lógica para salvar mensagem
        }
    }

    private fun tarefaSalva() {
        val intent = Intent(this,MainActivity::class.java)
        startActivity(intent)
        finish()
    }
}