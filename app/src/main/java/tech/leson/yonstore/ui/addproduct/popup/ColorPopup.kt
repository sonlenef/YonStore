package tech.leson.yonstore.ui.addproduct.popup

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.widget.PopupWindow
import kotlinx.android.synthetic.main.popup_color.view.*
import tech.leson.yonstore.R
import tech.leson.yonstore.utils.OnItemClickListener

class ColorPopup(private val context: Context) : PopupWindow(context), View.OnClickListener {

    lateinit var onItemClickListener: OnItemClickListener<String>

    @SuppressLint("InflateParams")
    private fun init() {
        val view = LayoutInflater.from(context).inflate(R.layout.popup_color, null)
        view.lnWhite.setOnClickListener(this)
        view.lnYellow.setOnClickListener(this)
        view.lnOrange.setOnClickListener(this)
        view.lnRed.setOnClickListener(this)
        view.lnGreen.setOnClickListener(this)
        view.lnBlue.setOnClickListener(this)
        view.lnBlack.setOnClickListener(this)
        contentView = view
    }

    init {
        init()
    }

    override fun onClick(p0: View?) {
        when (p0?.id) {
            R.id.lnWhite -> onItemClickListener.onClick("white")
            R.id.lnYellow -> onItemClickListener.onClick("yellow")
            R.id.lnOrange -> onItemClickListener.onClick("orange")
            R.id.lnRed -> onItemClickListener.onClick("red")
            R.id.lnGreen -> onItemClickListener.onClick("green")
            R.id.lnBlue -> onItemClickListener.onClick("blue")
            R.id.lnBlack -> onItemClickListener.onClick("black")
        }
        dismiss()
    }
}