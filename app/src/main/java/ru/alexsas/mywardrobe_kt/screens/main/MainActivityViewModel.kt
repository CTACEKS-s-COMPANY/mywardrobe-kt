package ru.alexsas.mywardrobe_kt.screens.main

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.alexsas.mywardrobe_kt.utils.share

class MainActivityViewModel(

) : ViewModel() {

    private val _username = MutableLiveData<String>()
    val username = _username.share()

}