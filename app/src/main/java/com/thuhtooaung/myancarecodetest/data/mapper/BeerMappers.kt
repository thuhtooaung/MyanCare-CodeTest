package com.thuhtooaung.myancarecodetest.data.mapper

import com.thuhtooaung.myancarecodetest.data.model.Beer
import com.thuhtooaung.myancarecodetest.data.local.BeerEntity
import com.thuhtooaung.myancarecodetest.data.remote.BeerDto

fun BeerDto.toBeerEntity(): BeerEntity {
    return BeerEntity(
        id = id,
        name = name,
        tagline = tagline,
        description = description,
        imageUrl = image_url
    )
}

fun BeerEntity.toBeer(): Beer {
    return Beer(
        id = id,
        name = name,
        tagline = tagline,
        description = description,
        imageUrl = imageUrl
    )
}