package br.com.franco.todolist.adapter

import android.annotation.SuppressLint
import android.graphics.Paint
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import br.com.franco.todolist.databinding.ItemTaskBinding
import br.com.franco.todolist.model.Task

class TaskAdapter : ListAdapter<Task, TaskAdapter.TaskViewHolder>(DiffCallBack()) {

    var listenerEdit: (Task) -> Unit = {}
    var listenerDelete: (Task) -> Unit = {}
    var listenerCheck: (isCheck: Boolean, Task) -> Unit = { _, _ -> }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemTaskBinding.inflate(inflater, parent, false)
        return TaskViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        holder.bind(getItem(position))

    }

    inner class TaskViewHolder(private val binding: ItemTaskBinding) :
        RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("SetTextI18n")
        fun bind(item: Task) {
            binding.cbTitle.setOnCheckedChangeListener(null)
            binding.cbTitle.text = item.title
            binding.tvDate.text = "${item.date} ${item.hour}"
            binding.cbTitle.isChecked = item.isChecked
            Log.i("TAG2", "binding")
            Log.i("TAG2", "marcar texto")
            markText(item.isChecked)
            binding.ivEdit.setOnClickListener {
                listenerEdit(item)
            }
            binding.ivDelete.setOnClickListener {
                listenerDelete(item)
            }

            binding.cbTitle.setOnCheckedChangeListener { _, isChecked ->
                //markText(isChecked)
                listenerCheck.invoke(isChecked, item)
            }
        }

        private fun markText(mark: Boolean) {
            if (mark) {
                binding.cbTitle.paintFlags = Paint.STRIKE_THRU_TEXT_FLAG
            } else {
                binding.cbTitle.paintFlags = 0
            }
        }
    }
}

class DiffCallBack : DiffUtil.ItemCallback<Task>() {
    override fun areItemsTheSame(oldItem: Task, newItem: Task) = oldItem == newItem

    override fun areContentsTheSame(oldItem: Task, newItem: Task) =
        (oldItem.id == newItem.id) or (oldItem.isChecked == newItem.isChecked)

}