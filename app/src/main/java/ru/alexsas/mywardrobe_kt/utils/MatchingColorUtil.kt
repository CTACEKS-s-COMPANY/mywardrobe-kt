package ru.alexsas.mywardrobe_kt.utils

class MatchingColorUtil (_r:Int,_g:Int,_b:Int) {
    val r = _r
    private val g = _g
    private val b = _b

    fun threeColors(): Array<Int> {
        return arrayOf(r, g, b, g, b, r, b, r, g)
    }

    fun twoColors(): Array<Int> {
        return arrayOf(r, g, b, 255 - r, 255 - g, 255 - b)
    }

}
