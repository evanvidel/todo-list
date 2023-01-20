package br.com.franco.todolist

import android.content.Context
import android.content.SharedPreferences
import br.com.franco.todolist.datasource.TaskDataSource
import br.com.franco.todolist.model.Task

class PreferencesUser(context: Context) {

    private val list = arrayListOf<Task>()

    fun getList() = list.toList()

    private val preferences: SharedPreferences =
        context.getSharedPreferences("todoList", Context.MODE_PRIVATE)

    fun saveString(key: String, str: String) {
        preferences.edit().putString(key, str).apply()
    }

    fun getString(key: String): String {
        return preferences.getString(key, "") ?: ""
    }

    fun insertTask(task: Task) {
        if (task.id == 0) {
            list.add(task.copy(id = list.size + 1))
        } else {
            // list.remove(findById(task.id))
            list.add(task)
        }
    }

}

/*val titulo = binding.cbTitle.text
if (titulo.isEmpty()) {
    PreferencesUser(this).saveString("TITULO",titulo)
    PreferencesUser(this).getString()("TITULO")

}*/
