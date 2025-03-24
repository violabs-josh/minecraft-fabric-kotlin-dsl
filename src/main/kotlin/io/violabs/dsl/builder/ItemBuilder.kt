package io.violabs.dsl.builder

import io.violabs.config.RegistryKey
import net.minecraft.item.Item
import net.minecraft.item.SwordItem
import net.minecraft.item.ToolMaterial
import net.minecraft.registry.RegistryKey as MinecraftRegistryKey

class ItemBuilder(private val name: String) {
    private val key = RegistryKey.Item(name)
    private var settings: Item.Settings? = null
    private var swordDetails: SwordDetails? = null

    fun key(): MinecraftRegistryKey<Item> = key.nativeKey

    fun itemSettings(scope: (ItemSettingsBuilder.() -> Unit)? = null): Item.Settings {
        val builder = ItemSettingsBuilder(key)
        scope?.invoke(builder)
        settings = builder.build()
        return settings!!
    }

    fun swordConfig(scope: (SwordDetails.() -> Unit)? = null) {
        swordDetails = SwordDetails()
        scope?.invoke(swordDetails!!)
    }

    fun build(): Item {
        val settings = settings ?: ItemSettingsBuilder(key).build()
        return swordDetails
            ?.let {
                val material = requireNotNull(it.material) { "Material must be set" }
                SwordItem(material, it.attackDamage, it.attackSpeed, settings)
            } ?: Item(settings)
    }

    class SwordDetails(
        var material: ToolMaterial? = null,
        var attackDamage: Float = 1f,
        var attackSpeed: Float = 1f
    )
}