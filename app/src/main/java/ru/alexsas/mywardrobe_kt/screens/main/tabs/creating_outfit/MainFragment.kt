package ru.alexsas.mywardrobe_kt.screens.main.tabs.creating_outfit

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.navOptions
import com.firebase.ui.auth.AuthUI
import com.google.firebase.auth.FirebaseAuth
import ru.alexsas.mywardrobe_kt.R
import ru.alexsas.mywardrobe_kt.databinding.FragmentMainBinding
import ua.cn.stu.navcomponent.tabs.utils.findTopNavController

class MainFragment() : Fragment(R.layout.fragment_main) {

    private lateinit var mBinding: FragmentMainBinding


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mBinding = FragmentMainBinding.bind(view)

        mBinding.signOutButton.setOnClickListener { view ->
//            FirebaseAuth.getInstance().signOut();
            AuthUI.getInstance().signOut(requireContext())
            //                Navigation.findNavController(view).navigate(R.id.loginFragment, null, new NavOptions.Builder().setPopUpTo(tabsFragment, true).build());
            findTopNavController().navigate(R.id.loginFragment, null, navOptions {
                popUpTo(R.id.tabsFragment) {
                    inclusive = true
                }
            })
        }

    }
}