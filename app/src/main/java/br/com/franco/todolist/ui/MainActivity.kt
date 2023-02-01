package br.com.franco.todolist.ui

import android.app.Activity
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import br.com.franco.todolist.adapter.TaskAdapter
import br.com.franco.todolist.databinding.ActivityMainBinding
import br.com.franco.todolist.datasource.FirebaseHelper
import br.com.franco.todolist.datasource.TaskDataSource
import br.com.franco.todolist.model.Task


class MainActivity : AppCompatActivity() {
    private val firebaseHelper = FirebaseHelper()

    private lateinit var binding: ActivityMainBinding
    private val adapter by lazy { TaskAdapter() }
    var resultLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            if (it.resultCode == Activity.RESULT_OK) {
                updateList()
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)



        binding.btnTeste.setOnClickListener {
            createDocument()
        }

        window.statusBarColor = Color.parseColor("#FFFFFF")

        binding.rvTasks.adapter = adapter
        updateList()
        insertListeners()
    }

    fun createDocument() {
        val task = Task(
            title = "task 1", id = 6
        )
        firebaseHelper.create(task)
    }

    private fun insertListeners() {
        binding.fab.setOnClickListener {
            val intent = Intent(this, AddTaskActivity::class.java)
            resultLauncher.launch(intent)
        }

        adapter.listenerEdit = {
            val intent = Intent(this, AddTaskActivity::class.java)
            intent.putExtra(AddTaskActivity.TASK_ID, it.id)
            resultLauncher.launch(intent)
        }
        adapter.listenerDelete = {
            TaskDataSource.deleteTask(it)
            updateList()
        }

        adapter.listenerCheck = { isCheck, task ->

//            if (isCheck) {
//                TaskDataSource.insertTask(task)
//            } else {
//                TaskDataSource.insertTaskTop(task)
//            }
//            adapter.submitList(TaskDataSource.getList())
        }
    }

    private fun updateList() {
        firebaseHelper.read {
            if (it.isEmpty()) {
                binding.includeEmpty.emptyState.visibility = View.VISIBLE
            } else {
                binding.includeEmpty.emptyState.visibility = View.GONE
                adapter.submitList(it)
            }
        }
        //firebaseHelper.getDocumentByTitle()
    }
}