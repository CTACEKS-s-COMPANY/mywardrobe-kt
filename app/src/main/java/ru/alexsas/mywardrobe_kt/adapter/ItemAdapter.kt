package ru.alexsas.mywardrobe_kt.adapter

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.Query
import ru.alexsas.mywardrobe_kt.databinding.ItemClothesBinding
import ru.alexsas.mywardrobe_kt.model.Item


abstract class ItemAdapter(query: Query) :
        FirestoreAdapter<ItemAdapter.ViewHolder>(query) {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(ItemClothesBinding.inflate(
            LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        getSnapshot(position).let { snapshot -> holder.bind(snapshot) }
    }


    class ViewHolder(val binding: ItemClothesBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(snapshot: DocumentSnapshot) {
            val item: Item? = snapshot.toObject(Item::class.java)
            binding.clothesItemType.text = item?.type
//            Log.d("BBB", item?.color.toString() + "\t" + Integer.toHexString(item!!.color))
            binding.clothesItemImage.setColorFilter(Color.parseColor("#"+Integer.toHexString(item!!.color)))
        }
    }
}

