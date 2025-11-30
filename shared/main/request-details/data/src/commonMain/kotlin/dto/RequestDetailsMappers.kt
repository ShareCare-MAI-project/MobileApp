package dto

import entities.Request

fun Request.toDTO() = RequestDTO(
    text = this.text,
    location = this.location,
    category = this.category.name,
    deliveryTypes = this.deliveryTypes.map { it.name }
)