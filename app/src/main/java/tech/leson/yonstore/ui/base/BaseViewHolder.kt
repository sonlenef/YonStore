package tech.leson.yonstore.ui.base

import android.view.View
import androidx.recyclerview.widget.RecyclerView.ViewHolder

abstract class BaseViewHolder<M>(itemView: View) : ViewHolder(itemView) {
    abstract fun onBind(data: M)
}
