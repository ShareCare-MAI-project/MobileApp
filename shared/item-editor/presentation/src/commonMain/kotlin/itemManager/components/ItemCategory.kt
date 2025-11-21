package itemManager.components

enum class ItemCategory {
    CLOTHES, TOYS, HOUSEHOLD, ELECTRONICS, OTHER;

    val title: String
        get() = when (this) {
            CLOTHES -> "Одежда"
            TOYS -> "Игрушки"
            HOUSEHOLD -> "Быт"
            ELECTRONICS -> "Электроника"
            OTHER -> "Другое"
        }
}