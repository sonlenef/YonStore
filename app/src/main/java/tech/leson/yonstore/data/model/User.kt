package tech.leson.yonstore.data.model

import java.io.Serializable

class User(
    var accountId: String,
    var fullName: String,
    var phoneNumber: String,
    var email: String,
    var gender: String,
    var dob: String,
    var avatar: String,
    var yonCoin: Int,
    var status: UserStatus,
) : Serializable {
    constructor():this("", "", "", "", "", "", "", 0, UserStatus())
}
