package br.com.franco.todolist.ui

import android.app.Activity
import android.graphics.Color
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import br.com.franco.todolist.databinding.ActivityAddTaskBinding
import br.com.franco.todolist.datasource.TaskDataSource
import br.com.franco.todolist.model.Task
import com.google.android.material.textfield.TextInputLayout

class AddTaskActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAddTaskBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddTaskBinding.inflate(layoutInflater)
        setContentView(binding.root)

        window.statusBarColor = Color.parseColor("#000000")

        binding.btnTask.setOnClickListener {
            val task = Task(
                title = binding.etTask.text.toString(),
                id = taskId

            )
            TaskDataSource.insertTask(task)
            setResult(Activity.RESULT_OK)
            finish()
        }
    }
}
