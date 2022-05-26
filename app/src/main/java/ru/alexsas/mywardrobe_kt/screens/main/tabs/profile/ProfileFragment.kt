package ru.alexsas.mywardrobe_kt.screens.main.tabs.profile

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import ru.alexsas.mywardrobe_kt.R
import ru.alexsas.mywardrobe_kt.databinding.FragmentProfileBinding
import ru.alexsas.mywardrobe_kt.model.Item
import ru.alexsas.mywardrobe_kt.utils.findTopNavController

class ProfileFragment : Fragment(R.layout.fragment_profile) {

    private lateinit var binding: FragmentProfileBinding

    private lateinit var auth: FirebaseAuth
    private lateinit var firestore: FirebaseFirestore
    private lateinit var userRef: DocumentReference
    private lateinit var myList: List<Item>

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentProfileBinding.bind(view)

        binding.settingsButton.setOnClickListener{findTopNavController().navigate(R.id.settingsFragment)}

        auth = Firebase.auth
        firestore = Firebase.firestore


        userRef = firestore.collection("users").document(auth.uid.toString())

        val itemsQuery = userRef.collection("clothes")

        itemsQuery.get()
            .addOnSuccessListener { querySnapshot ->
                // Успешно получили данные. Список в querySnapshot.documents
                myList = querySnapshot.toObjects(Item::class.java)
                binding.statistic.text = getString(R.string.firebase_stat ,myList.size.toString())
            }
            .addOnFailureListener { exception ->
                Log.d("BAD", "I HAD BAD NEWS")
            }
    }

    override fun onStart() {
        super.onStart()
        // Check if user is signed in (non-null) and update UI accordingly.
        val currentUser = auth.currentUser
        if(currentUser != null){
            binding.detail.text = getString(R.string.firebase_status_fmt, currentUser.displayName)
        }
    }



}