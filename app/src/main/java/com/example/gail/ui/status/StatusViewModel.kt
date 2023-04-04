package com.example.gail.ui.status

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel



class StatusViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        val url  = "http://10.29.8.37/motors/1"

    }
    val text: LiveData<String> = _text
}