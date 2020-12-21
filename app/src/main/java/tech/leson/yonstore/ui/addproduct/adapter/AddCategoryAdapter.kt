package tech.leson.yonstore.ui.addproduct.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import tech.leson.yonstore.R
import tech.leson.yonstore.data.model.Category
import tech.leson.yonstore.ui.addproduct.dialog.addCategory.AddCategoryNavigator
import tech.leson.yonstore.ui.addproduct.viewholder.CategoryViewHolder
import tech.leson.yonstore.ui.base.BaseAdapter

class AddCategoryAdapter(data: MutableList<Category>) :
    BaseAdapter<CategoryViewHolder, Category>(data) {

    lateinit var addCategoryNavigator: AddCategoryNavigator

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
        data.clear()
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        CategoryViewHolder(LayoutInflater.from(parent.context).inflate(
            R.layout.item_add_category, parent, false))

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        holder.onBind(data[position])
        holder.itemView.setOnClickListener { addCategoryNavigator.onCategorySelected(data[position]) }
    }

    override fun getItemCount() = data.size
}
