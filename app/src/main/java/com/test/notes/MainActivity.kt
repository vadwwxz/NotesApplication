package com.test.notes

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.recyclerview.widget.LinearLayoutManager
import com.test.notes.activities.AddNoteActivity
import com.test.notes.activities.ClickOnItemActivity
import com.test.notes.databinding.ActivityMainBinding
import com.test.notes.db.DataBaseManager
import com.test.notes.recyclerview.Note
import com.test.notes.recyclerview.NoteAdapter
import com.test.notes.view_model.MyViewModel
import kotlin.properties.Delegates

class MainActivity : AppCompatActivity(), NoteAdapter.Listener {

    private val binding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    private val dataBaseManager = DataBaseManager(this@MainActivity)
    lateinit var adapter: NoteAdapter
    private var launcherToWriteData by Delegates.notNull<ActivityResultLauncher<Intent>>()
    private var launcherToOnClickItem by Delegates.notNull<ActivityResultLauncher<Intent>>()
    private val myViewModel = MyViewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        //Set Title
        title = getString(R.string.actionBarTitle)
        setAdapter()
        onCLickButton()
        read()

        launcherToWriteData = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == RESULT_OK) {
                val title = result.data?.getStringExtra(Constants.TITLE).toString()
                val content = result.data?.getStringExtra(Constants.CONTENT).toString()
                dataBaseManager.insertToDataBase(title, content)
                read()
            }
        }
        launcherToOnClickItem = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == RESULT_OK) {
                val delete = result.data?.getStringExtra(Constants.DELETE)
                if (delete != null) {
                    myViewModel.note.observe(this@MainActivity) { note ->
                        dataBaseManager.deleteDataFromDataBase(note)
                    }
                    read()
                }
                val title = result.data?.getStringExtra(Constants.UPDATE_TITLE)
                val content = result.data?.getStringExtra(Constants.UPDATE_CONTENT)
                if (title != null || content != null) {
                    myViewModel.note.observe(this@MainActivity) { note ->
                        dataBaseManager.updateNoteInDataBase(note, title.toString(), content.toString())
                    }
                    read()
                }
            }
        }
    }
    private fun setAdapter() {
        adapter = NoteAdapter(this@MainActivity)
        binding.recyclerViewNotes.adapter = adapter
        binding.recyclerViewNotes.layoutManager = LinearLayoutManager(this@MainActivity)
    }
    private fun onCLickButton() {
        binding.buttonPlusNote.setOnClickListener {
            val intent = Intent(this, AddNoteActivity::class.java)
            launcherToWriteData.launch(intent)
        }
    }
    fun read() {
        val array = dataBaseManager.readDataBase()
        adapter.addNote(array)
    }

    override fun onClick(note: Note) {
        val intent = Intent(this@MainActivity, ClickOnItemActivity::class.java)
        intent.putExtra(Constants.TITLE, note.title)
        intent.putExtra(Constants.CONTENT, note.content)
        myViewModel.note.value = note
        launcherToOnClickItem.launch(intent)
    }

}