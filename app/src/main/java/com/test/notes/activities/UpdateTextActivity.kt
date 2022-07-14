package com.test.notes.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import com.test.notes.Constants
import com.test.notes.databinding.ActivityUpdateTextBinding

class UpdateTextActivity : AppCompatActivity() {
    private val binding by lazy {
        ActivityUpdateTextBinding.inflate(layoutInflater)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        val title = intent.getStringExtra(Constants.TITLE)
        val content = intent.getStringExtra(Constants.CONTENT)
        binding.editTextUpdateTitle.setText(title)
        binding.editTextUpdateContent.setText(content)
        binding.buttonSaveUpdateText.setOnClickListener {
            val intent = Intent()
            intent.putExtra(Constants.TITLE, binding.editTextUpdateTitle.text.toString())
            intent.putExtra(Constants.CONTENT, binding.editTextUpdateContent.text.toString())
            setResult(RESULT_OK, intent)
            finish()
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

}