package io.violabs.config

import io.violabs.dsl.register

object ModItems {
    val SUSPICIOUS_SUBSTANCE = register {
        addItem("suspicious_substance")
    }

    val POISONOUS_APPLE = register {
        val foodConfig = foodComponent {
            alwaysEdible()
            withEffect {
                everyTime()
                addStatusEffect {
                    markPoisonous()
                    duration = 6 * 20
                }
            }
        }

        addItem("poisonous_apple", foodConfig)
    }

    val GUIDITE_SWORD = register {
        addItem("guidite_sword") {
            swordConfig {
                material = ModToolMaterials.GUIDITE
            }
        }
    }

    @JvmStatic
    fun initialize() {
        println("Initializing Items")
    }
}
