package ru.alexsas.mywardrobe_kt.screens.main.tabs.creating_outfit

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.FirebaseAuth
import ru.alexsas.mywardrobe_kt.R
import ru.alexsas.mywardrobe_kt.databinding.FragmentMainBinding

class MainFragment():Fragment(R.layout.fragment_main){

    private lateinit var mBinding: FragmentMainBinding



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mBinding = FragmentMainBinding.bind(view)

        mBinding.signOutButton.setOnClickListener(View.OnClickListener { view ->
            FirebaseAuth.getInstance().signOut();
            //                Navigation.findNavController(view).navigate(R.id.loginFragment, null, new NavOptions.Builder().setPopUpTo(tabsFragment, true).build());
            findNavController().navigate(
                R.id.loginFragment,
                null
            )
        })
    }

}