package com.test.notes.activities

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.test.notes.Constants
import com.test.notes.MainActivity
import com.test.notes.R
import com.test.notes.databinding.ActivityAddNoteBinding
import com.test.notes.db.DataBaseManager

class AddNoteActivity : AppCompatActivity() {
    private val binding by lazy {
        ActivityAddNoteBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        binding.buttonSave.setOnClickListener {
            val intent = Intent()
            intent.putExtra(Constants.TITLE, binding.editTextTitle.text.toString())
            intent.putExtra(Constants.CONTENT, binding.editTextContent.text.toString())
            setResult(RESULT_OK, intent)
            finish()
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}