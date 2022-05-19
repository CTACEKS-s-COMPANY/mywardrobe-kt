package ru.alexsas.mywardrobe_kt.screens.main.tabs.profile

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.navOptions
import com.firebase.ui.auth.AuthUI
import ru.alexsas.mywardrobe_kt.R
import ru.alexsas.mywardrobe_kt.databinding.FragmentSettingsBinding
import ru.alexsas.mywardrobe_kt.utils.findTopNavController

class SettingsFragment : Fragment(R.layout.fragment_settings) {

    private lateinit var mBinding: FragmentSettingsBinding


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mBinding = FragmentSettingsBinding.bind(view)

        mBinding.signOutButton.setOnClickListener { view ->
            AuthUI.getInstance().signOut(requireContext())
            findTopNavController().navigate(R.id.loginFragment, null, navOptions {
                popUpTo(R.id.tabsFragment) {
                    inclusive = true
                }
            })
        }

    }

}