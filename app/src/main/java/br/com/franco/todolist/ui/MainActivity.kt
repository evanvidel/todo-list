package br.com.franco.todolist.ui

import android.app.Activity
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import br.com.franco.todolist.adapter.TaskAdapter
import br.com.franco.todolist.databinding.ActivityMainBinding
import br.com.franco.todolist.datasource.FirebaseHelper
import br.com.franco.todolist.ui.AddTaskActivity.Companion.TASK_ID


class MainActivity : AppCompatActivity() {

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

        window.statusBarColor = Color.parseColor("#FFFFFF")

        binding.rvTasks.adapter = adapter
        updateList()
        insertListeners()
    }

    private fun insertListeners() {
        binding.fab.setOnClickListener {
            val intent = Intent(this, AddTaskActivity::class.java)
            resultLauncher.launch(intent)
        }

        adapter.listenerEdit = {

            val intent = Intent(this, AddTaskActivity::class.java)
            intent.putExtra(TASK_ID, it)
            resultLauncher.launch(intent)

        }
        adapter.listenerDelete = {

            FirebaseHelper.getDocumentById(it.id) { path ->
                FirebaseHelper.delete(path)
                updateList()
            }
        }
        adapter.listenerCheck = { isCheck, task ->
            Log.i("TAG1", "insertListeners: $task")
            task.isChecked = isCheck

            FirebaseHelper.getDocumentById(task.id) { doc ->
                FirebaseHelper.update(doc, task) {
                    updateList()
                }
            }

            Log.i("TAG1", "insertListeners: $task")
        }
    }

    private fun updateList() {
        FirebaseHelper.read {
            if (it.isEmpty()) {
                binding.includeEmpty.emptyState.visibility = View.VISIBLE
            } else {
                binding.includeEmpty.emptyState.visibility = View.GONE
                adapter.submitList(it)
            }
        }
    }
}