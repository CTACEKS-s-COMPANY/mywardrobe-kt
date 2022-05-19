package ru.alexsas.mywardrobe_kt.screens.main.tabs.creating_outfit

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import ru.alexsas.mywardrobe_kt.R
import ru.alexsas.mywardrobe_kt.databinding.FragmentEditProfileBinding
import ru.alexsas.mywardrobe_kt.databinding.FragmentOutfitBinding
import ru.alexsas.mywardrobe_kt.utils.MatchingColorUtil
import kotlin.random.Random

class OutfitFragment:Fragment(R.layout.fragment_outfit) {
    private lateinit var binding: FragmentOutfitBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentOutfitBinding.bind(view)
        binding.matchColorButton.setOnClickListener { matchColorButtonPressed() }
    }

    private fun matchColorButtonPressed(){
        val r = Random.nextInt(0,255)
        val g = Random.nextInt(0,255)
        val b = Random.nextInt(0,255)
        val Coloring = MatchingColorUtil(r,g,b)
        val rgb = Coloring.ThreeColors();
        binding.matchColorButton.setBackgroundColor(Color.rgb(r,g,b))
        binding.view.setBackgroundColor(Color.rgb(rgb[0],rgb[1],rgb[2]))
        binding.view2.setBackgroundColor(Color.rgb(rgb[3],rgb[4],rgb[5]))
        binding.view3.setBackgroundColor(Color.rgb(r,g,b))
    }
}