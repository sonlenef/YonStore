package tech.leson.yonstore.data.model

class Address (
    var address1: String,
    var address2: String,
    var city: String,
    var nation: String,
    var zipcode: String
) {
    constructor():this("", "", "", "", "")
}
