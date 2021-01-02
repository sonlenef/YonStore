package tech.leson.yonstore.data.model

import java.io.Serializable

class Address(
    var fullName: String,
    var address1: String,
    var address2: String,
    var city: String,
    var region: String,
    var nation: String,
    var zipcode: String,
    var phoneNumber: String,
) : Serializable {
    constructor() : this("", "", "", "", "", "", "", "")
}
