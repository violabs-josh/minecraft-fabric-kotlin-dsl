package io.violabs.dsl

import net.minecraft.component.type.ConsumableComponent
import net.minecraft.component.type.FoodComponent

data class FoodConfig(
    val foodComponent: FoodComponent,
    val consumableComponent: ConsumableComponent? = null
)