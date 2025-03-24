package io.violabs.plugins

import org.gradle.api.Plugin
import org.gradle.api.Project

open class AssetManagementPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        target.logger.lifecycle("Applying AssetManagementPlugin")
        val extension = target.extensions.create("assetManagement", AssetManagementExtension::class.java)
        target.tasks.register("printAssets", PrintAssetJsonTask::class.java) {
            modAssets = extension.assets()
        }

        val generateFilesTask = target.tasks.register("generateItemJsonFiles", GenerateItemJsonFileTask::class.java) {
            modAssets = extension.assets()
            customAssetPath = extension.assetPath ?: ""
        }

        if (extension.automaticUpdate) {
            target.logger.lifecycle("Automatic update is enabled")
            target.tasks.named("build") {
                // Once build completes, run the generateFiles task
                finalizedBy(generateFilesTask)
            }
        }
    }
}