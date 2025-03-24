package io.violabs.plugins

import org.gradle.api.Project
import java.io.File

class FileManager(private val project: Project) {
    private val logger = project.logger

    fun generateFiles(
        modAssets: ModAssets? = null,
        customAssetPath: String = ""
    ) {
        if (modAssets == null) {
            logger.error("No assets found")
            return
        }

        val modAssetPath = customAssetPath
            .takeIf { it.isNotBlank() }
            ?: "src/main/resources/assets/${modAssets.modName}"
        val itemDetailsPath = "$modAssetPath/items"
        val itemModelPath = "$modAssetPath/models/item"

        val fullItemDetailPath = project.projectDir.resolve(itemDetailsPath)
        val fullItemModelPath = project.projectDir.resolve(itemModelPath)

        logger.lifecycle("itemPath: $fullItemDetailPath")
        logger.lifecycle("itemModelPath: $fullItemModelPath")

        val paths = Paths(fullItemDetailPath, fullItemModelPath)

        modAssets.items().forEach {
            convertItemIntoJsonFiles(it, paths)
        }
    }

    private fun convertItemIntoJsonFiles(
        item: ModAssets.Item,
        paths: Paths
    ) {
        val fileName = "${item.itemName}.json"
        val itemFile = File(paths.itemPath, fileName)
        val itemModelFile = File(paths.itemModelPath, fileName)

        val itemFileJson = item.toItemDetailsJson()

        if (itemFile.exists() && itemFile.readText() == itemFileJson) {
            logger.lifecycle("Items file already exists, skipping: $fileName")
        } else {
            logger.lifecycle("Generating item file: $fileName")
            if (!itemFile.exists()) {
                itemFile.createNewFile()
            }

            itemFile.writeText(itemFileJson)
        }

        val itemModelFileJson = item.toItemModelJson()

        if (itemModelFile.exists() && itemModelFile.readText() == itemModelFileJson) {
            logger.lifecycle("Model file already exists, skipping: $fileName")
        } else {
            logger.lifecycle("Generating model file: $fileName")
            if (!itemModelFile.exists()) {
                itemModelFile.createNewFile()
            }

            itemModelFile.writeText(itemModelFileJson)
        }
    }
}

class Paths(
    val itemPath: File,
    val itemModelPath: File
)