package ru.alexsas.mywardrobe.screens.main;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.navigation.NavController;
import androidx.navigation.NavDestination;
import androidx.navigation.NavGraph;
import androidx.navigation.NavHost;
import androidx.navigation.fragment.NavHostFragment;

import android.os.Bundle;
import android.os.Parcelable;


import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;
import java.util.Set;

import kotlin.collections.SetsKt;
import kotlin.jvm.internal.Intrinsics;
import ru.alexsas.mywardrobe.R;


public class MainActivity extends AppCompatActivity {


    private NavController navcontroller;
    private Set topLevelDestinations = SetsKt.setOf(getTabsDestination(), getSignInDestination());
    FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mAuth = FirebaseAuth.getInstance();

//        FirebaseAuth mAuth = (FirebaseAuth) getIntent().getExtras().getParcelable("PPP");

        NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager()
                .findFragmentById(R.id.fragmentContainer);
        navcontroller = Objects.requireNonNull(navHostFragment).getNavController();

//        NavController navController = getRootNavController();
        prepareRootNavController(isSignedIn(), navcontroller);
//        onNavControllerActivated(navcontroller);
//        getSupportFragmentManager().registerFragmentLifecycleCallbacks(getSupportFragmentManager(),true);
    }


//    private NavController getRootNavController() {
//        NavHostFragment navHost = (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.fragmentContainer);
//        return navHost.getNavController();
//    }

    private void prepareRootNavController(Boolean isSignIn, NavController navController) {
        NavGraph graph = navController.getNavInflater().inflate(getMainNavigationGraphId());
        graph.setStartDestination(isSignIn ? this.getTabsDestination() : this.getSignInDestination());
        navController.setGraph(graph);
    }

    private Boolean isSignedIn() {
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null) {
            return true;
        } else return false;
    }

//    private void onNavControllerActivated(NavController navController) {
//        if (this.navcontroller == navController) return;
//        this.navcontroller = navController;
//    }

    @Override
    public void onBackPressed() {
        if (this.isStartDestination(navcontroller != null ? navcontroller.getCurrentDestination() : null)) {
            super.onBackPressed();
        } else {
            if (navcontroller != null) {
                navcontroller.popBackStack();
            }
        }

    }

    public boolean onSupportNavigateUp() {
        return (navcontroller != null ? navcontroller.navigateUp() : false) || super.onSupportNavigateUp();
    }


    private final boolean isStartDestination(NavDestination destination) {
        if (destination == null) return false;
        NavGraph graph = destination.getParent();
        if (graph == null) return false;
        else {
            Set startDestinations = SetsKt.plus(topLevelDestinations, graph.getStartDestinationId());
            return startDestinations.contains(destination.getId());
        }

    }


    private Integer getMainNavigationGraphId() {
        return R.navigation.main_graph;
    }

    private Integer getTabsDestination() {
        return R.id.tabsFragment;
    }

    private Integer getSignInDestination() {
        return R.id.loginFragment;
    }


}