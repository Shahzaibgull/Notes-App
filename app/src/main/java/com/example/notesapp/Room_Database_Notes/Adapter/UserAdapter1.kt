package com.example.notesapp.Room_Database_Notes

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.notesapp.R

class UserAdapter1(
    private val context: Context,
    var user1List: ArrayList<User1>,
    private val updateListener: (User1) -> Unit,
    private val deleteListener: (User1) -> Unit
) : RecyclerView.Adapter<UserAdapter1.UserViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        return UserViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.each_row_cash_in_notes,
                parent,
                false
            )
        )
    }
    // method for filtering our recyclerview items.
    fun filterList(filterlist: ArrayList<User1>) {
        // below line is to add our filtered
        // list in our course array list.
        user1List = filterlist
        // below line is to notify our adapter
        // as change in recycler view data.
        notifyDataSetChanged()
    }
    /*fun filterList(filteredCourseList: ArrayList<User1>) {
        this.user1List = filteredCourseList;
        notifyDataSetChanged();
    }*/

    override fun getItemCount(): Int = user1List.size

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        val user1: User1 = user1List[position]
        holder.itemTitle.text = user1.title
        holder.itemDetails.text = user1.details

        // Set click listeners for update and delete buttons
        holder.updateButton.setOnClickListener {
            updateListener(user1)
        }

        holder.deleteButton.setOnClickListener {
            deleteListener(user1)
        }
    }

    fun setData(newList: ArrayList<User1>) {
        val diffCallback = UserDiffCallback1(user1List, newList)
        val diffResult = DiffUtil.calculateDiff(diffCallback)

        user1List.clear()
        user1List.addAll(newList)
        diffResult.dispatchUpdatesTo(this)
    }

    inner class UserViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val itemTitle: TextView = itemView.findViewById(R.id.itemTitle)
        val itemDetails: TextView = itemView.findViewById(R.id.itemDetails)
        val updateButton: Button = itemView.findViewById(R.id.updateButton)
        val deleteButton: Button = itemView.findViewById(R.id.deleteButton)
    }

}
