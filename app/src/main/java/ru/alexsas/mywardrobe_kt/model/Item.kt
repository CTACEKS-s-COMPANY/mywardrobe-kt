package ru.alexsas.mywardrobe_kt.model

import com.google.firebase.firestore.IgnoreExtraProperties

@IgnoreExtraProperties
data class Item (
//    var userId: String? = null,
    var color: Int = 0,
    var type: String? = null,
//    var photo: String? = null
)


