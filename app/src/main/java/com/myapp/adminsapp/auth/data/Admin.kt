package com.myapp.adminsapp.auth.data
data class SignInResult(
    val data: Admin?,
    val errorMessage: String?
)
data class Admin(
   val phone : String ? = "",
    val name : String ? = "",
    val Address : String? = "",
    val AdminId : String? ="",
    val profilePicture : String ? = ""
)
