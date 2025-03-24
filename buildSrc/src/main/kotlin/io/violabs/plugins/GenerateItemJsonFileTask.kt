package io.violabs.plugins

import org.gradle.api.DefaultTask
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.TaskAction

open class GenerateItemJsonFileTask : DefaultTask() {
    init {
        group = "assetManagement"
    }

    @Input
    var modAssets: ModAssets? = null

    @Input
    var customAssetPath: String = ""

    @TaskAction
    fun generateFiles() {
        FileManager(project).generateFiles(modAssets, customAssetPath)
    }
}
