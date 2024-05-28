package com.myapp.adminsapp.allProducts.domain

data class DomainProduct(
    val item: Product?,
    val key:String? = "") {
    data class Product(
        val product:String? = "",
        val price: Int? = null,
        val productQuantity: Int? = null,
        val productUnit: String? = null,
        val productStock: Int ? = null,
        val productCategory: String? = "",
        val productType: String ? = "",
        val ItemCount: Int? = null,
        val productImageUris: List<String>? = emptyList()

    )
}
