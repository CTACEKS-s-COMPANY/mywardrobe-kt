package ru.alexsas.mywardrobe_kt.adapter

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.collection.LLRBNode
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.ktx.toObject
import ru.alexsas.mywardrobe_kt.databinding.ItemClothesBinding
import ru.alexsas.mywardrobe_kt.model.Item


abstract class ItemAdapter(query: Query) :
        FirestoreAdapter<ItemAdapter.ViewHolder>(query) {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(ItemClothesBinding.inflate(
            LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getSnapshot(position))
    }


    class ViewHolder(val binding: ItemClothesBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(snapshot: DocumentSnapshot) {
            val item = snapshot.toObject<Item>()
            if (item==null){
                return
            }
            binding.clothesItemType.text = item.type;
            binding.clothesItemImage.setBackgroundColor(Color.parseColor("#S{item.color}"))
            //binding.clothesItemImage.setBackgroundColor(Color.parseColor("#${clothes.color}"))

        }
    }

}
