package io.violabs.dsl.builder

import net.minecraft.entity.effect.StatusEffect
import net.minecraft.entity.effect.StatusEffectInstance
import net.minecraft.entity.effect.StatusEffects
import net.minecraft.registry.entry.RegistryEntry

class StatusEffectBuilder {
    private var entry: RegistryEntry<StatusEffect>? = null
    var duration: Int = 1
    var amplifier: Int = 1

    fun markPoisonous() {
        if (entry != null) {
            throw IllegalStateException("Entry already set")
        }

        entry = StatusEffects.POISON
    }

    fun build(): StatusEffectInstance {
        requireNotNull(entry) { "Entry must not be null" }

        return StatusEffectInstance(entry, duration, amplifier)
    }
}