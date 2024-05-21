package com.example.moviefindefapd.data.models

data class UserData (
    val name:String?= null,
    val password: String?= null,
    val favoriteFilm:MutableList<String> = mutableListOf()
) {
    //Note: this is needed to read the data from the firebase database
    //firebase database throws this exception: UserData does not define a no-argument constructor
}
