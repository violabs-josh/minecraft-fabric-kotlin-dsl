package io.violabs.dsl.builder

import io.violabs.config.RegistryKey
import io.violabs.config.registryKey
import io.violabs.dsl.FoodConfig
import net.minecraft.item.Item

/**
 * Will build the Item Settings for the item.
 */
class ItemSettingsBuilder(private var registryKey: RegistryKey.Item) {
    private var foodConfig: FoodConfig? = null

    fun food(foodConfig: FoodConfig) {
        this.foodConfig = foodConfig
    }

    fun build(): Item.Settings {
        val settings = Item.Settings()

        foodConfig?.let {
            val component = foodConfig!!.foodComponent
            if (foodConfig!!.consumableComponent != null) {
                settings.food(component, foodConfig!!.consumableComponent!!)
            } else {
                settings.food(component)
            }
        }

        settings.registryKey(registryKey)

        return settings
    }
}