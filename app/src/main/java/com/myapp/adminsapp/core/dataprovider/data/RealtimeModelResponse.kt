package com.myapp.adminsapp.core.dataprovider.data

data class RealtimeModelResponse(
    val item:Product?,
    val key:String? = ""
){
    data class Product(
        val product:String? = "",
        val description:String? = "",
        val price: String ? = "",
        val image: String? = null
    )
}