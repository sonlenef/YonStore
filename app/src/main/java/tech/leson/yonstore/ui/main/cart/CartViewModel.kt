package tech.leson.yonstore.ui.main.cart

import android.view.View
import androidx.lifecycle.MutableLiveData
import tech.leson.yonstore.R
import tech.leson.yonstore.data.DataManager
import tech.leson.yonstore.data.model.Cart
import tech.leson.yonstore.data.model.Product
import tech.leson.yonstore.data.model.ProductInCart
import tech.leson.yonstore.data.model.User
import tech.leson.yonstore.ui.base.BaseViewModel
import tech.leson.yonstore.utils.rx.SchedulerProvider

class CartViewModel(dataManager: DataManager, schedulerProvider: SchedulerProvider) :
    BaseViewModel<CartNavigator>(dataManager, schedulerProvider) {

    val user: MutableLiveData<User> = MutableLiveData()

    private fun getUserCurrent() {
        setIsLoading(true)
        dataManager.getUser(dataManager.getUserUid())
            .addOnSuccessListener {
                for (doc in it) {
                    user.postValue(doc.toObject(User::class.java))
                }
                navigator?.updateCart()
                setIsLoading(false)
            }
            .addOnFailureListener {
                navigator?.onMsg(it.message.toString())
                setIsLoading(false)
            }
    }

    fun getCartProducts() {
        setIsLoading(true)
        dataManager.getUser(dataManager.getUserUid())
            .addOnSuccessListener {
                for (doc in it) {
                    doc.toObject(User::class.java).cart.forEach { cart ->
                        getProduct(cart)
                    }
                    user.postValue(doc.toObject(User::class.java))
                }
                setIsLoading(false)
            }
            .addOnFailureListener {
                navigator?.onMsg(it.message.toString())
                setIsLoading(false)
            }
    }

    private fun getProduct(cart: Cart) {
        dataManager.getProductById(cart.productId)
            .addOnSuccessListener {
                val product = it.toObject(Product::class.java)
                if (product != null) {
                    product.id = it.id
                }
                navigator?.addProduct(ProductInCart(product!!, cart.style, cart.qty))
            }
            .addOnFailureListener {
                navigator?.onMsg(it.message.toString())
            }
    }

    fun updateUser() {
        setIsLoading(true)
        dataManager.updateUser(user.value!!)
            .addOnSuccessListener {
                getUserCurrent()
                setIsLoading(false)
            }
            .addOnFailureListener {
                setIsLoading(false)
                navigator?.onMsg(it.message.toString())
            }
    }

    fun checkRestProduct(productInCart: ProductInCart, positionInCart: Int): Boolean {
        var isStocking = false
        dataManager.getProductById(productInCart.product.id)
            .addOnSuccessListener {
                val product = it.toObject(Product::class.java)
                product?.styles!!.forEach { item ->
                    if (item.size == productInCart.style.size && item.color == productInCart.style.color) {
                        if (productInCart.qty > item.rest) {
                            user.value!!.cart[positionInCart].qty = item.rest
                            updateUser()
                            navigator?.maxItem(productInCart, positionInCart)
                            navigator?.onMsg("Product '${product.name}' is only ${item.rest} pieces left")
                            isStocking = false
                        } else {
                            user.value!!.cart[positionInCart].qty = productInCart.qty
                            updateUser()
                            isStocking = true
                        }
                    }
                }
            }
            .addOnFailureListener {
                navigator?.onMsg(it.message.toString())
            }
        return isStocking
    }

    override fun onClick(view: View) {
        when (view.id) {
            R.id.btnApply -> {
            }
            R.id.btnCheckOut -> navigator?.onCheckOut()
        }
    }
}
