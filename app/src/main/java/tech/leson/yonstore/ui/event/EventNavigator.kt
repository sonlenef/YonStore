package tech.leson.yonstore.ui.event

import tech.leson.yonstore.data.model.Event

interface EventNavigator {
    fun setEvent(events: MutableList<Event>)
    fun onCreateEvent()
    fun onError(msg: String)
    fun onBack()
}
