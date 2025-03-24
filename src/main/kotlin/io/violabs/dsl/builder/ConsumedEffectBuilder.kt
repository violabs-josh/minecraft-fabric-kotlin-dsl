package io.violabs.dsl.builder

import net.minecraft.entity.effect.StatusEffectInstance
import net.minecraft.item.consume.ApplyEffectsConsumeEffect

/**
 * Builds an effect that is applied when the food is consumed.
 * Access:
 * @property probability The probability that the effect will be applied.
 * @property addStatusEffect Adds a status effect that will be applied when the food is consumed.
 */
class ConsumedEffectBuilder {
    var probability: Float = 1.0f
    private var statusEffects: MutableSet<StatusEffectInstance> = mutableSetOf()

    fun everyTime() {
        probability = 1.0f
    }

    /**
     * Adds a status effect to the effect.
     */
    fun addStatusEffect(scope: StatusEffectBuilder.() -> Unit) {
        val builder = StatusEffectBuilder()
        scope(builder)
        statusEffects.add(builder.build())
    }

    fun build(): ApplyEffectsConsumeEffect = ApplyEffectsConsumeEffect(statusEffects.toList(), probability)
}