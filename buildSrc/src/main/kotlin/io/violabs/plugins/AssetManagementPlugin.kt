package io.violabs.plugins

import org.gradle.api.Plugin
import org.gradle.api.Project

open class AssetManagementPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        println("Applying AssetManagementPlugin")
        val extension = target.extensions.create("assetManagement", AssetManagementExtension::class.java)
        target.tasks.register("printAssets", PrintAssetJsonTask::class.java) {
            modAssets = extension.assets()
        }
    }
}