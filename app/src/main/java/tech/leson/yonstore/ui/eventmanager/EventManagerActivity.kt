package tech.leson.yonstore.ui.eventmanager

import android.content.Context
import android.content.Intent
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_event_manager.*
import kotlinx.android.synthetic.main.navigation_header.*
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel
import tech.leson.yonstore.BR
import tech.leson.yonstore.R
import tech.leson.yonstore.data.model.Event
import tech.leson.yonstore.databinding.ActivityEventManagerBinding
import tech.leson.yonstore.ui.adapter.EventAdapter
import tech.leson.yonstore.ui.addevent.AddEventActivity
import tech.leson.yonstore.ui.base.BaseActivity

class EventManagerActivity : BaseActivity<ActivityEventManagerBinding, EventManagerNavigator, EventManagerViewModel>(),
    EventManagerNavigator {

    companion object {
        private var instance: Intent? = null

        @JvmStatic
        fun getIntent(context: Context) = instance ?: synchronized(this) {
            instance ?: Intent(context, EventManagerActivity::class.java).also { instance = it }
        }
    }

    private val mEventAdapter: EventAdapter by inject()

    override val bindingVariable: Int
        get() = BR.viewModel
    override val layoutId: Int
        get() = R.layout.activity_event_manager
    override val viewModel: EventManagerViewModel by viewModel()

    override fun init() {
        viewModel.setNavigator(this)
        tvTitle.text = getString(R.string.event)
    }

    override fun onStart() {
        super.onStart()
        viewModel.getEvents()
    }

    override fun setEvent(events: MutableList<Event>) {
        mEventAdapter.addAllData(events)
        rcvEvent.layoutManager = LinearLayoutManager(this)
        rcvEvent.adapter = mEventAdapter
    }

    override fun onCreateEvent() {
        startActivity(AddEventActivity.getIntent(this))
    }

    override fun onError(msg: String) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
    }

    override fun onBack() {
        finish()
    }
}
