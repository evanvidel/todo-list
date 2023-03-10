package br.com.franco.todolist.ui

import android.app.Activity
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import br.com.franco.todolist.R
import br.com.franco.todolist.databinding.ActivityAddTaskBinding
import br.com.franco.todolist.datasource.FirebaseHelper
import br.com.franco.todolist.extensions.format
import br.com.franco.todolist.extensions.text
import br.com.franco.todolist.model.Task
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.timepicker.MaterialTimePicker
import com.google.android.material.timepicker.TimeFormat
import java.util.*

class AddTaskActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAddTaskBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddTaskBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val dados = intent.extras
        val titulo = dados?.getString("titulo")
        val botao = dados?.getString("botao")
        binding.toolbar.title = titulo
        binding.btnNewTask.text = (botao)

        window.statusBarColor = Color.parseColor("#000000")
        binding.toolbar.setNavigationOnClickListener {
            startActivity(Intent(this,MainActivity::class.java))
        }

        if (intent.hasExtra(TASK_ID)) {
            val task: Task? = intent.getParcelableExtra(TASK_ID)
            task?.let {
                binding.tilTitle.text = it.title
                binding.tilDate.text = it.date
                binding.tilHour.text = it.hour
            }
        }

        insertListeners()
    }

    private fun insertListeners() {
        binding.tilDate.editText?.setOnClickListener {
            val datePicker = MaterialDatePicker.Builder.datePicker().build()
            datePicker.addOnPositiveButtonClickListener {
                val timeZone = TimeZone.getDefault()
                val offset = timeZone.getOffset(Date().time) * -1
                binding.tilDate.text = Date(it + offset).format()
            }
            datePicker.show(supportFragmentManager, "DATA_PICKER_TAG")
        }
        binding.tilHour.editText?.setOnClickListener {
            val timePicker = MaterialTimePicker.Builder()
                .setTimeFormat(TimeFormat.CLOCK_24H)
                .build()
            timePicker.addOnPositiveButtonClickListener {
                val minute =
                    if (timePicker.minute in 0..9) "0${timePicker.minute}" else timePicker.minute
                val hour = if (timePicker.hour in 0..9) "0${timePicker.hour}" else timePicker.hour

                binding.tilHour.text = "$hour:$minute"
            }
            timePicker.show(supportFragmentManager, null)
        }

        binding.btnCancel.setOnClickListener {
            finish()
        }

        binding.btnNewTask.setOnClickListener {
            if (intent.hasExtra(TASK_ID)) {
                val task: Task? = intent.getParcelableExtra(TASK_ID)
                task?.let {
                    task.title = binding.tilTitle.text
                    task.date = binding.tilDate.text
                    task.hour = binding.tilHour.text

                    FirebaseHelper.getDocumentById(task.id) { reference ->
                        FirebaseHelper.update(reference, it) {
                            setResult(Activity.RESULT_OK)
                            finish()
                        }
                    }
                }

            } else {
                val task = Task(
                    title = binding.tilTitle.text,
                    date = binding.tilDate.text,
                    hour = binding.tilHour.text,
                    id = Random().nextInt()
                )
                FirebaseHelper.create(task)
                setResult(Activity.RESULT_OK)
                finish()
            }
        }
    }

    companion object {
        const val TASK_ID = "task_id"
    }
}

