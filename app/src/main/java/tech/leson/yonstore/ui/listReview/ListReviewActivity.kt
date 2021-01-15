package tech.leson.yonstore.ui.listReview

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.widget.Toast
import androidx.core.content.res.ResourcesCompat
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_list_review.*
import kotlinx.android.synthetic.main.navigation_header.*
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel
import tech.leson.yonstore.BR
import tech.leson.yonstore.R
import tech.leson.yonstore.data.model.Review
import tech.leson.yonstore.databinding.ActivityListReviewBinding
import tech.leson.yonstore.ui.adapter.ReviewAdapter
import tech.leson.yonstore.ui.base.BaseActivity
import tech.leson.yonstore.ui.review.ReviewActivity

class ListReviewActivity :
    BaseActivity<ActivityListReviewBinding, ListReviewNavigator, ListReviewViewModel>(),
    ListReviewNavigator {

    companion object {
        private var instance: Intent? = null

        @JvmStatic
        fun getIntent(context: Context) = instance ?: synchronized(this) {
            instance ?: Intent(context, ListReviewActivity::class.java).also { instance = it }
        }
    }

    private val mReviewAdapter: ReviewAdapter by inject()

    override val bindingVariable: Int
        get() = BR.viewModel
    override val layoutId: Int
        get() = R.layout.activity_list_review
    override val viewModel: ListReviewViewModel by viewModel()

    override fun init() {
        viewModel.setNavigator(this)
        onBtnSelect(0)

        rcvReview.layoutManager = LinearLayoutManager(this)
        rcvReview.adapter = mReviewAdapter
    }

    override fun onStart() {
        super.onStart()

        intent.getStringExtra("productId")?.let {
            viewModel.getReviews(it)
        }
    }

    override fun getAllReviews() {
        onBtnSelect(0)
    }

    override fun getReviewOneStart() {
        onBtnSelect(1)
    }

    override fun getReviewTwoStart() {
        onBtnSelect(2)
    }

    override fun getReviewThreeStart() {
        onBtnSelect(3)
    }

    override fun getReviewFourStart() {
        onBtnSelect(4)
    }

    override fun getReviewFiveStart() {
        onBtnSelect(5)
    }

    @SuppressLint("SetTextI18n")
    override fun setReviews(reviews: MutableList<Review>) {
        tvTitle.text = "${reviews.size} ${getString(R.string.review)}"
        mReviewAdapter.addAllData(reviews)
    }

    override fun onWriteReview() {
        intent.getStringExtra("userId")?.let { userId ->
            intent.getStringExtra("productId")?.let { productId ->
                val i = ReviewActivity.getIntent(this)
                i.putExtra("userId", userId)
                i.putExtra("productId", productId)
                startActivity(i)
            }
        }
    }

    override fun onMsg(msg: String) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
    }

    override fun onBack() {
        finish()
    }

    private fun onBtnSelect(btn: Int) {
        when (btn) {
            0 -> {
                btnAllReview.isSelected = true
                btnAllReview.setTextColor(ResourcesCompat.getColor(resources, R.color.blue, null))
                btnOneStart.isSelected = false
                btnOneStart.setTextColor(ResourcesCompat.getColor(resources, R.color.grey, null))
                btnTwoStart.isSelected = false
                btnTwoStart.setTextColor(ResourcesCompat.getColor(resources, R.color.grey, null))
                btnThreeStart.isSelected = false
                btnThreeStart.setTextColor(ResourcesCompat.getColor(resources, R.color.grey, null))
                btnFourStart.isSelected = false
                btnFourStart.setTextColor(ResourcesCompat.getColor(resources, R.color.grey, null))
                btnFiveStart.isSelected = false
                btnFiveStart.setTextColor(ResourcesCompat.getColor(resources, R.color.grey, null))
            }
            1 -> {
                btnAllReview.isSelected = false
                btnAllReview.setTextColor(ResourcesCompat.getColor(resources, R.color.grey, null))
                btnOneStart.isSelected = true
                btnOneStart.setTextColor(ResourcesCompat.getColor(resources, R.color.blue, null))
                btnTwoStart.isSelected = false
                btnTwoStart.setTextColor(ResourcesCompat.getColor(resources, R.color.grey, null))
                btnThreeStart.isSelected = false
                btnThreeStart.setTextColor(ResourcesCompat.getColor(resources, R.color.grey, null))
                btnFourStart.isSelected = false
                btnFourStart.setTextColor(ResourcesCompat.getColor(resources, R.color.grey, null))
                btnFiveStart.isSelected = false
                btnFiveStart.setTextColor(ResourcesCompat.getColor(resources, R.color.grey, null))
            }
            2 -> {
                btnAllReview.isSelected = false
                btnAllReview.setTextColor(ResourcesCompat.getColor(resources, R.color.grey, null))
                btnOneStart.isSelected = false
                btnOneStart.setTextColor(ResourcesCompat.getColor(resources, R.color.grey, null))
                btnTwoStart.isSelected = true
                btnTwoStart.setTextColor(ResourcesCompat.getColor(resources, R.color.blue, null))
                btnThreeStart.isSelected = false
                btnThreeStart.setTextColor(ResourcesCompat.getColor(resources, R.color.grey, null))
                btnFourStart.isSelected = false
                btnFourStart.setTextColor(ResourcesCompat.getColor(resources, R.color.grey, null))
                btnFiveStart.isSelected = false
                btnFiveStart.setTextColor(ResourcesCompat.getColor(resources, R.color.grey, null))
            }
            3 -> {
                btnAllReview.isSelected = false
                btnAllReview.setTextColor(ResourcesCompat.getColor(resources, R.color.grey, null))
                btnOneStart.isSelected = false
                btnOneStart.setTextColor(ResourcesCompat.getColor(resources, R.color.grey, null))
                btnTwoStart.isSelected = false
                btnTwoStart.setTextColor(ResourcesCompat.getColor(resources, R.color.grey, null))
                btnThreeStart.isSelected = true
                btnThreeStart.setTextColor(ResourcesCompat.getColor(resources, R.color.blue, null))
                btnFourStart.isSelected = false
                btnFourStart.setTextColor(ResourcesCompat.getColor(resources, R.color.grey, null))
                btnFiveStart.isSelected = false
                btnFiveStart.setTextColor(ResourcesCompat.getColor(resources, R.color.grey, null))
            }
            4 -> {
                btnAllReview.isSelected = false
                btnAllReview.setTextColor(ResourcesCompat.getColor(resources, R.color.grey, null))
                btnOneStart.isSelected = false
                btnOneStart.setTextColor(ResourcesCompat.getColor(resources, R.color.grey, null))
                btnTwoStart.isSelected = false
                btnTwoStart.setTextColor(ResourcesCompat.getColor(resources, R.color.grey, null))
                btnThreeStart.isSelected = false
                btnThreeStart.setTextColor(ResourcesCompat.getColor(resources, R.color.grey, null))
                btnFourStart.isSelected = true
                btnFourStart.setTextColor(ResourcesCompat.getColor(resources, R.color.blue, null))
                btnFiveStart.isSelected = false
                btnFiveStart.setTextColor(ResourcesCompat.getColor(resources, R.color.grey, null))
            }
            5 -> {
                btnAllReview.isSelected = false
                btnAllReview.setTextColor(ResourcesCompat.getColor(resources, R.color.grey, null))
                btnOneStart.isSelected = false
                btnOneStart.setTextColor(ResourcesCompat.getColor(resources, R.color.grey, null))
                btnTwoStart.isSelected = false
                btnTwoStart.setTextColor(ResourcesCompat.getColor(resources, R.color.grey, null))
                btnThreeStart.isSelected = false
                btnThreeStart.setTextColor(ResourcesCompat.getColor(resources, R.color.grey, null))
                btnFourStart.isSelected = false
                btnFourStart.setTextColor(ResourcesCompat.getColor(resources, R.color.grey, null))
                btnFiveStart.isSelected = true
                btnFiveStart.setTextColor(ResourcesCompat.getColor(resources, R.color.blue, null))
            }
        }
    }
}
