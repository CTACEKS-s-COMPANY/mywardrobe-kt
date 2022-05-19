//package ru.alexsas.mywardrobe_kt.screens.main.tabs.settings
//
//import androidx.lifecycle.MutableLiveData
//import androidx.lifecycle.ViewModel
//import androidx.lifecycle.viewModelScope
//import kotlinx.coroutines.flow.collect
//import kotlinx.coroutines.flow.combine
//import kotlinx.coroutines.launch
//import ru.alexsas.mywardrobe_kt.model.boxes.BoxesRepository
//import ru.alexsas.mywardrobe_kt.model.boxes.entities.Box
//import ru.alexsas.mywardrobe_kt.utils.share
//
//class SettingsViewModel(
//    private val boxesRepository: BoxesRepository
//) : ViewModel(), SettingsAdapter.Listener {
//
//    private val _boxSettings = MutableLiveData<List<BoxSetting>>()
//    val boxSettings = _boxSettings.share()
//
//    init {
//        viewModelScope.launch {
//            val allBoxesFlow = boxesRepository.getBoxes(onlyActive = false)
//            val activeBoxesFlow = boxesRepository.getBoxes(onlyActive = true)
//            val boxSettingsFlow = combine(allBoxesFlow, activeBoxesFlow) { allBoxes, activeBoxes ->
//                allBoxes.map { BoxSetting(it, activeBoxes.contains(it)) } // O^n2 performance, should be optimized for large lists
//            }
//            boxSettingsFlow.collect {
//                _boxSettings.value = it
//            }
//        }
//    }
//
//    fun enableBox(box: Box) {
//        viewModelScope.launch { boxesRepository.activateBox(box) }
//    }
//
//    fun disableBox(box: Box) {
//        viewModelScope.launch { boxesRepository.deactivateBox(box) }
//    }
//}