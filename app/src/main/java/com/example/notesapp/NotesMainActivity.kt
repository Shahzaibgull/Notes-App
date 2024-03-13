package com.example.notesapp

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.ArrayAdapter
import android.widget.SearchView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.notesapp.Room_Database_Notes.NotesUpdateEntry
import com.example.notesapp.Room_Database_Notes.User1
import com.example.notesapp.Room_Database_Notes.UserAdapter1
import com.example.notesapp.Room_Database_Notes.UserViewModel1
import com.example.notesapp.Room_Database_Notes.UserViewModelFactory1
import com.example.notesapp.databinding.ActivityNotesMainBinding

class NotesMainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityNotesMainBinding
    private lateinit var userViewModel1: UserViewModel1
    private lateinit var recyclerView: RecyclerView
    private lateinit var userAdapter1: UserAdapter1

    companion object {
        const val EDIT_USER_REQUEST = 1
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityNotesMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)

        val sharedPreferences = getSharedPreferences("Status", Context.MODE_PRIVATE) // Initialize the AutoCompleteTextView with the saved status
        val savedStatus = sharedPreferences.getString("selected_status", "")
        val items = arrayOf("All Notes", "Favorites", "Non Categories")
        val arrayAdapter = ArrayAdapter(this, R.layout.gender_list_notes, items)
        binding.status.setAdapter(arrayAdapter)

        if (savedStatus != null && savedStatus.isNotEmpty()) { // Set the selected item if it was saved previously
            val position = items.indexOf(savedStatus)
            if (position != -1) {
                binding.status.setText(savedStatus, false)
            }
        }
        binding.status.setOnItemClickListener { _, _, position, _ ->
            val selectedStatus = items[position]
            saveStatusToSharedPreferences(selectedStatus)
        }
        binding.fabID.setOnClickListener {
            startActivity(Intent(this, CreateNotes::class.java))
        }
        binding.SearchBackID.setOnClickListener {
            binding.view1.isVisible=false
            binding.searchBar.isVisible=false
            binding.SearchBackID.isVisible=false
            binding.toolbar.isVisible=true
        }
        userAdapter1 = UserAdapter1(
            this,
            ArrayList(),
            { selectedUser -> updateUser(selectedUser) }, // Update listener
            { selectedUser -> deleteUser(selectedUser) }  // Delete listener
        )
        binding.recyclerView.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(this@NotesMainActivity)
            adapter = userAdapter1
        }
        userViewModel1 = ViewModelProvider(this, UserViewModelFactory1(this))[UserViewModel1::class.java]
        userViewModel1.user1ListLiveData.observe(this, Observer { // Observe user list changes
            userAdapter1.setData(it as ArrayList<User1>)
        })
        binding.searchBar.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                return false
            }
            override fun onQueryTextChange(msg: String): Boolean {
                filter(msg)
                return false
            }
        })

        /*binding.se.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {
                filter(p0.toString())
            }
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }
        })*/
    }
    private fun filter(text: String) {
        // creating a new array list to filter our data.
        val filteredlist: ArrayList<User1> = ArrayList()

        // running a for loop to compare elements.
        for (item in userAdapter1.user1List) {
            // checking if the entered string matched with any item of our recycler view.
            if (item.title.toLowerCase().contains(text.toLowerCase())) {
                // if the item is matched we are
                // adding it to our filtered list.
                filteredlist.add(item)
            }
        }
        if (filteredlist.isEmpty()) {
            // if no item is added in filtered list we are
            // displaying a toast message as no data found.
            Toast.makeText(this, "No Data Found..", Toast.LENGTH_SHORT).show()
        } else {
            // at last we are passing that filtered
            // list to our adapter class.
            userAdapter1.filterList(filteredlist)
        }
    }

   /* fun filter(text: String) {
        val filteredCourseAry: ArrayList<User1> = ArrayList()
        for (eachCourse in userAdapter1.user1List) {
            if (eachCourse.title.toLowerCase().contains(text.toLowerCase())) {
                filteredCourseAry.add(eachCourse)
            }
        }
        userAdapter1.filterList(filteredCourseAry);
    }*/

    private fun saveStatusToSharedPreferences(selectedStatus: String) {
        val sharedPreferences = getSharedPreferences("Status", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putString("selected_status", selectedStatus)
        editor.apply()
    }
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.bottom_items_notes, menu)
        return true
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.Edit1 -> {
                Toast.makeText(applicationContext, "Shows Edit", Toast.LENGTH_SHORT).show()
                return true
            }
            R.id.ManageCategories2 -> {
                Toast.makeText(applicationContext, "Shows ManageCategories", Toast.LENGTH_SHORT).show()
                return true
            }
            R.id.SearchText -> {
                binding.view1.isVisible=true
                binding.searchBar.isVisible=true
                binding.SearchBackID.isVisible=true
                binding.toolbar.isVisible=false
                return true
            }
            /*R.id.DeleteText -> { }*/
        }
        return super.onOptionsItemSelected(item)
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == EDIT_USER_REQUEST && resultCode == Activity.RESULT_OK) {
            val updatedUser1 = data?.getParcelableExtra<User1>("UPDATED_USER")
            if (updatedUser1 != null) {
                val updatedUserPosition = userAdapter1.user1List.indexOfFirst { it.id == updatedUser1.id }  // Find the position of the updated user in your data list
                if (updatedUserPosition != -1) {
                    userAdapter1.user1List[updatedUserPosition] = updatedUser1  // Replace the old user with the updated user
                    userAdapter1.notifyItemChanged(updatedUserPosition)
                }
            }
        }
    }

    private fun updateUser(user1: User1) {

        val intent = Intent(this, NotesUpdateEntry::class.java).apply {
            putExtra("SELECTED_USER", user1)
        }
        startActivityForResult(intent, NotesMainActivity.EDIT_USER_REQUEST)
    }

    private fun deleteUser(user1: User1) {
        userViewModel1.deleteUser(applicationContext, user1)
    }
}


