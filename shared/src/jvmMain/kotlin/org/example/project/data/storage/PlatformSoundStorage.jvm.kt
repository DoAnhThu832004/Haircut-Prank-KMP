package org.example.project.data.storage

import org.example.project.domain.model.Sound
import java.io.File

actual object PlatformSoundStorage {

    private val categoryMap = mapOf(
        "1_air_horn" to "Air Horn",
        "2_hair_clipper" to "Hair Clipper",
        "3_fart" to "Fart",
        "4_burp" to "Burp",
        "5_toilet_flushing" to "Toilet Flushing",
        "6_gun" to "Gun",
        "7_breaking" to "Breaking",
        "8_car" to "Car",
        "9_meme" to "Meme",
        "10_bomb" to "Bomb",
        "11_scary" to "Scary",
        "12_animals" to "Animals",
        "13_taser" to "Taser",
        "14_siren" to "Siren"
    )

    actual fun getInitialSounds(): List<Sound> {
        val candidateRoots = listOf(
            File("D:/test/HaircutPrank/sounds/taymay_haircut_version_4/4"),
            File("./sounds/taymay_haircut_version_4/4"),
            File(System.getProperty("user.home"), ".haircutprank/sounds/taymay_haircut_version_4/4")
        )

        val soundRoot = candidateRoots.firstOrNull { it.exists() && it.isDirectory }
            ?: return emptyList()

        val results = mutableListOf<Sound>()

        val categoryDirs = soundRoot.listFiles()?.filter { it.isDirectory }?.sortedBy {
            it.name.substringBefore("_").toIntOrNull() ?: 999
        } ?: emptyList()

        for (dir in categoryDirs) {
            val categoryName = categoryMap[dir.name]
                ?: dir.name.substringAfter("_").replace("_", " ").trim().split(" ")
                    .joinToString(" ") { s -> s.replaceFirstChar { it.uppercase() } }

            val iconFile = File(dir, "icon.webp").takeIf { it.exists() }?.absolutePath ?: ""

            val mp3Files = dir.walkTopDown()
                .filter { it.isFile && it.extension.equals("mp3", ignoreCase = true) }
                .sortedBy { file ->
                    val digits = file.name.filter { it.isDigit() }
                    digits.toIntOrNull() ?: 0
                }
                .toList()

            for (file in mp3Files) {
                val soundId = file.name.filter { it.isDigit() }.toIntOrNull() ?: results.size + 1
                val soundName = file.nameWithoutExtension.trim()

                results.add(
                    Sound(
                        id = soundId,
                        idCategory = categoryName,
                        name = soundName,
                        pathSound = file.absolutePath,
                        iconPath = iconFile,
                        checkFavorite = false
                    )
                )
            }
        }

        return results
    }
}
