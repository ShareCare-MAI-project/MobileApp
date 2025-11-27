package dto

import entity.ItemResponse

fun ItemResponseDTO.toDomain() = ItemResponse(
    title = title,
    description = description,
    location = location,
    category = category,
    deliveryTypes = deliveryTypes,
    id = id,
    ownerId = ownerId,
    recipientId = recipientId,
    images = images
)