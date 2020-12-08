package com.imperoitpractical.viewmodels

import android.app.Application
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.imperoitpractical.base.BaseViewModel
import com.imperoitpractical.model.CategoryListModel
import com.imperoitpractical.model.SubcategoryListModel
import com.imperoitpractical.retrofit.RetrofitClass
import com.imperoitpractical.utils.Constants
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivityViewModel(application: Application) : BaseViewModel(application) {

    private val TAG = "MainActivityViewModel"

    var categoriesList : MutableLiveData<ArrayList<CategoryListModel.Category>> = MutableLiveData()
    var subCategoryList : MutableLiveData<ArrayList<SubcategoryListModel.SubCategory>> = MutableLiveData<ArrayList<SubcategoryListModel.SubCategory>>()
    var subCategoryListSize : MutableLiveData<Int> = MutableLiveData(0)
    var progressShow : MutableLiveData<Boolean> = MutableLiveData(false)
    var loading: MutableLiveData<Boolean> = MutableLiveData(true)

    var subcategoryPageIndex : Int = 1

    fun getCategoryData() {

        progressShow.value = true

        val categoryCall = RetrofitClass.getClient.getCategoryList(Constants.categoryCallJson())

        categoryCall.enqueue(object : Callback<CategoryListModel> {
            override fun onResponse(
                call: Call<CategoryListModel>,
                response: Response<CategoryListModel>
            ) {
                Log.e(TAG, "onResponse: ${response.body()}")
                progressShow.value = false
                if (response.isSuccessful) {

                    categoriesList.value =
                        (response.body()!!.result!!.category as ArrayList<CategoryListModel.Category>?)!!

                    getSubCategoryData(subcategoryPageIndex)
                }
            }

            override fun onFailure(call: Call<CategoryListModel>, t: Throwable) {
                progressShow.value = false
                Log.e(TAG, "onResponse: ${t.toString()}")
            }
        })
    }

    fun getSubCategoryData(pageIndex : Int){

        progressShow.value = true

        val subCategoryCall = RetrofitClass.getClient.getSubCategoryList(Constants.subcategoryCallJson(56,pageIndex))

        subCategoryCall.enqueue(object : Callback<SubcategoryListModel> {
            override fun onResponse(
                call: Call<SubcategoryListModel>,
                response: Response<SubcategoryListModel>
            ) {
                Log.e(TAG, "onResponse: ${response.body()}")
                progressShow.value = false

                if (response.isSuccessful) {
                    if (subCategoryList.value != null) {
                        if (subCategoryList.value!!.size > 0) {
                            subCategoryList.value!!.addAll(response.body()!!.result!!.category!![0].subCategories as ArrayList<SubcategoryListModel.SubCategory>)
                        } else {
                            subCategoryList.value =
                                (response.body()!!.result!!.category!![0].subCategories as ArrayList<SubcategoryListModel.SubCategory>)
                        }
                    } else {
                        subCategoryList.value =
                            (response.body()!!.result!!.category!![0].subCategories as ArrayList<SubcategoryListModel.SubCategory>)
                    }

                    subCategoryListSize.value = subCategoryList.value!!.size
                }

            }

            override fun onFailure(call: Call<SubcategoryListModel>, t: Throwable) {
                progressShow.value = false
                Log.e(TAG, "onResponse: ${t.toString()}")
            }
        })

    }

}