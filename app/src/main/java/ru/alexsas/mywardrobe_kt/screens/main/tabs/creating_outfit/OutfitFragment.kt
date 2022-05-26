package ru.alexsas.mywardrobe_kt.screens.main.tabs.creating_outfit

import android.graphics.Color
import android.os.Bundle
import android.util.Log
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
import kotlin.math.abs
import kotlin.random.Random
import ru.alexsas.mywardrobe_kt.utils.MatchingColorUtil as MatchingColorUtil1

class OutfitFragment : Fragment(R.layout.fragment_outfit) {
    private lateinit var mBinding: FragmentOutfitBinding
    private lateinit var firestore: FirebaseFirestore
    lateinit var auth: FirebaseAuth
    private lateinit var userRef: DocumentReference

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        var switchOn = true

        //Создание массивов, хранящих цвета
        var myList: List<Item>
        val shirtList: MutableList<Int> = mutableListOf()
        val pantsList: MutableList<Int> = mutableListOf()
        val bootsList: MutableList<Int> = mutableListOf()
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

        //Детект сдвига свича
        mBinding.Switcher.setOnCheckedChangeListener { compoundButton, isChecked ->
            switchOn = isChecked
        }
        mBinding.GenerateButton.setOnClickListener() {
            val itemsQuery = userRef.collection("clothes")
            itemsQuery.get().addOnSuccessListener { querySnapshot ->
                myList = querySnapshot.toObjects(Item::class.java)
                //перезапихивание предметов по 3 массивам
                var maxi = myList.size-1
                for (i in 0..maxi) {
                    Log.d("rar", "for cycle $i")
                    when (myList[i].type) {
                        "T-shirt" -> shirtList.add(myList[i].color)
                        "Pants" -> pantsList.add(myList[i].color)
                        "Boots" -> bootsList.add(myList[i].color)
                    }
                }
                pantsList.shuffle()
                bootsList.shuffle()
                //взятие случайной футболки и получение ее цвета
                val rand = Random.nextInt(0, shirtList.size - 1)
                val Hex = Integer.toString(shirtList[rand] - 16777216, 16)
                val color: Int = Color.parseColor("#$Hex")

                //Запуски функции в зависимости от свитча
                if (switchOn) {
                    matchTreeColorPressed(color, pantsList, bootsList)
                } else {
                    matchTwoColorPressed(color, pantsList)
                }
            }
        }
    }


    //Функция сочетания двух цветов
    private fun matchTwoColorPressed(color: Int,pantsList: List<Int>){
        val Coloring = MatchingColorUtil1(color)
        var rgb = Coloring.twoColors()
        var flag = false
        for (i in 0..(pantsList.size-1)){
            if (sameColors(rgb[1],pantsList[i])){
                rgb[1] = pantsList[i]
                flag = true
                break
            }
        }
        if (!flag){
            rgb = Coloring.threeColors()
            for (i in 0..(pantsList.size-1)){
                if (sameColors(rgb[1],pantsList[i])){
                    rgb[1] = pantsList[i]
                    flag = true
                    break
                }
                else if(sameColors(rgb[2],pantsList[i])){
                    rgb[1] = pantsList[i]
                    flag = true
                    break
                }
            }
        }
        if (!flag){
            rgb = Coloring.threeColors()
            for (i in 0..(pantsList.size-1)){
                if (sameColors(rgb[0],pantsList[i])){
                    rgb[1] = pantsList[i]
                    flag = true
                    break
                }
            }
        }
        if (!flag) rgb[1] = Color.rgb(255,255,255)
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
    }


    //Функция сочетания трех цветов
    private fun matchTreeColorPressed(color: Int,pantsList: List<Int>,bootsList: List<Int>){
        val Coloring = MatchingColorUtil1(color)
        var rgb = Coloring.threeColors()
        for (i in 0..(pantsList.size-1)){
            if (sameColors(rgb[1],pantsList[i]) ||
                sameColors(rgb[2],pantsList[i]) ||
                sameColors(rgb[0],pantsList[i])){
                rgb[1] = pantsList[i]
                break
            }
            else {
                rgb[1] = Color.rgb(255,255,255)
            }
        }
        for (i in 0..(bootsList.size-1)){
            if (sameColors(rgb[1],bootsList[i]) ||
                sameColors(rgb[2],bootsList[i]) ||
                sameColors(rgb[0],bootsList[i])){
                rgb[2] = bootsList[i]
                break
            }
            else {
                rgb[2] = Color.rgb(255,255,255)
            }
        }
        if (rgb[1] == Color.rgb(255,255,255) || rgb[2] == Color.rgb(255,255,255)){
            for (i in 0..(pantsList.size-1)){
                if (sameColors(rgb[1],pantsList[i]) ||
                    sameColors(rgb[0],pantsList[i])){
                    rgb[1] = pantsList[i]
                    break
                }
                else {
                    rgb[1] = Color.rgb(255,255,255)
                }
            }
            for (i in 0..(bootsList.size-1)){
                if (sameColors(rgb[1],bootsList[i])||
                    sameColors(rgb[0],bootsList[i])){
                    rgb[2] = bootsList[i]
                    break
                }
                else {
                    rgb[2] = Color.rgb(255,255,255)
                }
            }
        }

        //mBinding.matchColorButton2.setBackgroundColor(Color.rgb(r,g,b))
        //Футболка
        mBinding.ShirtView.setColorFilter(rgb[0])
        mBinding.ShirtBandView.setColorFilter(minColor(rgb[0]))
        //Штаны
        mBinding.PantsView.setColorFilter(rgb[1])
        mBinding.PantsBandView.setColorFilter(minColor(rgb[1]))

        //Ботинки
        mBinding.BootsTopView.setColorFilter(rgb[2])
        mBinding.BootsBottomView.setColorFilter(minColor(rgb[2]))

        //Кубики с цветом
        mBinding.CollorViewTop.setColorFilter(rgb[0])
        mBinding.CollorViewMiddle.setColorFilter(rgb[1])
        mBinding.CollorViewBottom.setColorFilter(rgb[2])
    }

    //Проверка на "похожесть" цвета
    private fun sameColors(Color1: Int, Color2: Int) : Boolean{
        val value = 100
        val r1 = Color1.red
        val g1 = Color1.green
        val b1 = Color1.blue
        val r2 = Color2.red
        val g2 = Color2.green
        val b2 = Color2.blue
        return (abs(r1-r2) < value && abs(g1-g2) < value && abs(b1 - b2) < value)
    }

    //Нахождение тона цвета потемнее
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


