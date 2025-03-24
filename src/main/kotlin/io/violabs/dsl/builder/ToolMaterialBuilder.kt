package io.violabs.dsl.builder

import net.minecraft.block.Block
import net.minecraft.item.Item
import net.minecraft.item.ToolMaterial
import net.minecraft.registry.tag.TagKey

class ToolMaterialBuilder {
    var durability: Int = 1
    var miningSpeed: Float = 1f
    var attackDamageBonus: Float = 0f
    var enchantmentValue: Int = 0
    var toolsRepairableBy: TagKey<Item>? = null
    var invalidDropBlockTag: TagKey<Block>? = null

    fun build(): ToolMaterial = ToolMaterial(
        invalidDropBlockTag,
        durability,
        miningSpeed,
        attackDamageBonus,
        enchantmentValue,
        toolsRepairableBy
    )
}