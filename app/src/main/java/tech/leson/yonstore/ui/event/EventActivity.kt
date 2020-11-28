package tech.leson.yonstore.ui.event

import android.content.Context
import android.content.Intent
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_event.*
import kotlinx.android.synthetic.main.navigation_header.*
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel
import tech.leson.yonstore.BR
import tech.leson.yonstore.R
import tech.leson.yonstore.data.model.Event
import tech.leson.yonstore.databinding.ActivityEventBinding
import tech.leson.yonstore.ui.adapter.EventAdapter
import tech.leson.yonstore.ui.addevent.AddEventActivity
import tech.leson.yonstore.ui.base.BaseActivity

class EventActivity : BaseActivity<ActivityEventBinding, EventNavigator, EventViewModel>(),
    EventNavigator {

    companion object {
        private var instance: Intent? = null

        @JvmStatic
        fun getIntent(context: Context) = instance ?: synchronized(this) {
            instance ?: Intent(context, EventActivity::class.java).also { instance = it }
        }
    }

    private val mEventAdapter: EventAdapter by inject()

    override val bindingVariable: Int
        get() = BR.viewModel
    override val layoutId: Int
        get() = R.layout.activity_event
    override val viewModel: EventViewModel by viewModel()

    override fun init() {
        viewModel.setNavigator(this)
        tvTitle.text = getString(R.string.event)
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
