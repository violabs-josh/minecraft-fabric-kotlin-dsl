package io.violabs.plugins

import org.gradle.api.model.ObjectFactory
import org.gradle.api.provider.Property
import javax.inject.Inject

open class AssetManagementExtension @Inject constructor(objects: ObjectFactory) {
    private var assets: Property<ModAssets> = objects.property(ModAssets::class.java)
    var automaticUpdate: Boolean = true
    var assetPath: String? = null

    fun assets(): ModAssets? = assets.orNull

    fun modAssets(name: String, config: ModAssets.() -> Unit = {}): ModAssets {
        val mod = ModAssets(name)
        mod.apply(config)
        assets.set(mod)
        return mod
    }
}