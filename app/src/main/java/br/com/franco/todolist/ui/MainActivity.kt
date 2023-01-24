package br.com.franco.todolist.ui

import android.app.Activity
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import br.com.franco.todolist.adapter.TaskAdapter
import br.com.franco.todolist.databinding.ActivityMainBinding
import br.com.franco.todolist.datasource.TaskDataSource
import br.com.franco.todolist.model.Task

@Suppress("DEPRECATION")
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val adapter by lazy { TaskAdapter() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        window.statusBarColor = Color.parseColor("#FFFFFF")


        binding.rvTasks.adapter = adapter
        updateList()
        insertListeners()
    }

    private fun insertListeners() {
        binding.fab.setOnClickListener {
            startActivityForResult(Intent(this, AddTaskActivity::class.java), CREATE_NEW_TASK)
        }

        adapter.listenerEdit = {
            val intent = Intent(this, AddTaskActivity::class.java)
            intent.putExtra(AddTaskActivity.TASK_ID, it.id)
            startActivityForResult(intent, CREATE_NEW_TASK)
        }
        adapter.listenerDelete = {
            TaskDataSource.deleteTask(it)
            updateList()
        }

        adapter.listenerCheck = { isCheck, task ->

            if (isCheck) {
                TaskDataSource.insertTask(task)
            } else {
                TaskDataSource.insertTaskTop(task)
            }
            adapter.submitList(TaskDataSource.getList())
        }
    }

    @Deprecated("Deprecated in Java")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == CREATE_NEW_TASK && resultCode == Activity.RESULT_OK) updateList()

    }

    private fun updateList() {
        TaskDataSource.apply {
            insertTask(Task("Tarefa 1"))
            insertTask(Task("Tarefa 2"))
            insertTask(Task("Tarefa 3"))
            insertTask(Task("Tarefa 4"))
        }
        val list = TaskDataSource.getList()
        binding.includeEmpty.emptyState.visibility =
            if (list.isEmpty()) View.VISIBLE else View.GONE
        adapter.submitList(list)
    }

    companion object {
        private const val CREATE_NEW_TASK = 1000
    }
}