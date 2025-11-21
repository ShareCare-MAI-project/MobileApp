package itemManager.components

enum class DeliveryType {
    PICKUP, MAIL, SELF_DELIVERY;

    val title: String
        get() = when (this) {
            PICKUP -> "Самовывоз"
            MAIL -> "Доставка почтой"
            SELF_DELIVERY -> "Человек доставит сам"
        }
}