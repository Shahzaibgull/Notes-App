package com.example.notesapp

import android.content.Intent
import android.graphics.Paint
import android.os.Bundle
import android.text.TextUtils
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import com.example.notesapp.Room_Database_Notes.User1
import com.example.notesapp.Room_Database_Notes.UserViewModel1
import com.example.notesapp.Room_Database_Notes.UserViewModelFactory1
import com.example.notesapp.databinding.ActivityCreateNotesBinding


class CreateNotes : AppCompatActivity() {
    private lateinit var binding: ActivityCreateNotesBinding
    private var clicks=false
    private lateinit var userViewModel1: UserViewModel1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityCreateNotesBinding.inflate(layoutInflater)
        setContentView(binding.root)

        userViewModel1 = ViewModelProvider(this, UserViewModelFactory1(this))[UserViewModel1::class.java]

        binding.CategoryID.setOnClickListener {
            startActivity(Intent(this, SubCategory::class.java))
        }
        binding.TaskID.setOnClickListener {
            if(clicks)
            {
                binding.EnterDetailsID.isVisible=true
                binding.checkID.isVisible=false
                binding.CheckEnterDetailsID.isVisible=false
                clicks=false
            }
            else
            {
                binding.EnterDetailsID.isVisible=false
                binding.checkID.isVisible=true
                binding.CheckEnterDetailsID.isVisible=true
                clicks=true
            }
        }
        binding.checkID.setOnClickListener {
            if(binding.checkID.isChecked)
            {
                binding.CheckEnterDetailsID.paintFlags = binding.CheckEnterDetailsID.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
            }
            else
            {
                binding.CheckEnterDetailsID.paintFlags = binding.CheckEnterDetailsID.paintFlags and Paint.STRIKE_THRU_TEXT_FLAG.inv()
            }
        }
        binding.saveID.setOnClickListener {
            saveDataIntoRoomDatabase1()
            Toast.makeText(this, "Notes Saved", Toast.LENGTH_SHORT).show().toString()
            finish()
        }
    }
    private fun saveDataIntoRoomDatabase1() {
        val getTitle = binding.EnterTitleID.text.toString().trim()
        val getDetails = binding.EnterDetailsID.text.toString().trim()

        if (!TextUtils.isEmpty(getTitle) && !TextUtils.isEmpty(getDetails)) {

            userViewModel1.insert1(this, User1(getTitle, getDetails))

        } else {
            Toast.makeText(applicationContext, "Please fill all the fields", Toast.LENGTH_SHORT).show()
        }
    }
}