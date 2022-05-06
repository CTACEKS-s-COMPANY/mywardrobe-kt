package ru.alexsas.mywardrobe_kt.screens.main.tabs.settings

import ru.alexsas.mywardrobe_kt.model.boxes.entities.Box


data class BoxSetting(
    val box: Box,
    val enabled: Boolean
)