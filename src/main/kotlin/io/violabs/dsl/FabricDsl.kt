package io.violabs.dsl

import io.violabs.config.ModItems
import io.violabs.config.ModToolMaterials
import io.violabs.dsl.builder.ToolMaterialBuilder
import net.minecraft.item.ToolMaterial

fun fabMod(modId: String, scope: VEngine.() -> Unit) {
    VEngine(modId).scope()
    ModItems.initialize()
    ModToolMaterials.initialize()
}

fun <T> register(scope: DslRegistry.() -> T): T {
    val registry = DslRegistry()
    return scope(registry)
}


fun createToolMaterial(scope: ToolMaterialBuilder.() -> Unit): ToolMaterial {
    val builder = ToolMaterialBuilder()
    scope(builder)
    return builder.build()
}

