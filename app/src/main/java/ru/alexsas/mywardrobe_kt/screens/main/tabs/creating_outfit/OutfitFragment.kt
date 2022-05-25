package ru.alexsas.mywardrobe_kt.screens.main.tabs.creating_outfit

import android.graphics.Color
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import ru.alexsas.mywardrobe_kt.R
import ru.alexsas.mywardrobe_kt.databinding.FragmentOutfitBinding
import ru.alexsas.mywardrobe_kt.utils.MatchingColorUtil
import kotlin.math.max
import kotlin.math.min
import kotlin.random.Random

class OutfitFragment : Fragment(R.layout.fragment_outfit) {
    private lateinit var mBinding: FragmentOutfitBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val randMax = 180
        val randMin = 0
        var r: Int
        var g: Int
        var b: Int
        var switchOn = true

        super.onViewCreated(view, savedInstanceState)
        mBinding = FragmentOutfitBinding.bind(view)

        mBinding.Switcher.setOnCheckedChangeListener { compoundButton, isChecked ->
            switchOn = isChecked
        }

        mBinding.GenerateButton.setOnClickListener() {
            r = Random.nextInt(randMin, randMax)
            g = Random.nextInt(randMin, randMax)
            b = Random.nextInt(randMin, randMax)
            if (switchOn){
                matchTwoColorPressed(r, g, b)
            }
            else{
                matchTreeColorPressed(r, g, b)
            }
        }
    }

    private fun matchTwoColorPressed(r:Int,g:Int,b:Int){
        val Coloring = MatchingColorUtil(r,g,b)
        val rgb = Coloring.twoColors()
        //Футболка
        mBinding.ShirtView.setColorFilter(Color.rgb(rgb[0],rgb[1],rgb[2]))
        mBinding.ShirtBandView.setColorFilter(Color.rgb(minColor(rgb[0]),minColor(rgb[1]),minColor(rgb[2])))

        //Штаны
        mBinding.PantsView.setColorFilter(Color.rgb(rgb[3],rgb[4],rgb[5]))
        mBinding.PantsBandView.setColorFilter(Color.rgb(minColor(rgb[3]),minColor(rgb[4]),minColor(rgb[5])))

        //Ботинки
        mBinding.BootsTopView.clearColorFilter()
        mBinding.BootsBottomView.clearColorFilter()

        //Кубики с цветом
        mBinding.CollorViewTop.setBackgroundColor(Color.rgb(rgb[0],rgb[1],rgb[2]))
        mBinding.CollorViewMiddle.setBackgroundColor(Color.rgb(rgb[3],rgb[4],rgb[5]))
        mBinding.CollorViewBottom.setBackgroundColor(Color.rgb(255,255,255))

        mBinding.swapButton.setOnClickListener(){
            rgb[0] = rgb[3].also { rgb[3] = rgb[0] } //Сократил
            rgb[1] = rgb[4].also { rgb[4] = rgb[1] }
            rgb[2] = rgb[5].also { rgb[5] = rgb[2] }
            //Футболка
            mBinding.ShirtView.setColorFilter(Color.rgb(rgb[0],rgb[1],rgb[2]))
            mBinding.ShirtBandView.setColorFilter(Color.rgb(minColor(rgb[0]),minColor(rgb[1]),minColor(rgb[2])))

            //Штаны
            mBinding.PantsView.setColorFilter(Color.rgb(rgb[3],rgb[4],rgb[5]))
            mBinding.PantsBandView.setColorFilter(Color.rgb(minColor(rgb[3]),minColor(rgb[4]),minColor(rgb[5])))

            //Кубики с цветом
            mBinding.CollorViewTop.setBackgroundColor(Color.rgb(rgb[0],rgb[1],rgb[2]))
            mBinding.CollorViewMiddle.setBackgroundColor(Color.rgb(rgb[3],rgb[4],rgb[5]))
        }
    }

    private fun matchTreeColorPressed(r:Int,g:Int,b:Int){
        val colorUtil = MatchingColorUtil(r,g,b)
        val rgb = colorUtil.threeColors()
        //mBinding.matchColorButton2.setBackgroundColor(Color.rgb(r,g,b))
        //Футболка
        mBinding.ShirtView.setColorFilter(Color.rgb(rgb[0],rgb[1],rgb[2]))
        mBinding.ShirtBandView.setColorFilter(Color.rgb(minColor(rgb[0]),minColor(rgb[1]),minColor(rgb[2])))

        //Штаны
        mBinding.PantsView.setColorFilter(Color.rgb(rgb[3],rgb[4],rgb[5]))
        mBinding.PantsBandView.setColorFilter(Color.rgb(minColor(rgb[3]),minColor(rgb[4]),minColor(rgb[5])))

        //Ботинки
        mBinding.BootsTopView.setColorFilter(Color.rgb(rgb[6],rgb[7],rgb[8]))
        mBinding.BootsBottomView.setColorFilter(Color.rgb(minColor(rgb[6]),minColor(rgb[7]),minColor(rgb[8])))

        //Кубики с цветом
        mBinding.CollorViewTop.setBackgroundColor(Color.rgb(rgb[0],rgb[1],rgb[2]))
        mBinding.CollorViewMiddle.setBackgroundColor(Color.rgb(rgb[3],rgb[4],rgb[5]))
        mBinding.CollorViewBottom.setBackgroundColor(Color.rgb(rgb[6],rgb[7],rgb[8]))

        mBinding.swapButton.setOnClickListener(){
            rgb[0] = rgb[3].also { rgb[3] = rgb[0] } //Сократил цвета сравнивать
            rgb[1] = rgb[4].also { rgb[4] = rgb[1] }
            rgb[2] = rgb[5].also { rgb[5] = rgb[2] }
            rgb[3] = rgb[6].also { rgb[6] = rgb[3] }
            rgb[4] = rgb[7].also { rgb[7] = rgb[4] }
            rgb[5] = rgb[8].also { rgb[8] = rgb[5] }
            //Футболка
            mBinding.ShirtView.setColorFilter(Color.rgb(rgb[0],rgb[1],rgb[2]))
            mBinding.ShirtBandView.setColorFilter(Color.rgb(minColor(rgb[0]),minColor(rgb[1]),minColor(rgb[2])))

            //Штаны
            mBinding.PantsView.setColorFilter(Color.rgb(rgb[3],rgb[4],rgb[5]))
            mBinding.PantsBandView.setColorFilter(Color.rgb(minColor(rgb[3]),minColor(rgb[4]),minColor(rgb[5])))

            //Ботинки
            mBinding.BootsTopView.setColorFilter(Color.rgb(rgb[6],rgb[7],rgb[8]))
            mBinding.BootsBottomView.setColorFilter(Color.rgb(minColor(rgb[6]),minColor(rgb[7]),minColor(rgb[8])))

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
/*
    private fun nearColors(Color1: Color, Color2: Color) : Boolean{
        val r1 = Color1.red()
        val g1 = Color1.green()
        val b1 = Color1.blue()
        val r2 = Color2.red()
        val g2 = Color2.green()
        val b2 =Color2.blue()
        if ()
    }
*/
    fun minColor(a:Int):Int{ //Понижение цвета
        if (a > 30) return a-30
        return 0
    }



}

