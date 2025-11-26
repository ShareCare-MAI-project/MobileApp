package itemManager.components

enum class DeliveryType {
    PICKUP, MAIL, OWNER_DELIVERY;

    val title: String
        get() = when (this) {
            PICKUP -> "Самовывоз"
            MAIL -> "Доставка почтой"
            OWNER_DELIVERY -> "Человек доставит сам"
        }
}