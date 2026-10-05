package org.example.project.presentation.screens.home

import haircutprank.shared.generated.resources.*
import org.jetbrains.compose.resources.DrawableResource

object CategoryResourceMapper {
    fun getLocalBackground(categoryKey: String): DrawableResource? {
        return when (categoryKey) {
            "hair_clipper" -> Res.drawable.bg_hair_clipper
            "air_horn" -> Res.drawable.bg_air_horn
            "breaking" -> Res.drawable.bg_breaking
            "fart" -> Res.drawable.bg_fart
            "burp" -> Res.drawable.bg_burp
            "toilet_flushing" -> Res.drawable.bg_toilet_flushing
            "gun" -> Res.drawable.bg_gun
            "car" -> Res.drawable.bg_car
            "meme" -> Res.drawable.bg_meme
            "siren" -> Res.drawable.bg_animals
            "taser" -> Res.drawable.bg_hair_clipper
            "scary" -> Res.drawable.bg_breaking
            "animals" -> Res.drawable.bg_bomb
            "bomb" -> Res.drawable.bg_bomb
            else -> null
        }
    }

    fun getLocalIcon(categoryKey: String): DrawableResource? {
        return when (categoryKey) {
            "hair_clipper" -> Res.drawable.ic_hair
            "air_horn" -> Res.drawable.ic_air
            "breaking" -> Res.drawable.ic_breaking
            "fart" -> Res.drawable.ic_fart
            "burp" -> Res.drawable.ic_burp
            "toilet_flushing" -> Res.drawable.ic_toilet
            "gun" -> Res.drawable.ic_gun
            "car" -> Res.drawable.ic_car
            "meme" -> Res.drawable.ic_meme
            "siren" -> Res.drawable.ic_police
            else -> null
        }
    }
}
