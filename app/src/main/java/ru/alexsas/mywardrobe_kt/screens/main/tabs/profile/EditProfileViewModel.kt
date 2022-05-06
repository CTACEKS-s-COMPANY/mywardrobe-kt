package ru.alexsas.mywardrobe_kt.screens.main.tabs.profile

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.alexsas.mywardrobe_kt.utils.MutableLiveEvent
import ru.alexsas.mywardrobe_kt.utils.MutableUnitLiveEvent
import ru.alexsas.mywardrobe_kt.utils.publishEvent
import ru.alexsas.mywardrobe_kt.utils.share

class EditProfileViewModel(
) : ViewModel() {

    private val _initialUsernameEvent = MutableLiveEvent<String>()
    val initialUsernameEvent = _initialUsernameEvent.share()

    private val _saveInProgress = MutableLiveData(false)
    val saveInProgress = _saveInProgress.share()

    private val _goBackEvent = MutableUnitLiveEvent()
    val goBackEvent = _goBackEvent.share()

    private val _showEmptyFieldErrorEvent = MutableUnitLiveEvent()
    val showEmptyFieldErrorEvent = _showEmptyFieldErrorEvent.share()




    private fun goBack() = _goBackEvent.publishEvent()

    private fun showProgress() {
        _saveInProgress.value = true
    }

    private fun hideProgress() {
        _saveInProgress.value = false
    }

    private fun showEmptyFieldErrorMessage() = _showEmptyFieldErrorEvent.publishEvent()


}