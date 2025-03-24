package io.violabs.plugins

open class AssetManagementExtension {
    private var assets: ModAssets? = null

    fun assets(): ModAssets? = assets

    fun modAssets(name: String, config: ModAssets.() -> Unit = {}): ModAssets {
        val mod = ModAssets(name)
        mod.apply(config)
        assets = mod
        return mod
    }
}