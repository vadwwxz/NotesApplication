package com.test.notes.activities

import androidx.activity.result.contract.ActivityResultContracts
import kotlin.properties.Delegates
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.method.ScrollingMovementMethod
import android.util.Log
import androidx.activity.result.ActivityResultLauncher
import com.test.notes.Constants
import com.test.notes.databinding.ActivityClickOnItemBinding

class ClickOnItemActivity : AppCompatActivity() {
    private val binding by lazy {
        ActivityClickOnItemBinding.inflate(layoutInflater)
    }
    private var launcher by Delegates.notNull<ActivityResultLauncher<Intent>>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        binding.textViewOnClickTitle.text = intent.getStringExtra(Constants.TITLE)
        binding.textViewOnClickContent.text = intent.getStringExtra(Constants.CONTENT)
        binding.textViewOnClickContent.movementMethod = ScrollingMovementMethod()

        binding.buttonDelete.setOnClickListener {
            val intent = Intent()
            intent.putExtra(Constants.DELETE, Constants.DELETE)
            setResult(RESULT_OK, intent)
            finish()
        }
        binding.buttonExit.setOnClickListener {
            if (binding.buttonExit.text == "Exit") {
                finish()
            } else {
                val intent = Intent()
                intent.putExtra(Constants.UPDATE_TITLE, binding.textViewOnClickTitle.text.toString())
                intent.putExtra(Constants.UPDATE_CONTENT, binding.textViewOnClickContent.text.toString())
                setResult(RESULT_OK, intent)
                finish()
            }
        }
        launcher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            if (it.resultCode == RESULT_OK) {
                val title = it.data?.getStringExtra(Constants.TITLE)
                val content = it.data?.getStringExtra(Constants.CONTENT)
                binding.textViewOnClickTitle.text = title
                binding.textViewOnClickContent.text = content
                val intent = Intent()
                intent.putExtra(Constants.TITLE, binding.textViewOnClickTitle.text.toString())
                intent.putExtra(Constants.CONTENT, binding.textViewOnClickContent.text.toString())
                setResult(RESULT_OK, intent)
                binding.buttonExit.text = "Save"
            }
        }
        binding.buttonUpdate.setOnClickListener {
            val intent = Intent(this@ClickOnItemActivity, UpdateTextActivity::class.java)
            intent.putExtra(Constants.TITLE, binding.textViewOnClickTitle.text.toString())
            intent.putExtra(Constants.CONTENT, binding.textViewOnClickContent.text.toString())
            launcher.launch(intent)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}