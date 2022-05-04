package ru.alexsas.mywardrobe.screens.main.tabs.creating_outfit;

import static ru.alexsas.mywardrobe.R.id.loginFragment;
import static ru.alexsas.mywardrobe.R.id.tabsFragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavOptions;
import androidx.navigation.NavOptionsBuilder;
import androidx.navigation.NavOptionsBuilderKt;
import androidx.navigation.Navigation;
import androidx.navigation.PopUpToBuilder;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.firebase.ui.auth.AuthUI;

import java.util.zip.Inflater;

import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import ru.alexsas.mywardrobe.R;
import ru.alexsas.mywardrobe.databinding.FragmentMainBinding;


public class MainFragment extends Fragment {

    FragmentMainBinding mBinding;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mBinding = FragmentMainBinding.inflate(inflater, container, false);
        return mBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        mBinding.signOutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AuthUI.getInstance().signOut(getContext());
//                Navigation.findNavController(view).navigate(R.id.loginFragment);
                Navigation.findNavController(view).navigate(R.id.loginFragment, null, new NavOptions.Builder().setPopUpTo(tabsFragment, true).build());
//                Navigation.findNavController(view).navigate(R.id.loginFragment,null, new NavOptionsBuilder().);
//                new PopUpToBuilder().setInclusive(true);)
//                NavOptions no = new NavOptions.Builder().setPopUpTo(loginFragment,true).build();
//                NavOptions na = no.build();
//                nb.popUpTo(2, (Function1<? super PopUpToBuilder, Unit>) );
//                PopUpToBuilder pb = new PopUpToBuilder();
//                pb.setInclusive(true);
//                nb.popUpTo(3, (Function1<? super PopUpToBuilder, Unit>) pb.);
            }
        });
    }
}