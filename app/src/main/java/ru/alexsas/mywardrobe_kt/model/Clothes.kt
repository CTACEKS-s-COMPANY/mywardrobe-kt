package ru.alexsas.mywardrobe_kt.model

import android.text.TextUtils
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.ServerTimestamp
import java.util.*

data class Clothes (
//    var userId: String? = null,
    var color: Int = 0,
    var type: String? = null,
//    var photo: String? = null
) {

//    constructor(user: FirebaseUser, color: String, type: String, photo: String) : this() {
//        this.userId = user.uid
//        this.color = color
//        this.type = type
//        this.photo = photo
//    }
}