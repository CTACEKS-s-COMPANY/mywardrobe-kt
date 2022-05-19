package ru.alexsas.mywardrobe_kt.screens.main.tabs.wardrobe

import android.app.Activity
import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.annotation.StringRes
import androidx.appcompat.app.AlertDialog
import androidx.core.text.HtmlCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.firebase.ui.auth.AuthUI
import com.firebase.ui.auth.ErrorCodes
import com.firebase.ui.auth.FirebaseAuthUIActivityResultContract
import com.firebase.ui.auth.data.model.FirebaseAuthUIAuthenticationResult
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreException
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import ru.alexsas.mywardrobe_kt.R
import ru.alexsas.mywardrobe_kt.adapter.ClothesAdapter
import ru.alexsas.mywardrobe_kt.databinding.FragmentWardrobeBinding
import ru.alexsas.mywardrobe_kt.utils.findTopNavController

class WardrobeFragment:Fragment(R.layout.fragment_wardrobe) {

    lateinit var firestore: FirebaseFirestore
    lateinit var auth: FirebaseAuth
    lateinit var query: Query

    private lateinit var binding: FragmentWardrobeBinding
    lateinit var adapter: ClothesAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentWardrobeBinding.inflate(inflater, container, false);
        return binding.root;
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        // Enable Firestore logging
        FirebaseFirestore.setLoggingEnabled(true)

        // Firestore
        firestore = Firebase.firestore
        auth = FirebaseAuth.getInstance()

        query = firestore.collection("users")
            .document(auth.uid.toString()).collection("clothes")
        // RecyclerView

        adapter = object : ClothesAdapter(query) {
            override fun onDataChanged() {
                // Show/hide content if the query returns empty.
                if (itemCount == 0) {
                    binding.recyclerClothes.visibility = View.GONE
                    binding.viewEmpty.visibility = View.VISIBLE
                } else {
                    binding.recyclerClothes.visibility = View.VISIBLE
                    binding.viewEmpty.visibility = View.GONE
                }
            }

            override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            }

            override fun onError(e: FirebaseFirestoreException) {
                // Show a snackbar on errors
                Snackbar.make(binding.root,
                    "Error: check logs for info.", Snackbar.LENGTH_LONG).show()
            }
        }

        binding.recyclerClothes.layoutManager = LinearLayoutManager(context)
        binding.recyclerClothes.adapter = adapter
        binding.newitembutton.setOnClickListener { findTopNavController().navigate(R.id.action_tabsFragment_to_newItemFragment) }

    }

    public override fun onStart() {
        super.onStart()


        // Start listening for Firestore updates
        adapter.startListening()
    }

    public override fun onStop() {
        super.onStop()
        adapter.stopListening()
    }





    companion object {

        private const val TAG = "WardrobeFragment"

    }
}
