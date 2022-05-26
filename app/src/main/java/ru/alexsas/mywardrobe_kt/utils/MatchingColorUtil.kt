package ru.alexsas.mywardrobe_kt.utils

import android.graphics.Color
import androidx.core.graphics.blue
import androidx.core.graphics.green
import androidx.core.graphics.red

class MatchingColorUtil (color:Int) {
    val r = color.red
    val g = color.green
    val b = color.blue

    fun threeColors(): Array<Int> {
        return arrayOf(Color.rgb(r, g, b),Color.rgb(g, b, r),Color.rgb(b, r, g))
    }

    fun twoColors(): Array<Int> {
        return arrayOf(Color.rgb(r, g, b),Color.rgb( 255 - r, 255 - g, 255 - b))
    }

}
