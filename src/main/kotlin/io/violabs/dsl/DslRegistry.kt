package io.violabs.dsl

import io.violabs.dsl.builder.FoodComponentBuilder
import io.violabs.dsl.builder.ItemBuilder
import net.minecraft.item.Item
import net.minecraft.registry.Registries
import net.minecraft.registry.Registry

class DslRegistry {
    val foodConfigs: MutableList<FoodConfig> = mutableListOf()

    fun foodComponent(scope: FoodComponentBuilder.() -> Unit): FoodConfig {
        val builder = FoodComponentBuilder()
        scope(builder)
        val foodConfig = builder.toFoodConfig()
        foodConfigs.add(foodConfig)
        return foodConfig
    }


    fun addItem(name: String, scope: (ItemBuilder.() -> Unit)? = null): Item {
        val builder = ItemBuilder(name)
        scope?.invoke(builder)
        val item: Item = builder.build()
        Registry.register(Registries.ITEM, builder.key(), item)
        return item
    }

    fun addItem(name: String, foodConfig: FoodConfig): Item = addItem(name) {
        itemSettings {
            food(foodConfig)
        }
    }
}