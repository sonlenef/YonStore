package tech.leson.yonstore.ui.main.explore

import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.fragment_explore.*
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.qualifier.named
import tech.leson.yonstore.BR
import tech.leson.yonstore.R
import tech.leson.yonstore.data.model.Category
import tech.leson.yonstore.databinding.FragmentExploreBinding
import tech.leson.yonstore.ui.adapter.CategoryAdapter
import tech.leson.yonstore.ui.base.BaseFragment
import tech.leson.yonstore.ui.listproducts.ListProductsActivity
import tech.leson.yonstore.utils.OnCategoryClickListener

class ExploreFragment : BaseFragment<FragmentExploreBinding, ExploreNavigator, ExploreViewModel>(),
    ExploreNavigator, OnCategoryClickListener {

    companion object {
        private var instance: ExploreFragment? = null

        @JvmStatic
        fun getInstance() = instance ?: synchronized(this) {
            instance ?: ExploreFragment().also { instance = it }
        }
    }

    private val mManCateAdapter: CategoryAdapter by inject((named("explore")))
    private val mWomanCateAdapter: CategoryAdapter by inject((named("explore")))

    override val bindingVariable: Int
        get() = BR.viewModel
    override val layoutId: Int
        get() = R.layout.fragment_explore
    override val viewModel: ExploreViewModel by viewModel()

    override fun init() {
        viewModel.setNavigator(this)
        viewModel.getCategory()
    }

    override fun onManFashion(data: MutableList<Category>) {
        mManCateAdapter.listener = this
        mManCateAdapter.clearData()
        mManCateAdapter.addAllData(data)
        val layoutManager = GridLayoutManager(activity, 4, RecyclerView.VERTICAL, false)
        rcvManFashion.layoutManager = layoutManager
        rcvManFashion.adapter = mManCateAdapter
    }

    override fun onWomanFashion(data: MutableList<Category>) {
        mWomanCateAdapter.listener = this
        mWomanCateAdapter.clearData()
        mWomanCateAdapter.addAllData(data)
        val layoutManager = GridLayoutManager(activity, 4, RecyclerView.VERTICAL, false)
        rcvWomanFashion.layoutManager = layoutManager
        rcvWomanFashion.adapter = mWomanCateAdapter
    }

    override fun onError(msg: String) {
        activity?.let {
            Toast.makeText(it, msg, Toast.LENGTH_SHORT).show()
        }
    }

    override fun onClick(category: Category) {
        activity?.let {
            val intent = ListProductsActivity.getIntent(it)
            intent.putExtra("type", ListProductsActivity.CATEGORY)
            intent.putExtra(ListProductsActivity.CATEGORY, category)
            startActivity(intent)
        }
    }
}
