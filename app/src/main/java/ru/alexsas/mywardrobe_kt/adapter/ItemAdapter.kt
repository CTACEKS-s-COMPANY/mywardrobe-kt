package ru.alexsas.mywardrobe_kt.adapter

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.Query
import ru.alexsas.mywardrobe_kt.R
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
            when (binding.clothesItemType.text.toString()){
                "Pants" -> {
                    binding.clothesItemImage.setImageResource(R.drawable.ic_pants_all)
                    binding.clothesItemImageFill.visibility = View.INVISIBLE
                    binding.clothesItemImage.setColorFilter(Color.parseColor("#"+Integer.toHexString(item!!.color)))
                }
                "Boots" ->{
                    binding.clothesItemImage.setImageResource(R.drawable.ic_boots_all)
                    binding.clothesItemImage.setColorFilter(Color.parseColor("#"+Integer.toHexString(item!!.color)))
                    binding.clothesItemImageFill.visibility = View.INVISIBLE

                }
                "T-shirt" -> {
                    binding.clothesItemImage.setImageResource(R.drawable.ic_tshirt_all)
                    binding.clothesItemImageFill.visibility = View.INVISIBLE
                    binding.clothesItemImage.setColorFilter(Color.parseColor("#"+Integer.toHexString(item!!.color)))
                }
//                else -> binding.clothesItemImage.setImageResource(R.drawable.ic_sad)
            }
//            Log.d("BBB", item?.color.toString() + "\t" + Integer.toHexString(item!!.color))
//            binding.clothesItemImage.setColorFilter(Color.parseColor("#"+Integer.toHexString(item!!.color)))
        }
    }
}

