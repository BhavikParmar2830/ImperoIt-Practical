package com.imperoitpractical.views

import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.imperoitpractical.R
import com.imperoitpractical.base.BaseActivity
import com.imperoitpractical.databinding.ActivityMainBinding
import com.imperoitpractical.utils.Constants
import com.imperoitpractical.viewmodels.MainActivityViewModel
import kotlin.collections.ArrayList
import kotlin.reflect.KClass

class MainActivity : BaseActivity<MainActivityViewModel, ActivityMainBinding>() {

    private val TAG = "MainActivity"

    override val modelClass: KClass<MainActivityViewModel> = MainActivityViewModel::class
    override val layoutId: Int = R.layout.activity_main

    private lateinit var manager: LinearLayoutManager
    private lateinit var managerVertical: LinearLayoutManager
    private var categoriesAdapter: CategoriesAdapter? = null
    private var subCategoriesAdapter: SubCategoriesAdapter? = null

    var pastVisiblesItems = 0
    var visibleItemCount: Int = 0
    var totalItemCount: Int = 0

    override fun initControls() {

        binding.lifecycleOwner = this
        binding.viewModel = viewModel

        manager = LinearLayoutManager(this)
        managerVertical = LinearLayoutManager(this)
        manager.orientation = LinearLayoutManager.HORIZONTAL

        addObserver()
        viewModel.getCategoryData()

        binding.rvSubcategories.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                if (dy > 0) {
                    visibleItemCount = managerVertical.childCount
                    totalItemCount = managerVertical.itemCount
                    pastVisiblesItems = managerVertical.findFirstVisibleItemPosition()

                    if (viewModel.loading.value!!) {
                        if (visibleItemCount + pastVisiblesItems >= totalItemCount) {

                            viewModel.loading.value = false
                            viewModel.subcategoryPageIndex = viewModel.subcategoryPageIndex + 1

//                            if (viewModel.hasMore) {
                            android.os.Handler()
                                .postDelayed(
                                    { viewModel.getSubCategoryData(viewModel.subcategoryPageIndex) },
                                    1500
                                )
//                            }
                        }
                    }
                }
            }
        })


    }

    private fun addObserver() {
        viewModel.categoriesList.observe(this, mObserver)
        viewModel.subCategoryListSize.observe(this, nObserver)
    }

    val mObserver = Observer<Any> {
        val categoryNameList = ArrayList<String>()

        for (i in viewModel.categoriesList.value!!.indices) {
            categoryNameList.add(viewModel.categoriesList.value!![i].name!!)
        }
        initCategoryRv(categoryNameList)
    }

    private fun initCategoryRv(categoryNameList: ArrayList<String>) {

        if (categoriesAdapter == null) {
            categoriesAdapter = CategoriesAdapter(this, categoryNameList)
        }

        binding.rvCategories.layoutManager = manager
        binding.rvCategories.adapter = categoriesAdapter
    }

    val nObserver = Observer<Any> {

        viewModel.loading.value = true
        if (viewModel.subcategoryPageIndex == 1) {
            if (viewModel.subCategoryListSize.value != 0)
                initSubcategories()
        } else
            subCategoriesAdapter!!.notifyDataSetChanged()
    }

    private fun initSubcategories() {
        if (subCategoriesAdapter == null) {
            subCategoriesAdapter = SubCategoriesAdapter(this, viewModel.subCategoryList.value!!)
        }
        binding.rvSubcategories.layoutManager = managerVertical
        binding.rvSubcategories.adapter = subCategoriesAdapter
    }

}