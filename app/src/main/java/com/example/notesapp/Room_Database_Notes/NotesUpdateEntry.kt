package com.example.notesapp.Room_Database_Notes

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.notesapp.NotesMainActivity
import com.example.notesapp.databinding.ActivityNotesUpdateEntryBinding

class NotesUpdateEntry : AppCompatActivity() {

    private lateinit var binding: ActivityNotesUpdateEntryBinding
    private lateinit var userViewModel1: UserViewModel1
    private lateinit var updatedUser1: User1

    private lateinit var appDb: UserDatabase1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNotesUpdateEntryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        appDb = UserDatabase1.getInstance(this)!!
        userViewModel1 = ViewModelProvider(this, UserViewModelFactory1(this))[UserViewModel1::class.java]

        binding.updateReportBack.setOnClickListener {
            val intent = Intent(this, NotesMainActivity::class.java)
            startActivity(intent)
        }

        val selectedUser1 = intent.getParcelableExtra<User1>("SELECTED_USER")
        binding.editTitle.setText(selectedUser1?.title)
        binding.editDetails.setText(selectedUser1?.details)

        binding.buttonSave.setOnClickListener {
            updateData(selectedUser1)
        }
    }

    private fun updateData(selectedUser1: User1?) {
        val newName = binding.editTitle.text.toString().trim()
        val newAge = binding.editDetails.text.toString().trim()

        if (newName.isNotEmpty() && newAge > 0.toString()) {
            // Update the user using the UserViewModel
            selectedUser1?.let {
                it.title = newName
                it.details = newAge
                userViewModel1.updateUser(this, it)
                Toast.makeText(this, "Successfully Updated", Toast.LENGTH_SHORT).show()
            }

            // Set the result and finish the activity
            val resultIntent = Intent().apply {
                putExtra("UPDATED_USER", selectedUser1)
            }
            setResult(Activity.RESULT_OK, resultIntent)
            finish()
        } else {
            Toast.makeText(this, "Please Enter Valid Data", Toast.LENGTH_SHORT).show()
        }
    }
}
