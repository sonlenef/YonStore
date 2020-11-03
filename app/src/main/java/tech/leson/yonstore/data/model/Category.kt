package tech.leson.yonstore.data.model

import tech.leson.yonstore.R
import java.io.Serializable

data class Category(var uid: String, val name: String, val description: String, val group: String) :
    Serializable {

    constructor() : this("", "", "", "")

    fun getIcon(): Int {
        return when (name) {
            "Man Shirt" -> R.drawable.ct_man_shirt
            "Man Work Equipment" -> R.drawable.ct_man_bag
            "Man T-Shirt" -> R.drawable.ct_man_tshirt
            "Man Shoes" -> R.drawable.ct_man_shoes
            "Man Pants" -> R.drawable.ct_man_pants
            "Man Underwear" -> R.drawable.ct_man_underwear
            "Dress" -> R.drawable.ct_woman_dress
            "Woman T-Shirt" -> R.drawable.ct_woman_tshirt
            "Woman Pants" -> R.drawable.ct_woman_pants
            "Skirt" -> R.drawable.ct_woman_skirt
            "Woman Bag" -> R.drawable.ct_woman_bag
            "High Heels" -> R.drawable.ct_woman_shoes
            "Bikini" -> R.drawable.ct_woman_bikini
            else -> 0
        }
    }
}
