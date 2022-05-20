package ru.alexsas.mywardrobe_kt.utils

class MatchingColorUtil (_r:Int,_g:Int,_b:Int) {
    val r = _r
    val g = _g
    val b = _b

    fun ThreeColors():Array<Int>{
        val rgb:Array<Int> = arrayOf(r,g,b,g,b,r,b,r,g)
        return rgb
    }

    fun TwoColors():Array<Int>{
        val rgb:Array<Int> = arrayOf(r,g,b,255-r,255-g,255-b)
        return rgb
    }
}
