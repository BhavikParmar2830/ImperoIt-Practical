package com.imperoitpractical.utils

import com.google.gson.JsonObject
import org.json.JSONObject

object Constants {

    val CERAMIC_CATEGORY_ID : Int = 56

    fun categoryCallJson() : JsonObject{

        val categoryJson = JsonObject()

        categoryJson.addProperty("CategoryId",0)
        categoryJson.addProperty("DeviceManufacturer","Google")
        categoryJson.addProperty("DeviceModel","Android SDK built for x86")
        categoryJson.addProperty("DeviceToken","")
        categoryJson.addProperty("PageIndex",1)

        return categoryJson
    }

    fun subcategoryCallJson(categoryId : Int,pageIndex : Int) : JsonObject{

        val subCatJson = JsonObject()

        subCatJson.addProperty("CategoryId",categoryId)
        subCatJson.addProperty("PageIndex",pageIndex)
        return subCatJson
    }

    fun productCallJson(subCategotyId : Int, pageIndex : Int) : JsonObject{

        val productsJson = JsonObject()

        productsJson.addProperty("PageIndex",pageIndex)
        productsJson.addProperty("SubCategoryId",subCategotyId)
        return productsJson
    }

}