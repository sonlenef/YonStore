package tech.leson.yonstore.ui.addevent

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Context
import android.content.Intent
import kotlinx.android.synthetic.main.navigation_header.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import tech.leson.yonstore.BR
import tech.leson.yonstore.R
import tech.leson.yonstore.databinding.ActivityAddEventBinding
import tech.leson.yonstore.ui.base.BaseActivity
import java.util.*

class AddEventActivity :
    BaseActivity<ActivityAddEventBinding, AddEventNavigator, AddEventViewModel>(),
    AddEventNavigator {

    companion object {
        private var instance: Intent? = null

        @JvmStatic
        fun getIntent(context: Context) = instance ?: synchronized(this) {
            instance ?: Intent(context, AddEventActivity::class.java).also { instance = it }
        }
    }

    override val bindingVariable: Int
        get() = BR.viewModel
    override val layoutId: Int
        get() = R.layout.activity_add_event
    override val viewModel: AddEventViewModel by viewModel()

    override fun init() {
        viewModel.setNavigator(this)
        tvTitle.text = getString(R.string.add_event)
    }

    override fun onPickStartDate() {
        pickDateTime(0)
    }

    override fun onPickEndDate() {
        pickDateTime(1)
    }

    private fun pickDateTime(type: Int) {
        val currentDateTime = Calendar.getInstance()
        val startYear = currentDateTime.get(Calendar.YEAR)
        val startMonth = currentDateTime.get(Calendar.MONTH)
        val startDay = currentDateTime.get(Calendar.DAY_OF_MONTH)
        val startHour = currentDateTime.get(Calendar.HOUR_OF_DAY)
        val startMinute = currentDateTime.get(Calendar.MINUTE)

        DatePickerDialog(this, { _, year, month, day ->
            TimePickerDialog(this, { _, hour, minute ->
                val pickedDateTime = Calendar.getInstance()
                pickedDateTime.set(year, month, day, hour, minute)
                onPickDateTime(type, pickedDateTime)
            },
                startHour,
                startMinute,
                true).show()
        },
            startYear,
            startMonth,
            startDay
        ).show()
    }

    private fun onPickDateTime(type: Int, time: Calendar) {
        when (type) {
            0 -> {
            }
            1 -> {
            }
        }
    }

    override fun onBack() {
        finish()
    }
}
