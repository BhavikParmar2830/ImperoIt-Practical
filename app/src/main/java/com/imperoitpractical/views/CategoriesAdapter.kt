package com.imperoitpractical.views

import android.content.Context
import android.util.TypedValue
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.imperoitpractical.R
import com.imperoitpractical.databinding.ItemCategoriesBinding
import com.imperoitpractical.utils.layoutInflater

class CategoriesAdapter(
    private val context: Context,
    private val categoriesList: ArrayList<String>
) : RecyclerView.Adapter<CategoriesAdapter.Item>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): Item = Item(ItemCategoriesBinding.inflate(parent.context.layoutInflater()))

    override fun getItemCount(): Int {
        return categoriesList.size
    }

    override fun onBindViewHolder(holder: CategoriesAdapter.Item, position: Int) {

        if (categoriesList[position] == "Ceramic"){
            holder.binding.tvCategoryName.setTextSize(TypedValue.COMPLEX_UNIT_DIP,16f)
            holder.binding.tvCategoryName.setTextColor(context.resources.getColor(R.color.white))
        }
        holder.binding.tvCategoryName.text = categoriesList[position]
    }

    class Item(val binding: ItemCategoriesBinding) : RecyclerView.ViewHolder(binding.root)

}