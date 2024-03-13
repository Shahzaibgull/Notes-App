package com.example.notesapp

import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Window
import android.widget.Button
import com.example.notesapp.databinding.ActivitySubCategoryBinding

class SubCategory : AppCompatActivity() {
    private lateinit var binding: ActivitySubCategoryBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivitySubCategoryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.SubCategoryBackID.setOnClickListener {
            startActivity(Intent(this, CreateNotes::class.java))
        }
        binding.textViewAddCategory.setOnClickListener {
            showExitCustomDialogBox()
        }
    }
    private fun showExitCustomDialogBox(){      // custom dialog box
        val dialog= Dialog(this)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(false)
        dialog.setContentView(R.layout.layout_custom_dailog_notes)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        val btnYes : Button = dialog.findViewById(R.id.buttonYes)
        val btnNo : Button = dialog.findViewById(R.id.buttonNo)

        btnYes.setOnClickListener {
            finish()
        }
        btnNo.setOnClickListener {
            dialog.dismiss()
        }
        dialog.show()
    }
}