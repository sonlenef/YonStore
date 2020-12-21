package tech.leson.yonstore.ui.eventmanager

import tech.leson.yonstore.data.model.Event

interface EventManagerNavigator {
    fun setEvent(events: MutableList<Event>)
    fun onCreateEvent()
    fun onError(msg: String)
    fun onBack()
}
