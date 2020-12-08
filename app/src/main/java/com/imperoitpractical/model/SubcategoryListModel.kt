package com.imperoitpractical.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName


class SubcategoryListModel {
    @SerializedName("Status")
    @Expose
    var status = 0

    @SerializedName("Message")
    @Expose
    var message: String? = null

    @SerializedName("Result")
    @Expose
    var result: Result? = null

    class SubCategory {
        @SerializedName("Id")
        @Expose
        var id = 0

        @SerializedName("Name")
        @Expose
        var name: String? = null

        @SerializedName("Product")
        @Expose
        var product: List<Product>? = null
    }

    class Result {
        @SerializedName("Category")
        @Expose
        var category: List<Category>? = null
    }

    class Category {
        @SerializedName("Id")
        @Expose
        var id = 0

        @SerializedName("Name")
        @Expose
        var name: String? = null

        @SerializedName("IsAuthorize")
        @Expose
        var isAuthorize = 0

        @SerializedName("Update080819")
        @Expose
        var update080819 = 0

        @SerializedName("Update130919")
        @Expose
        var update130919 = 0

        @SerializedName("SubCategories")
        @Expose
        var subCategories: List<SubCategory>? = null
    }

    class Product {
        @SerializedName("Name")
        @Expose
        var name: String? = null

        @SerializedName("PriceCode")
        @Expose
        var priceCode: String? = null

        @SerializedName("ImageName")
        @Expose
        var imageName: String? = null

        @SerializedName("Id")
        @Expose
        var id = 0
    }
}