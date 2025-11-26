package itemManager.components

import entities.enums.DeliveryType
import entities.enums.DeliveryType.Mail
import entities.enums.DeliveryType.OwnerDelivery
import entities.enums.DeliveryType.Pickup
import entities.enums.ItemCategory
import entities.enums.ItemCategory.Clothes
import entities.enums.ItemCategory.Electronics
import entities.enums.ItemCategory.Household
import entities.enums.ItemCategory.Other
import entities.enums.ItemCategory.Toys

val DeliveryType.title: String
    get() = when (this) {
        Pickup -> "Самовывоз"
        Mail -> "Доставка почтой"
        OwnerDelivery -> "Человек доставит сам"
    }

val ItemCategory.title: String
    get() = when (this) {
        Clothes -> "Одежда"
        Toys -> "Игрушки"
        Household -> "Быт"
        Electronics -> "Электроника"
        Other -> "Другое"
    }