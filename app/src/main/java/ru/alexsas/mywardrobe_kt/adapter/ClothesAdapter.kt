package ru.alexsas.mywardrobe_kt.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.ktx.toObject
import ru.alexsas.mywardrobe_kt.databinding.ItemClothesBinding
import ru.alexsas.mywardrobe_kt.screens.main.tabs.wardrobe.WardrobeFragment
import ru.alexsas.mywardrobe_kt.model.Clothes

/**
 * RecyclerView adapter for a list of Restaurants.
 */
abstract class ClothesAdapter(query: Query) :
        FirestoreAdapter<ClothesAdapter.ViewHolder>(query) {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(ItemClothesBinding.inflate(
                LayoutInflater.from(parent.context), parent, false))
    }


    class ViewHolder(val binding: ItemClothesBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(
            snapshot: DocumentSnapshot,
        ) {

            val clothes = snapshot.toObject<Clothes>()
            if (clothes == null) {
                return
            }

//            clothes.color

            // Load image
            Glide.with(binding.clothesItemType.context)
                    .load(clothes.photo)
                    .into(binding.ClothesItemImage)


            binding.clothesItemType.text = clothes.type

        }
    }

}
