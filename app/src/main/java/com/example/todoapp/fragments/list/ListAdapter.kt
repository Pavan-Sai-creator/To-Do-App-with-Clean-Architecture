package com.example.todoapp.fragments.list

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.todoapp.R
import com.example.todoapp.data.models.Priority
import com.example.todoapp.data.models.ToDoData

class ListAdapter : RecyclerView.Adapter<ListAdapter.MyViewHolder>() {

    var dataList = emptyList<ToDoData>()

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.row_layout, parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.itemView.findViewById<TextView>(R.id.title_txt).text = dataList[position].title
        holder.itemView.findViewById<TextView>(R.id.description_txt).text =
            dataList[position].description

        holder.itemView.findViewById<ConstraintLayout>(R.id.row_background).setOnClickListener {
            val action = ListFragmentDirections.actionListFragmentToUpdateFragment(dataList[position])
            holder.itemView.findNavController().navigate(action)
        }

        val priority = dataList[position].priority
        when (priority) {
            Priority.HIGH -> holder.itemView.findViewById<androidx.cardview.widget.CardView>(R.id.priority_indicator)
                .setCardBackgroundColor(
                    ContextCompat.getColor(
                        holder.itemView.context,
                        R.color.red
                    )
                )
            Priority.MEDIUM -> holder.itemView.findViewById<androidx.cardview.widget.CardView>(R.id.priority_indicator)
                .setCardBackgroundColor(
                    ContextCompat.getColor(
                        holder.itemView.context,
                        R.color.yellow
                    )
                )
            Priority.LOW -> holder.itemView.findViewById<androidx.cardview.widget.CardView>(R.id.priority_indicator)
                .setCardBackgroundColor(
                    ContextCompat.getColor(
                        holder.itemView.context,
                        R.color.green
                    )
                )
        }


    }
    fun setData(toDoData: List<ToDoData>){
        this.dataList = toDoData
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return dataList.size
    }
}