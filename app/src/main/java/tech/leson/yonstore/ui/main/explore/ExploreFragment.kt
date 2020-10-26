package tech.leson.yonstore.ui.main.explore

import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.fragment_explore.*
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.qualifier.named
import tech.leson.yonstore.BR
import tech.leson.yonstore.R
import tech.leson.yonstore.databinding.FragmentExploreBinding
import tech.leson.yonstore.ui.base.BaseFragment
import tech.leson.yonstore.ui.main.CATEGORY
import tech.leson.yonstore.ui.main.home.adapter.CategoryAdapter
import tech.leson.yonstore.data.model.Category

class ExploreFragment : BaseFragment<FragmentExploreBinding, ExploreNavigator, ExploreViewModel>(),
    ExploreNavigator {

    companion object {
        private var instance: ExploreFragment? = null

        @JvmStatic
        fun getInstance() = instance ?: synchronized(this) {
            instance ?: ExploreFragment().also { instance = it }
        }
    }

    private val mManCateAdapter: CategoryAdapter by inject((named("manFashion")))
    private val mWomanCateAdapter: CategoryAdapter by inject((named("womanFashion")))

    override val bindingVariable: Int
        get() = BR.viewModel
    override val layoutId: Int
        get() = R.layout.fragment_explore
    override val viewModel: ExploreViewModel by viewModel()

    override fun init() {
        viewModel.setNavigator(this)
        setManFashion()
        setWomanFashion()
    }

    private fun setWomanFashion() {
        mWomanCateAdapter.clearData()
        mWomanCateAdapter.addData(Category(CATEGORY.WOMAN_DRESS,
            getString(R.string.woman_dress),
            R.drawable.ct_woman_dress))
        mWomanCateAdapter.addData(Category(CATEGORY.WOMAN_T_SHIRT,
            getString(R.string.woman_t_shirt),
            R.drawable.ct_woman_tshirt))
        mWomanCateAdapter.addData(Category(CATEGORY.WOMAN_PANTS,
            getString(R.string.woman_pants),
            R.drawable.ct_woman_pants))
        mWomanCateAdapter.addData(Category(CATEGORY.WOMAN_SKIRT,
            getString(R.string.woman_skirt),
            R.drawable.ct_woman_skirt))
        mWomanCateAdapter.addData(Category(CATEGORY.WOMAN_BAG,
            getString(R.string.woman_bag),
            R.drawable.ct_woman_bag))
        mWomanCateAdapter.addData(Category(CATEGORY.WOMAN_SHOES,
            getString(R.string.woman_shoes),
            R.drawable.ct_woman_shoes))
        mWomanCateAdapter.addData(Category(CATEGORY.WOMAN_BIKINI,
            getString(R.string.woman_bikini),
            R.drawable.ct_woman_bikini))
        val layoutManager = GridLayoutManager(activity, 4, RecyclerView.VERTICAL, false)
        rcvWomanFashion.layoutManager = layoutManager
        rcvWomanFashion.adapter = mWomanCateAdapter
    }

    private fun setManFashion() {
        mManCateAdapter.clearData()
        mManCateAdapter.addData(Category(CATEGORY.MAN_SHIRT,
            getString(R.string.man_shirt),
            R.drawable.ct_man_shirt))
        mManCateAdapter.addData(Category(CATEGORY.MAN_WORD_EQUIPMENT,
            getString(R.string.man_work_equipment),
            R.drawable.ct_man_bag))
        mManCateAdapter.addData(Category(CATEGORY.MAN_T_SHIRT,
            getString(R.string.man_t_shirt),
            R.drawable.ct_man_tshirt))
        mManCateAdapter.addData(Category(CATEGORY.MAN_SHOES,
            getString(R.string.man_shoes),
            R.drawable.ct_man_shoes))
        mManCateAdapter.addData(Category(CATEGORY.MAN_PANTS,
            getString(R.string.man_pants),
            R.drawable.ct_man_pants))
        mManCateAdapter.addData(Category(CATEGORY.MAN_UNDERWEAR,
            getString(R.string.man_underwear),
            R.drawable.ct_man_underwear))
        val layoutManager = GridLayoutManager(activity, 4, RecyclerView.VERTICAL, false)
        rcvManFashion.layoutManager = layoutManager
        rcvManFashion.adapter = mManCateAdapter
    }
}
