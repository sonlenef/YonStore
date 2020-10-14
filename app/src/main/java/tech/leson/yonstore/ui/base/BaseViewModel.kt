package tech.leson.yonstore.ui.base

import android.view.View
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.reactivex.rxjava3.disposables.CompositeDisposable
import tech.leson.yonstore.data.DataManager
import tech.leson.yonstore.utils.rx.SchedulerProvider
import java.lang.ref.WeakReference

abstract class BaseViewModel<N>(
    private var mDataManager: DataManager,
    private var mSchedulerProvider: SchedulerProvider,
) : ViewModel() {

    private val mIsLoading = MutableLiveData<Boolean>()

    private var mCompositeDisposable: CompositeDisposable = CompositeDisposable()
    private var mNavigator: WeakReference<N>? = null

    override fun onCleared() {
        mCompositeDisposable.dispose()
        super.onCleared()
    }

    val compositeDisposable: CompositeDisposable
        get() = mCompositeDisposable

    val dataManager: DataManager
        get() = mDataManager

    abstract fun onClick(view: View)

    open fun getIsLoading(): MutableLiveData<Boolean>? {
        return mIsLoading
    }

    open fun setIsLoading(isLoading: Boolean) {
        mIsLoading.value = isLoading
    }

    val navigator: N?
        get() = mNavigator!!.get()

    fun setNavigator(navigator: N) {
        mNavigator = WeakReference(navigator)
    }

    val schedulerProvider: SchedulerProvider
        get() = mSchedulerProvider
}
