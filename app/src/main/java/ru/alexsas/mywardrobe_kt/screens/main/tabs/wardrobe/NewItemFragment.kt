package ru.alexsas.mywardrobe_kt.screens.main.tabs.wardrobe

import android.app.Activity
import android.content.DialogInterface
import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import android.widget.Toast
import androidx.annotation.ColorInt
import androidx.appcompat.widget.AppCompatButton
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.github.dhaval2404.colorpicker.ColorPickerDialog
import com.github.dhaval2404.colorpicker.model.ColorShape
import com.github.dhaval2404.colorpicker.util.ColorUtil
import com.github.dhaval2404.colorpicker.util.SharedPref
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import ru.alexsas.mywardrobe_kt.R
import ru.alexsas.mywardrobe_kt.databinding.FragmentAdditemBinding
import ru.alexsas.mywardrobe_kt.model.Clothes


class NewItemFragment : Fragment(R.layout.fragment_additem) {


    private lateinit var binding: FragmentAdditemBinding
    private lateinit var firestore: FirebaseFirestore
    private lateinit var clothes: Clothes
    private lateinit var auth: FirebaseAuth
    private var mColor = 0
    private val taskData = HashMap<String, Any>()


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAdditemBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        // Initialize Firestore
        firestore = Firebase.firestore
        auth = FirebaseAuth.getInstance();
        binding.radioGroup.clearCheck();
        binding.radioGroup.setOnCheckedChangeListener { _, checkedId ->
            // get the radio group checked radio button
            binding.radioGroup.findViewById<RadioButton>(checkedId)?.apply {
                // show the checked radio button's text in text view
                taskData["type"] = text.toString()
                binding.radioGroup.visibility = View.GONE
                binding.paletteButton.visibility = View.VISIBLE
                binding.textView.text = getString(R.string.choose_the_color)
            }
        }


        val primaryColor = ContextCompat.getColor(requireContext(), R.color.colorPrimary)
        mColor = SharedPref(requireContext()).getRecentColor(primaryColor)
        binding.paletteButton.setOnClickListener { _ ->
            ColorPickerDialog
                .Builder(requireActivity()) // Pass Activity Instance
                .setColorShape(ColorShape.SQAURE) // Or ColorShape.CIRCLE
                .setDefaultColor(mColor) // Pass Default Color
                .setColorListener { color, _ ->
                    mColor = color
                    Log.d("TTT", color.toString())
                    taskData["color"] = color
                    binding.paletteButton.visibility = View.GONE
                    binding.textView.visibility = View.GONE
                }
                .setDismissListener {
                    Log.d("ColorPickerDialog", "Handle dismiss event")
                }
                .show()
        }

        binding.sendButton.setOnClickListener {
            addItem()
            findNavController().popBackStack();
        }
    }


    private fun addItem() {
        auth.currentUser?.let {
            firestore.collection("users").document(it.uid).collection("clothes").add(taskData)
                .addOnSuccessListener {
                    Log.i("FireStoreItem", "Successfully added item")
                }
                .addOnFailureListener {
                    Log.i("FireStoreItem", "Bad news, item wasn't be saved (")
                }
        }
    }


    companion object {

        private const val TAG = "RestaurantDetail"

        const val KEY_RESTAURANT_ID = "key_restaurant_id"
    }


}
