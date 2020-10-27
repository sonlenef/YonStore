package tech.leson.yonstore.ui.main

enum class CATEGORY(s: String) {
    MAN_SHIRT("Man Shirt"),
    MAN_WORD_EQUIPMENT("Man Work Equipment"),
    MAN_T_SHIRT("Man T-Shirt"),
    MAN_SHOES("Man Shoes"),
    MAN_PANTS("Man Pants"),
    MAN_UNDERWEAR("Man Underwear"),
    WOMAN_DRESS("Dress"),
    WOMAN_T_SHIRT("Woman T-Shirt"),
    WOMAN_PANTS("Woman Pants"),
    WOMAN_SKIRT("Skirt"),
    WOMAN_BAG("Woman Bag"),
    WOMAN_SHOES("High Heels"),
    WOMAN_BIKINI("Bikini");

    companion object {
        fun fromString(text: String?): CATEGORY? {
            for (b in values()) {
                if (b.name == text) {
                    return b
                }
            }
            return null
        }
    }
}
