package tech.leson.yonstore.ui.main.home

import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.rd.animation.type.AnimationType
import kotlinx.android.synthetic.main.fragment_home.*
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel
import tech.leson.yonstore.BR
import tech.leson.yonstore.R
import tech.leson.yonstore.databinding.FragmentHomeBinding
import tech.leson.yonstore.ui.base.BaseFragment
import tech.leson.yonstore.ui.main.home.adapter.SlideShowAdapter
import tech.leson.yonstore.ui.main.home.model.Banner

class HomeFragment :
    BaseFragment<FragmentHomeBinding, HomeNavigator, HomeViewModel>(),
    HomeNavigator {

    companion object {
        private var instance: HomeFragment? = null

        @JvmStatic
        fun getInstance() = instance ?: synchronized(this) {
            instance ?: HomeFragment().also { instance = it }
        }
    }

    private val mSlideShowAdapter: SlideShowAdapter by inject()

    override val bindingVariable: Int
        get() = BR.viewModel
    override val layoutId: Int
        get() = R.layout.fragment_home
    override val viewModel: HomeViewModel by viewModel()

    override fun init() {
        viewModel.setNavigator(this)

        setBanner()
    }

    private fun setBanner() {
        mSlideShowAdapter.addData(Banner("Hahahah"))
        mSlideShowAdapter.addData(Banner("Hahahah"))
        mSlideShowAdapter.addData(Banner("Hahahah"))
        mSlideShowAdapter.addData(Banner("Hahahah"))
        mSlideShowAdapter.addData(Banner("Hahahah"))
        pageIndicatorView.setAnimationType(AnimationType.WORM)
        slideShow.adapter = mSlideShowAdapter

        pageIndicatorView.radius = 4
        pageIndicatorView.padding = 6
        pageIndicatorView.count = mSlideShowAdapter.itemCount
        pageIndicatorView.selection = slideShow.currentItem
        slideShow.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                pageIndicatorView.selection = position
                (slideShow.getChildAt(0) as RecyclerView).overScrollMode =
                    RecyclerView.OVER_SCROLL_NEVER
            }
        })
    }
}
