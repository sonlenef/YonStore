package tech.leson.yonstore.data.model

class Event(
    var title: String,
    var startTime: String,
    var endTime: String,
    var description: String,
    var discount: Double
) {
    constructor():this("", "", "", "", 0.0)
}
