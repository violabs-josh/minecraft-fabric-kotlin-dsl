package io.violabs.plugins

import org.gradle.api.DefaultTask
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.TaskAction

open class PrintAssetJsonTask : DefaultTask() {
    init {
        group = "assetManagement"
    }

    @Input
    var modAssets: ModAssets? = null

    @TaskAction
    fun printJson() {
        if (modAssets == null) {
            println("No assets found")
            return
        }

        modAssets?.items()?.forEach(::convertItemIntoJsonFiles)
    }

    private fun convertItemIntoJsonFiles(item: ModAssets.Item) {
        val itemDetails = item.toItemDetailsJson()
        println(itemDetails)
        val itemModel = item.toItemModelJson()
        println(itemModel)
    }
}