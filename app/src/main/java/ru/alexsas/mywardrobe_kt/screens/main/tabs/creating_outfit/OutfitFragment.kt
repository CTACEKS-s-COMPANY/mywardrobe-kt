package ru.alexsas.mywardrobe_kt.screens.main.tabs.creating_outfit

import android.graphics.Color
import android.os.Bundle
import android.view.View
import androidx.core.graphics.blue
import androidx.core.graphics.green
import androidx.core.graphics.red
import androidx.fragment.app.Fragment
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import ru.alexsas.mywardrobe_kt.R
import ru.alexsas.mywardrobe_kt.databinding.FragmentOutfitBinding
import ru.alexsas.mywardrobe_kt.model.Item
import kotlin.random.Random
import ru.alexsas.mywardrobe_kt.utils.MatchingColorUtil as MatchingColorUtil1

class OutfitFragment : Fragment(R.layout.fragment_outfit) {
    private lateinit var mBinding: FragmentOutfitBinding
    private lateinit var firestore: FirebaseFirestore
    lateinit var auth: FirebaseAuth
    private lateinit var userRef: DocumentReference

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val randMax = 180
        val randMin = 0
        var r: Int
        var g: Int
        var b: Int
        var switchOn = true

        var weardrobeSize = 0

        var myList: List<Item>
        firestore = Firebase.firestore
        auth = FirebaseAuth.getInstance()
        userRef = firestore.collection("users").document(auth.uid.toString())/*
        val itemsQuery = userRef.collection("clothes")
        itemsQuery.get().addOnSuccessListener { querySnapshot ->
            myList = querySnapshot.toObjects(Item::class.java)
            color = myList[Random.nextInt(0,myList.size)].color
        }
        */

        super.onViewCreated(view, savedInstanceState)
        mBinding = FragmentOutfitBinding.bind(view)
        mBinding.Switcher.setOnCheckedChangeListener { compoundButton, isChecked ->
            switchOn = isChecked
        }
        mBinding.GenerateButton.setOnClickListener() {
            val color:Color
            val itemsQuery = userRef.collection("clothes")
            itemsQuery.get().addOnSuccessListener { querySnapshot ->
                myList = querySnapshot.toObjects(Item::class.java)

                val Hex = Integer.toString(myList[Random.nextInt(0,myList.size)].color- 16777216, 16)
                val color: Int = Color.parseColor("#$Hex")
                r = color.red
                g = color.green
                b = color.blue
                if (switchOn) {
                    matchTwoColorPressed(color)
                } else {
                    matchTreeColorPressed(color)
                }
            }
        }
    }

    private fun matchTwoColorPressed(color: Int){
        val Coloring = MatchingColorUtil1(color)
        val rgb = Coloring.twoColors()
        //Футболка
        mBinding.ShirtView.setColorFilter(rgb[0])
        mBinding.ShirtBandView.setColorFilter(minColor(rgb[0]))

        //Штаны
        mBinding.PantsView.setColorFilter(rgb[1])
        mBinding.PantsBandView.setColorFilter(minColor(rgb[1]))

        //Ботинки
        mBinding.BootsTopView.clearColorFilter()
        mBinding.BootsBottomView.clearColorFilter()

        //Кубики с цветом
        mBinding.CollorViewTop.setBackgroundColor(rgb[0])
        mBinding.CollorViewMiddle.setBackgroundColor(rgb[1])
        mBinding.CollorViewBottom.setBackgroundColor(Color.rgb(255,255,255))

        mBinding.swapButton.setOnClickListener(){
            rgb[0] = rgb[1].also { rgb[1] = rgb[0] } //Сократил
            //Футболка
            mBinding.ShirtView.setColorFilter(rgb[0])
            mBinding.ShirtBandView.setColorFilter(minColor(rgb[0]))

            //Штаны
            mBinding.PantsView.setColorFilter(rgb[1])
            mBinding.PantsBandView.setColorFilter(minColor(rgb[1]))

            //Ботинки
            mBinding.BootsTopView.clearColorFilter()
            mBinding.BootsBottomView.clearColorFilter()

            //Кубики с цветом
            mBinding.CollorViewTop.setBackgroundColor(rgb[0])
            mBinding.CollorViewMiddle.setBackgroundColor(rgb[1])
        }
    }

    private fun matchTreeColorPressed(color: Int){
        val colorUtil = MatchingColorUtil1(color)
        val rgb = colorUtil.threeColors()
        //mBinding.matchColorButton2.setBackgroundColor(Color.rgb(r,g,b))
        //Футболка
        mBinding.ShirtView.setColorFilter(rgb[0])
        mBinding.ShirtBandView.setColorFilter(minColor(rgb[0]))
        //Штаны
        mBinding.PantsView.setColorFilter(rgb[1])
        mBinding.PantsBandView.setColorFilter(minColor(rgb[1]))

        //Ботинки
        mBinding.BootsTopView.setColorFilter(rgb[3])
        mBinding.BootsBottomView.setColorFilter(minColor(rgb[3]))

        //Кубики с цветом
        mBinding.CollorViewTop.setBackgroundColor(rgb[0])
        mBinding.CollorViewMiddle.setBackgroundColor(rgb[1])
        mBinding.CollorViewBottom.setBackgroundColor(rgb[3])

        mBinding.swapButton.setOnClickListener(){
            rgb[0] = rgb[1].also { rgb[1] = rgb[0] } //Сократил цвета сравнивать
            rgb[1] = rgb[2].also { rgb[2] = rgb[3] }
            //mBinding.matchColorButton2.setBackgroundColor(Color.rgb(r,g,b))
            //Футболка
            mBinding.ShirtView.setColorFilter(rgb[0])
            mBinding.ShirtBandView.setColorFilter(minColor(rgb[0]))
            //Штаны
            mBinding.PantsView.setColorFilter(rgb[1])
            mBinding.PantsBandView.setColorFilter(minColor(rgb[1]))

            //Ботинки
            mBinding.BootsTopView.setColorFilter(rgb[3])
            mBinding.BootsBottomView.setColorFilter(minColor(rgb[3]))

            //Кубики с цветом
            mBinding.CollorViewTop.setBackgroundColor(rgb[0])
            mBinding.CollorViewMiddle.setBackgroundColor(rgb[1])
            mBinding.CollorViewBottom.setBackgroundColor(rgb[3])

        }
    }

    /*private fun fromRGBtoHUE(_r:Int,_g:Int,_b:Int) : Double {
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

    private fun nearColors(Color1: Color, Color2: Color) : Boolean{
        val r1 = Color1.red()
        val g1 = Color1.green()
        val b1 = Color1.blue()
        val r2 = Color2.red()
        val g2 = Color2.green()
        val b2 = Color2.blue()
        return (abs(r1-r2) < 30 && abs(g1-g2) < 30 && abs(b1 - b2) < 30)
    }
    */
    fun minColor(color:Int):Int{ //Понижение цвета
        var r = color.red
        var g = color.green
        var b =color.blue
        if (r > 30) r-=30
        else r = 0
        if (g > 30) g-=30
        else g = 0
        if (b > 30) b-=30
        else b = 0
        return Color.rgb(r,g,b)
    }



}

