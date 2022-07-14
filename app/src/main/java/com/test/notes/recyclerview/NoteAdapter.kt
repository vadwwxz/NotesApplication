package com.test.notes.recyclerview

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.test.notes.R
import com.test.notes.databinding.NoteItemExampleBinding

class NoteAdapter(val listener: Listener) : RecyclerView.Adapter<NoteAdapter.NoteHolder>() {

    var listOfNotes: MutableList<Note> = mutableListOf()

    class NoteHolder(view: View) : RecyclerView.ViewHolder(view){
        private val binding = NoteItemExampleBinding.bind(view)
        fun bind(note: Note, listener: Listener) = with(binding) {
            textViewTitle.text = note.title
            textViewContent.text = note.content
            binding.cardView.setOnClickListener {
                listener.onClick(note)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.note_item_example, parent, false)
        return NoteHolder(view)
    }

    override fun onBindViewHolder(holder: NoteHolder, position: Int) {
        holder.bind(listOfNotes[position], listener)
    }

    override fun getItemCount(): Int {
        return listOfNotes.size
    }

    @SuppressLint("NotifyDataSetChanged")
    fun addNote(noteList: MutableList<Note>) {
        listOfNotes = noteList
        notifyDataSetChanged()
    }

    interface Listener {
        fun onClick(note: Note)
    }
}