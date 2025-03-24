package io.violabs.config

import io.violabs.dsl.createToolMaterial
import net.minecraft.registry.tag.BlockTags
import net.minecraft.registry.tag.ItemTags

object ModToolMaterials {

    val GUIDITE = createToolMaterial {
        invalidDropBlockTag = BlockTags.INCORRECT_FOR_WOODEN_TOOL
        durability = 455
        miningSpeed = 5f
        attackDamageBonus = 1.5f
        enchantmentValue = 22
        toolsRepairableBy = ItemTags.DIRT
    }

    @JvmStatic
    fun initialize() {
        println("Initializing Items")
    }
}