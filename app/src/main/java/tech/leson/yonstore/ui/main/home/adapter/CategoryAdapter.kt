package tech.leson.yonstore.ui.main.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import tech.leson.yonstore.R
import tech.leson.yonstore.ui.base.BaseAdapter
import tech.leson.yonstore.ui.main.home.model.Category
import tech.leson.yonstore.ui.main.home.viewholder.CategoryViewHolder

class CategoryAdapter(data: MutableList<Category>) :
    BaseAdapter<CategoryViewHolder, Category>(data) {

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
        CategoryViewHolder(LayoutInflater.from(parent.context).inflate(
            R.layout.item_category, parent, false))

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        holder.onBind(data[position])
    }

    override fun getItemCount(): Int = data.size
}
