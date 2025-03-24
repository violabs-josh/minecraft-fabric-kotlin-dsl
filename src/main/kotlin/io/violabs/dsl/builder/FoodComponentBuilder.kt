package io.violabs.dsl.builder

import io.violabs.dsl.FoodConfig
import net.minecraft.component.type.ConsumableComponent
import net.minecraft.component.type.ConsumableComponents
import net.minecraft.component.type.FoodComponent
import net.minecraft.item.consume.ApplyEffectsConsumeEffect

class FoodComponentBuilder {
    private var effect: ApplyEffectsConsumeEffect? = null
    private var alwaysEdible: Boolean = false
    var nutrition: Int = 0
    var saturationModifier: Float = 0.0f

    /**
     * Adds an effect to the food item. There can only be one effect per food item,
     * but that effect can have multiple combinations of status effects.
     */
    fun withEffect(scope: ConsumedEffectBuilder.() -> Unit) {
        if (effect != null) {
            throw IllegalStateException("Effect already set")
        }

        val builder = ConsumedEffectBuilder()
        scope(builder)
        effect = builder.build()
    }

    fun alwaysEdible() {
        alwaysEdible = true
    }

    fun buildComponent(): FoodComponent {
        val foodBuilder = FoodComponent.Builder()
        foodBuilder.nutrition(nutrition)
        foodBuilder.saturationModifier(saturationModifier)
        if (alwaysEdible) {
            foodBuilder.alwaysEdible()
        }
        return foodBuilder.build()
    }

    fun buildConsumableComponent(): ConsumableComponent {
        val foodBuilder: ConsumableComponent.Builder = ConsumableComponents.food()

        effect?.let { foodBuilder.consumeEffect(effect) }

        return foodBuilder.build()
    }

    fun toFoodConfig(): FoodConfig = FoodConfig(
        buildComponent(),
        buildConsumableComponent()
    )
}