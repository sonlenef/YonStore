package tech.leson.yonstore.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import tech.leson.yonstore.R
import tech.leson.yonstore.data.model.Category
import tech.leson.yonstore.ui.adapter.viewholder.CategoryHorizontalViewHolder
import tech.leson.yonstore.ui.adapter.viewholder.CategoryViewHolder
import tech.leson.yonstore.ui.base.BaseAdapter
import tech.leson.yonstore.ui.base.BaseViewHolder
import tech.leson.yonstore.ui.main.home.HomeNavigator
import tech.leson.yonstore.utils.OnCategoryClickListener
import tech.leson.yonstore.utils.OnItemClickListener

class CategoryAdapter(data: MutableList<Category>, viewType: Int) :
    BaseAdapter<BaseViewHolder<Category>, Category>(data) {

    private val layoutViewType = viewType
    lateinit var listener: OnCategoryClickListener

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

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<Category> {
        return when (viewType) {
            LAYOUT_VIEW_TYPE_HOME, LAYOUT_VIEW_TYPE_EXPLORE -> {
                CategoryHorizontalViewHolder(LayoutInflater.from(parent.context).inflate(
                    R.layout.item_category_home, parent, false), listener)
            }
            else -> CategoryViewHolder(LayoutInflater.from(parent.context).inflate(
                R.layout.item_category, parent, false))
        }
    }

    override fun onBindViewHolder(holder: BaseViewHolder<Category>, position: Int) {
        holder.onBind(data[position])
    }

    override fun getItemViewType(position: Int) = layoutViewType

    override fun getItemCount(): Int = data.size

    companion object {
        const val LAYOUT_VIEW_TYPE_CATEGORY = 0
        const val LAYOUT_VIEW_TYPE_HOME = 1
        const val LAYOUT_VIEW_TYPE_EXPLORE = 2
    }
}
