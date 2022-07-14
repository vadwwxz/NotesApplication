package com.test.notes.view_model

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.test.notes.recyclerview.Note

class MyViewModel : ViewModel() {
    val note: MutableLiveData<Note> by lazy {
        MutableLiveData<Note>()
    }
}