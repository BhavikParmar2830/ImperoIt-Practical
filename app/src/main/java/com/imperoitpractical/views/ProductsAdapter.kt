package com.imperoitpractical.views

import android.content.Context
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.imperoitpractical.databinding.ItemProductsBinding
import com.imperoitpractical.databinding.ItemSubcategoriesBinding
import com.imperoitpractical.model.ProductsModel
import com.imperoitpractical.model.SubcategoryListModel
import com.imperoitpractical.utils.layoutInflater

class ProductsAdapter(
    private val context: Context,
    private val productList: ArrayList<ProductsModel.Result>
) : RecyclerView.Adapter<ProductsAdapter.Item>() {

    private lateinit var manager: LinearLayoutManager

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): Item = Item(ItemProductsBinding.inflate(parent.context.layoutInflater()))

    override fun getItemCount(): Int {
        return productList.size
    }

    override fun onBindViewHolder(holder: ProductsAdapter.Item, position: Int) {

        holder.binding.tvProductName.text = productList[position].name
        holder.binding.tvProductName.isSelected = true
        holder.binding.tvProductCode.text = productList[position].priceCode
        Glide.with(context)
            .load(productList[position].imageName)
            .into(holder.binding.ivProductImage)

    }

    class Item(val binding: ItemProductsBinding) : RecyclerView.ViewHolder(binding.root)

}