package ru.alexsas.mywardrobe_kt.screens.main.tabs.creating_outfit

import android.graphics.Color
import android.os.Bundle
import android.view.View
import androidx.core.graphics.toColor
import androidx.fragment.app.Fragment
import ru.alexsas.mywardrobe_kt.R
import ru.alexsas.mywardrobe_kt.databinding.FragmentOutfitBinding
import ru.alexsas.mywardrobe_kt.utils.MatchingColorUtil
import kotlin.math.max
import kotlin.math.min
import kotlin.random.Random

class OutfitFragment() : Fragment(R.layout.fragment_outfit) {
    private lateinit var mBinding: FragmentOutfitBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        var rand_max = 180
        var rand_min = 0
        var r: Int
        var g: Int
        var b: Int
        var switchOn: Boolean = true

        super.onViewCreated(view, savedInstanceState)
        mBinding = FragmentOutfitBinding.bind(view)

        mBinding.Switcher.setOnCheckedChangeListener { compoundButton, isChecked ->
            switchOn = isChecked
        }

        mBinding.GenerateButton.setOnClickListener() {
            if (switchOn){
                r = Random.nextInt(rand_min, rand_max)
                g = Random.nextInt(rand_min, rand_max)
                b = Random.nextInt(rand_min, rand_max)
                matchTwoColorPressed(r, g, b)
            }
            else{
                r = Random.nextInt(rand_min, rand_max)
                g = Random.nextInt(rand_min, rand_max)
                b = Random.nextInt(rand_min, rand_max)
                matchTreeColorPressed(r, g, b)
            }

        }
    }

    private fun matchTwoColorPressed(r:Int,g:Int,b:Int){
        val Coloring = MatchingColorUtil(r,g,b)
        val rgb = Coloring.TwoColors()

        //Футболка
        mBinding.ShirtView.setColorFilter(Color.rgb(rgb[0],rgb[1],rgb[2]))
        mBinding.ShirtBandView.setColorFilter(Color.rgb(LC(rgb[0]),LC(rgb[1]),LC(rgb[2])))

        //Штаны
        mBinding.PantsView.setColorFilter(Color.rgb(rgb[3],rgb[4],rgb[5]))
        mBinding.PantsBandView.setColorFilter(Color.rgb(LC(rgb[3]),LC(rgb[4]),LC(rgb[5])))

        //Ботинки
        mBinding.BootsTopView.clearColorFilter()
        mBinding.BootsBottomView.clearColorFilter()

        //Кубики с цветом
        mBinding.CollorViewTop.setBackgroundColor(Color.rgb(rgb[0],rgb[1],rgb[2]))
        mBinding.CollorViewMiddle.setBackgroundColor(Color.rgb(rgb[3],rgb[4],rgb[5]))
        mBinding.CollorViewBottom.setBackgroundColor(Color.rgb(255,255,255))

        mBinding.swapButton.setOnClickListener(){
            var mem_r = rgb[0]
            var mem_g = rgb[1]
            var mem_b = rgb[2]
            rgb[0] = rgb[3]
            rgb[1] = rgb[4]
            rgb[2] = rgb[5]
            rgb[3] = mem_r
            rgb[4] = mem_g
            rgb[5] = mem_b
            //Футболка
            mBinding.ShirtView.setColorFilter(Color.rgb(rgb[0],rgb[1],rgb[2]))
            mBinding.ShirtBandView.setColorFilter(Color.rgb(LC(rgb[0]),LC(rgb[1]),LC(rgb[2])))

            //Штаны
            mBinding.PantsView.setColorFilter(Color.rgb(rgb[3],rgb[4],rgb[5]))
            mBinding.PantsBandView.setColorFilter(Color.rgb(LC(rgb[3]),LC(rgb[4]),LC(rgb[5])))

            //Кубики с цветом
            mBinding.CollorViewTop.setBackgroundColor(Color.rgb(rgb[0],rgb[1],rgb[2]))
            mBinding.CollorViewMiddle.setBackgroundColor(Color.rgb(rgb[3],rgb[4],rgb[5]))
        }
    }

    private fun matchTreeColorPressed(r:Int,g:Int,b:Int){
        val Coloring = MatchingColorUtil(r,g,b)
        val rgb = Coloring.ThreeColors()
        //mBinding.matchColorButton2.setBackgroundColor(Color.rgb(r,g,b))
        //Футболка
        mBinding.ShirtView.setColorFilter(Color.rgb(rgb[0],rgb[1],rgb[2]))
        mBinding.ShirtBandView.setColorFilter(Color.rgb(LC(rgb[0]),LC(rgb[1]),LC(rgb[2])))

        //Штаны
        mBinding.PantsView.setColorFilter(Color.rgb(rgb[3],rgb[4],rgb[5]))
        mBinding.PantsBandView.setColorFilter(Color.rgb(LC(rgb[3]),LC(rgb[4]),LC(rgb[5])))

        //Ботинки
        mBinding.BootsTopView.setColorFilter(Color.rgb(rgb[6],rgb[7],rgb[8]))
        mBinding.BootsBottomView.setColorFilter(Color.rgb(LC(rgb[6]),LC(rgb[7]),LC(rgb[8])))

        //Кубики с цветом
        mBinding.CollorViewTop.setBackgroundColor(Color.rgb(rgb[0],rgb[1],rgb[2]))
        mBinding.CollorViewMiddle.setBackgroundColor(Color.rgb(rgb[3],rgb[4],rgb[5]))
        mBinding.CollorViewBottom.setBackgroundColor(Color.rgb(rgb[6],rgb[7],rgb[8]))

        mBinding.swapButton.setOnClickListener(){
            var mem_r = rgb[0]
            var mem_g = rgb[1]
            var mem_b = rgb[2]
            rgb[0] = rgb[3]
            rgb[1] = rgb[4]
            rgb[2] = rgb[5]
            rgb[3] = rgb[6]
            rgb[4] = rgb[7]
            rgb[5] = rgb[8]
            rgb[6] = mem_r
            rgb[7] = mem_g
            rgb[8] = mem_b
            //Футболка
            mBinding.ShirtView.setColorFilter(Color.rgb(rgb[0],rgb[1],rgb[2]))
            mBinding.ShirtBandView.setColorFilter(Color.rgb(LC(rgb[0]),LC(rgb[1]),LC(rgb[2])))

            //Штаны
            mBinding.PantsView.setColorFilter(Color.rgb(rgb[3],rgb[4],rgb[5]))
            mBinding.PantsBandView.setColorFilter(Color.rgb(LC(rgb[3]),LC(rgb[4]),LC(rgb[5])))

            //Ботинки
            mBinding.BootsTopView.setColorFilter(Color.rgb(rgb[6],rgb[7],rgb[8]))
            mBinding.BootsBottomView.setColorFilter(Color.rgb(LC(rgb[6]),LC(rgb[7]),LC(rgb[8])))

            //Кубики с цветом
            mBinding.CollorViewTop.setBackgroundColor(Color.rgb(rgb[0],rgb[1],rgb[2]))
            mBinding.CollorViewMiddle.setBackgroundColor(Color.rgb(rgb[3],rgb[4],rgb[5]))
            mBinding.CollorViewBottom.setBackgroundColor(Color.rgb(rgb[6],rgb[7],rgb[8]))
        }
    }

    private fun fromRGBtoHUE(_r:Int,_g:Int,_b:Int) : Double {
        val r = _r/255
        val g = _g/255
        val b = _b/255
        val maximum = max(r,max(g,b))
        val minimum = min(r,min(g,b))
        if (maximum == r){
            return 60.0*(g-b)/(maximum-minimum)
        }
        else if(maximum == g){
            return (2.0 + (b-r)/(maximum-minimum))*60
        }
        else{
            return (4.0 + (r-g)/(maximum-minimum))*60
        }
    }


    fun LC(a:Int):Int{
        if (a > 30) return a-30
        return a
    }/*
    private fun nearCollors(color1: Color, color2: Color) : Boolean{
        val r:=
    }*/
}

