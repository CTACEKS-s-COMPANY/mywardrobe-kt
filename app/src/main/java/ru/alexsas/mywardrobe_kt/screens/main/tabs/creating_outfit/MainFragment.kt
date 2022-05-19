package ru.alexsas.mywardrobe_kt.screens.main.tabs.creating_outfit

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.navOptions
import com.firebase.ui.auth.AuthUI
import ru.alexsas.mywardrobe_kt.R
import ru.alexsas.mywardrobe_kt.databinding.FragmentMainBinding
import ru.alexsas.mywardrobe_kt.utils.findTopNavController

class MainFragment() : Fragment(R.layout.fragment_main) {

    private lateinit var mBinding: FragmentMainBinding


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mBinding = FragmentMainBinding.bind(view)


    }
}