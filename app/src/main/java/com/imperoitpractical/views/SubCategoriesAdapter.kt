package com.imperoitpractical.views

import android.content.Context
import android.util.Log
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.imperoitpractical.databinding.ItemSubcategoriesBinding
import com.imperoitpractical.model.ProductsModel
import com.imperoitpractical.model.SubcategoryListModel
import com.imperoitpractical.retrofit.RetrofitClass
import com.imperoitpractical.utils.Constants
import com.imperoitpractical.utils.layoutInflater
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SubCategoriesAdapter(
    private val context: Context,
    private val categoriesList: ArrayList<SubcategoryListModel.SubCategory>
) : RecyclerView.Adapter<SubCategoriesAdapter.Item>() {

    private val TAG = "SubCategoriesAdapter"

    private var productsAdapter: ProductsAdapter? = null
    private var productPageIndex = 1
    var pastVisiblesItems = 0
    var visibleItemCount: Int = 0
    var totalItemCount: Int = 0
    var loading = false

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): Item = Item(ItemSubcategoriesBinding.inflate(parent.context.layoutInflater()))

    override fun getItemCount(): Int {
        return categoriesList.size
    }

    override fun onBindViewHolder(holder: SubCategoriesAdapter.Item, position: Int) {


        val manager = LinearLayoutManager(context)
        manager.orientation = LinearLayoutManager.HORIZONTAL

        holder.binding.tvSubcategoryName.text = categoriesList[position].name

        val productList: ArrayList<ProductsModel.Result> = ArrayList()

        getProductData(holder, categoriesList[holder.adapterPosition].id, manager,productList)

        holder.binding.rvProducts.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                if (dy > 0) {
                    visibleItemCount = manager.childCount
                    totalItemCount = manager.itemCount
                    pastVisiblesItems = manager.findFirstVisibleItemPosition()

                    if (loading) {
                        if (visibleItemCount + pastVisiblesItems >= totalItemCount) {

                            loading = false
                            productPageIndex += 1

//                            if (viewModel.hasMore) {
                            android.os.Handler()
                                .postDelayed(
                                    { getProductData(
                                        holder,
                                        categoriesList[holder.adapterPosition].id,
                                        manager,
                                        productList
                                    ) },
                                    1500
                                )
//                            }
                        }
                    }
                }
            }
        })

        initProductList(holder, productList, manager)
    }

    private fun initProductList(
        holder: SubCategoriesAdapter.Item,
        products: List<ProductsModel.Result>?,
        manager : LinearLayoutManager
    ) {

        if (productPageIndex == 1) {
            if (products!!.isNotEmpty())
                productsAdapter = ProductsAdapter(
                    context,
                    products as ArrayList<ProductsModel.Result>
                )

            holder.binding.rvProducts.layoutManager = manager
            holder.binding.rvProducts.adapter = productsAdapter
        } else
            productsAdapter!!.notifyDataSetChanged()
    }

    class Item(val binding: ItemSubcategoriesBinding) : RecyclerView.ViewHolder(binding.root)

    fun getProductData(
        holder: Item,
        subcat: Int,
        manager: LinearLayoutManager,
        productList: ArrayList<ProductsModel.Result>
    ) {

        val categoryCall = RetrofitClass.getClient.getProductList(
            Constants.productCallJson(
                subcat,
                productPageIndex
            )
        )

        categoryCall.enqueue(object : Callback<ProductsModel> {
            override fun onResponse(
                call: Call<ProductsModel>,
                response: Response<ProductsModel>
            ) {
                loading = true
                Log.e(TAG, "onResponse: ${response.body()}")

//                if (productList.size > 0) {
                    productList.addAll(response.body()!!.result!!)
//                } else {
//                    productList =
//                        ((response.body()!!.result as ArrayList<ProductsModel.Result>?)!!)
//                }

                initProductList(holder,productList, manager)

            }

            override fun onFailure(call: Call<ProductsModel>, t: Throwable) {
                loading = true
                Log.e(TAG, "onResponse: ${t.toString()}")
            }
        })
    }

}