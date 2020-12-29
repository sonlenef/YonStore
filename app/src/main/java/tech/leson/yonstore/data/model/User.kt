package tech.leson.yonstore.data.model

import java.io.Serializable

class User(
    var id: String,
    var accountId: String,
    var fullName: String,
    var phoneNumber: String,
    var email: String,
    var gender: String,
    var dob: String,
    var address: Address,
    var role: String,
    var avatar: String,
    var favorite: MutableList<String>,
    var cart: MutableList<Cart>,
    var yonCoin: Int,
    var status: UserStatus,
) : Serializable {
    constructor() : this("",
        "",
        "",
        "",
        "",
        "",
        "",
        Address(),
        "user",
        "",
        ArrayList(),
        ArrayList(),
        0,
        UserStatus())
}
