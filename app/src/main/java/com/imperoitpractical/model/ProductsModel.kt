package com.imperoitpractical.model

import com.google.gson.annotations.Expose

import com.google.gson.annotations.SerializedName


class ProductsModel {

    @SerializedName("Status")
    @Expose
    var status = 0

    @SerializedName("Message")
    @Expose
    var message: String? = null

    @SerializedName("Result")
    @Expose
    var result: List<Result>? = null

    class Result {
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