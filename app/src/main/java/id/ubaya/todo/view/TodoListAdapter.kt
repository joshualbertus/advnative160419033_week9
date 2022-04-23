package id.ubaya.todo.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import id.ubaya.todo.R
import id.ubaya.todo.model.Todo
import kotlinx.android.synthetic.main.todo_item_layout.view.*

class TodoListAdapter(val todoList: ArrayList<Todo>, val adapterOnClick: (Todo) -> Unit) : RecyclerView.Adapter<TodoListAdapter.TodoViewHolder>() {
    class TodoViewHolder(var view: View) : RecyclerView.ViewHolder(view)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TodoViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.todo_item_layout, parent, false)

        return TodoViewHolder(view)
    }

    override fun onBindViewHolder(holder: TodoViewHolder, position: Int) {
        val todo = todoList[position]
        with(holder.view) {
            val priority = when(todo.priority) {
                1 -> "Low"
                2 -> "Medium"
                else -> "High"
            }
            checkTask.text = "[$priority] ${todoList[position].title}"
            checkTask.setOnCheckedChangeListener { compoundButton, b ->
                if (b) adapterOnClick(todo)
            }
            buttonEdit.setOnClickListener{
                val action = TodoListFragmentDirections.actionEditTodo(todo.uuid)
                Navigation.findNavController(it).navigate(action)
            }
        }
    }

    override fun getItemCount(): Int = todoList.size

    fun updateTodoList(newTodoList: List<Todo>) {
        todoList.clear()
        todoList.addAll(newTodoList)
        notifyDataSetChanged()
    }
}