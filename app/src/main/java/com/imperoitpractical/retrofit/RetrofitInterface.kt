package com.imperoitpractical.retrofit

import com.google.gson.JsonObject
import com.imperoitpractical.model.CategoryListModel
import com.imperoitpractical.model.ProductsModel
import com.imperoitpractical.model.SubcategoryListModel
import org.json.JSONObject
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST


interface RetrofitInterface {

    // for fetching categories and subcategories

    @POST("DashBoard")
    fun getCategoryList(
        @Body data : JsonObject
    ) : Call<CategoryListModel>

    @POST("DashBoard")
    fun getSubCategoryList(
        @Body data : JsonObject
    ) : Call<SubcategoryListModel>

    @POST("ProductList")
    fun getProductList(
        @Body data : JsonObject
    ) : Call<ProductsModel>

}