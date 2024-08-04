package com.myapp.adminsapp.categories.data
data class RealtimeCategory(
    val item : Category1? ,
    val key : String ? = ""
){
    data class Category1(
        val CategoryType : String? ="" ,
        val CategoryName : String? ="",
        val image : String?=""

    )

}

data class Category(
    val CategoryType : String ,
    val CategoryName : String ,
    val image : String

)

