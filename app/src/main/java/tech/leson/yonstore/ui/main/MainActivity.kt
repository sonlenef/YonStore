package tech.leson.yonstore.ui.main

import android.content.Context
import android.content.Intent
import android.view.View
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.android.synthetic.main.app_bar_main.*
import kotlinx.android.synthetic.main.content_main.*
import kotlinx.android.synthetic.main.navigation_footer_main.*
import kotlinx.android.synthetic.main.navigation_header_search.*
import kotlinx.android.synthetic.main.navigation_header_title.*
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf
import tech.leson.yonstore.BR
import tech.leson.yonstore.R
import tech.leson.yonstore.databinding.ActivityMainBinding
import tech.leson.yonstore.ui.base.BaseActivity
import tech.leson.yonstore.ui.favorite.FavoriteActivity
import tech.leson.yonstore.ui.main.account.AccountFragment
import tech.leson.yonstore.ui.main.cart.CartFragment
import tech.leson.yonstore.ui.main.explore.ExploreFragment
import tech.leson.yonstore.ui.main.home.HomeFragment
import tech.leson.yonstore.ui.main.offer.OfferFragment
import tech.leson.yonstore.utils.AppUtils

class MainActivity : BaseActivity<ActivityMainBinding, MainNavigator, MainViewModel>(),
    MainNavigator {

    private val mHomeFragment = HomeFragment.getInstance()
    private val mExploreFragment = ExploreFragment.getInstance()
    private val mCartFragment = CartFragment.getInstance()
    private val mOfferFragment = OfferFragment.getInstance()
    private val mAccountFragment = AccountFragment.getInstance()

    companion object {
        var tabCurrent = TAB.TAB_HOME
        private var instance: Intent? = null

        @JvmStatic
        fun getIntent(context: Context) = instance ?: synchronized(this) {
            instance ?: Intent(context, MainActivity::class.java).also { instance = it }
        }
    }

    private val mainTabAdapter by inject<MainTabAdapter> { parametersOf(this) }

    private var mTabs: ArrayList<Fragment> = ArrayList()

    override val bindingVariable: Int
        get() = BR.viewModel
    override val layoutId: Int
        get() = R.layout.activity_main
    override val viewModel: MainViewModel by viewModel()

    override fun init() {
        viewModel.setNavigator(this)

        mTabs.add(mHomeFragment)
        mTabs.add(mExploreFragment)
        mTabs.add(mCartFragment)
        mTabs.add(mOfferFragment)
        mTabs.add(mAccountFragment)

        mainTabAdapter.mTabs = mTabs
        viewTabs.orientation = ViewPager2.ORIENTATION_HORIZONTAL
        viewTabs.adapter = mainTabAdapter
        viewTabs.offscreenPageLimit = 4
        viewTabs.isUserInputEnabled = false

        TabLayoutMediator(tabMain, viewTabs) { tab, position ->
            when (position) {
                0 -> {
                    tab.icon = ContextCompat.getDrawable(this, R.drawable.ic_home)
                    viewTabs.currentItem = position
                }
                1 -> {
                    tab.icon = ContextCompat.getDrawable(this, R.drawable.ic_explore)
                }
                2 -> {
                    tab.icon = ContextCompat.getDrawable(this, R.drawable.ic_cart)
                }
                3 -> {
                    tab.icon = ContextCompat.getDrawable(this, R.drawable.ic_offer)
                }
                4 -> {
                    tab.icon = ContextCompat.getDrawable(this, R.drawable.ic_user)
                }
            }
        }.attach()

        tabMain.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                viewTabs.currentItem = tab!!.position
                val tabIconColor = ContextCompat.getColor(this@MainActivity, R.color.blue)
                AppUtils.setColorFilter(tab.icon!!, tabIconColor)
                when (tab.position) {
                    0 -> {
                        tabCurrent = TAB.TAB_HOME
                        onNavSearch()
                    }
                    1 -> {
                        tabCurrent = TAB.TAB_EXPLORE
                        onNavSearch()
                    }
                    2 -> {
                        tabCurrent = TAB.TAB_CART
                        onNavTitle(getString(R.string.your_cart))
                    }
                    3 -> {
                        tabCurrent = TAB.TAB_OFFER
                        onNavTitle(getString(R.string.offer))
                    }
                    4 -> {
                        tabCurrent = TAB.TAB_ACCOUNT
                        onNavTitle(getString(R.string.account))
                    }
                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
                val tabIconColor = ContextCompat.getColor(this@MainActivity, R.color.grey)
                AppUtils.setColorFilter(tab?.icon!!, tabIconColor)
            }

            override fun onTabReselected(tab: TabLayout.Tab?) {}
        })

        onNavSearch()
        tabMain.getTabAt(0)?.select()
        val tabIconColor = ContextCompat.getColor(this@MainActivity, R.color.blue)
        AppUtils.setColorFilter(tabMain.getTabAt(0)?.icon!!, tabIconColor)
        edtSearch.setOnFocusChangeListener { _, p1 -> if (p1) onSearch() }
        edtSearch.setOnClickListener { onSearch() }
    }

    override fun onSearch() {}

    override fun onFavorite() {
        startActivity(FavoriteActivity.getIntent(this))
    }

    fun onNavSearch() {
        navTitle.visibility = View.GONE
        navSearch.visibility = View.VISIBLE
    }

    fun onNavTitle(title: String) {
        navSearch.visibility = View.GONE
        navTitle.visibility = View.VISIBLE
        tvTitle.text = title
    }
}
