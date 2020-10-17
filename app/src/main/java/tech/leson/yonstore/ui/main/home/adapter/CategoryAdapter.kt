package tech.leson.yonstore.ui.main.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import tech.leson.yonstore.R
import tech.leson.yonstore.ui.base.BaseAdapter
import tech.leson.yonstore.ui.base.BaseViewHolder
import tech.leson.yonstore.ui.main.home.model.Category
import tech.leson.yonstore.ui.main.home.viewholder.CategoryHorizontalViewHolder
import tech.leson.yonstore.ui.main.home.viewholder.CategoryVerticalViewHolder

class CategoryAdapter(data: MutableList<Category>, viewType: Int) :
    BaseAdapter<BaseViewHolder<Category>, Category>(data) {

    private val layoutViewType = viewType

    override fun addData(data: Category) {
        this.data.add(data)
        notifyItemChanged(this.data.size - 1)
    }

    override fun addAllData(data: MutableList<Category>) {
        this.data.clear()
        this.data.addAll(data)
        notifyDataSetChanged()
    }

    override fun clearData() {
        this.data.clear()
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        if (viewType == LAYOUT_VIEW_TYPE_HOME)
            CategoryHorizontalViewHolder(LayoutInflater.from(parent.context).inflate(
                R.layout.item_category_home, parent, false))
        else CategoryVerticalViewHolder(LayoutInflater.from(parent.context).inflate(
            R.layout.item_category_explore, parent, false))

    override fun onBindViewHolder(holder: BaseViewHolder<Category>, position: Int) {
        holder.onBind(data[position])
    }

    override fun getItemViewType(position: Int) = layoutViewType

    override fun getItemCount(): Int = data.size

    companion object {
        const val LAYOUT_VIEW_TYPE_HOME = 0
        const val LAYOUT_VIEW_TYPE_EXPLORE = 1
    }
}
