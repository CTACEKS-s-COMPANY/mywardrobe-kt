package ru.alexsas.mywardrobe_kt.screens.main.tabs.wardrobe

import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.RadioButton
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.github.dhaval2404.colorpicker.ColorPickerDialog
import com.github.dhaval2404.colorpicker.model.ColorShape
import com.github.dhaval2404.colorpicker.util.SharedPref
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreException
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import ru.alexsas.mywardrobe_kt.R
import ru.alexsas.mywardrobe_kt.adapter.ItemAdapter
import ru.alexsas.mywardrobe_kt.databinding.FragmentWardrobeBinding
import ru.alexsas.mywardrobe_kt.utils.findTopNavController

class WardrobeFragment:Fragment(R.layout.fragment_wardrobe) {

    lateinit var firestore: FirebaseFirestore
    lateinit var auth: FirebaseAuth
    lateinit var adapter: ItemAdapter
    private lateinit var userRef: DocumentReference
    private var mColor = 0
    private val taskData = HashMap<String, Any>()
    private var newbuttonclicked = false

    private lateinit var binding: FragmentWardrobeBinding

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

        userRef = firestore.collection("users").document(auth.uid.toString())

        val itemsQuery = userRef.collection("clothes")
        val primaryColor = ContextCompat.getColor(requireContext(), R.color.colorPrimary)
        mColor = SharedPref(requireContext()).getRecentColor(primaryColor)


        binding.radioGroup.setOnCheckedChangeListener { _, checkedId ->
            // get the radio group checked radio button
            binding.radioGroup.findViewById<RadioButton>(checkedId)?.apply {
                // show the checked radio button's text in text view
                taskData["type"] = text.toString()
                binding.radioGroup.visibility = View.GONE
                binding.paletteButton.visibility = View.VISIBLE
            }
        }

        binding.paletteButton.setOnClickListener { _ ->
            ColorPickerDialog
                .Builder(requireActivity()) // Pass Activity Instance
                .setColorShape(ColorShape.SQAURE) // Or ColorShape.CIRCLE
                .setDefaultColor(mColor) // Pass Default Color
                .setColorListener { color, _ ->
                    mColor = color
                    taskData["color"] = color
                    binding.paletteButton.visibility = View.GONE
                    binding.sendButton.visibility = View.VISIBLE
                }
                .setDismissListener {
                    Log.d("ColorPickerDialog", "Handle dismiss event")
                }
                .show()
        }

        // RecyclerView
        adapter = object : ItemAdapter(itemsQuery) {

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


            override fun onError(e: FirebaseFirestoreException) {
                // Show a snackbar on errors
                Snackbar.make(binding.root,
                    "Error: check logs for info.", Snackbar.LENGTH_LONG).show()
            }
        }

        binding.recyclerClothes.adapter = adapter

        binding.newitembutton.setOnClickListener {
            if (!newbuttonclicked) {
                binding.newitem.visibility = View.VISIBLE
                newbuttonclicked = true
            }
            else {
                binding.radioGroup.clearCheck()
                binding.newitem.visibility = View.INVISIBLE
                binding.paletteButton.visibility = View.INVISIBLE
                binding.sendButton.visibility = View.INVISIBLE
                binding.radioGroup.visibility = View.VISIBLE
                newbuttonclicked = false
                taskData.clear()
            }
        }

        binding.sendButton.setOnClickListener {
            auth.currentUser?.let {
                firestore.collection("users").document(it.uid).collection("clothes").add(taskData)
                    .addOnSuccessListener {
                        Log.i("FireStoreItem", "Successfully added item")
                    }
                    .addOnFailureListener {
                        Log.i("FireStoreItem", "Bad news, item wasn't be saved (")
                        Toast.makeText(context, "Bad news, item wasn't be saved (", Toast.LENGTH_SHORT).show()
                    }
            }

            taskData.clear()
            newbuttonclicked = false
            binding.radioGroup.clearCheck()
            binding.newitem.visibility = View.INVISIBLE
            binding.paletteButton.visibility = View.INVISIBLE
            binding.sendButton.visibility = View.INVISIBLE
            binding.radioGroup.visibility = View.VISIBLE
        }

    }


    override fun onStart() {
        super.onStart()

        // Start listening for Firestore updates
        adapter.startListening()
    }

    override fun onStop() {
        super.onStop()
        adapter.stopListening()
    }





    companion object {

        private const val TAG = "WardrobeFragment"

    }
}
