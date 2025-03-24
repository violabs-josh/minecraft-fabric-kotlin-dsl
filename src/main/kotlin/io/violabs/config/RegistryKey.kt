package io.violabs.config

import io.violabs.common.FabricModReferences
import net.minecraft.block.AbstractBlock
import net.minecraft.block.Block as MinecraftBlock
import net.minecraft.item.Item as MinecraftItem
import net.minecraft.item.ItemGroup as MinecraftItemGroup
import net.minecraft.item.ItemGroups
import net.minecraft.registry.RegistryKeys
import net.minecraft.util.Identifier
import net.minecraft.registry.RegistryKey as MinecraftRegistryKey

sealed class RegistryKey<T>(
    val nativeKey: MinecraftRegistryKey<T>
) {
    data class Block(val name: String) : RegistryKey<MinecraftBlock>(
        MinecraftRegistryKey.of(
            RegistryKeys.BLOCK,
            Identifier.of(FabricModReferences.MOD_ID, name)
        )
    )

    data class Item(val name: String) : RegistryKey<MinecraftItem>(
        MinecraftRegistryKey.of(
            RegistryKeys.ITEM,
            Identifier.of(FabricModReferences.MOD_ID, name)
        )
    )

    data class ItemGroup(
        val name: String? = null,
        val key: MinecraftRegistryKey<MinecraftItemGroup>? = null
    ) : RegistryKey<MinecraftItemGroup>(
        key ?: MinecraftRegistryKey.of(
            RegistryKeys.ITEM_GROUP,
            Identifier.of(FabricModReferences.MOD_ID, name)
        )
    ) {
        companion object {
            val INGREDIENTS = ItemGroup(key = ItemGroups.INGREDIENTS)
            val TOOLS = ItemGroup(key = ItemGroups.TOOLS)
        }
    }
}

fun AbstractBlock.Settings.registryKey(blockKey: RegistryKey.Block): AbstractBlock.Settings {
    return this.registryKey(blockKey.nativeKey)
}

fun MinecraftItem.Settings.registryKey(itemKey: RegistryKey.Item): MinecraftItem.Settings {
    return this.registryKey(itemKey.nativeKey)
}