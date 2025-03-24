package io.violabs

import io.violabs.common.FabricModReferences
import io.violabs.config.ModItems
import io.violabs.config.RegistryKey
import io.violabs.dsl.fabMod
import net.fabricmc.api.ModInitializer

object Beginner : ModInitializer {
    override fun onInitialize() = fabMod(FabricModReferences.MOD_ID) {
        // This code runs as soon as Minecraft is in a mod-load-ready state.
        // However, some things (like resources) may still be uninitialized.
        // Proceed with mild caution.
        logger.info("Hello Fabric world!")

        modifyMenu {
            group(RegistryKey.ItemGroup.INGREDIENTS) {
                addItem(ModItems.SUSPICIOUS_SUBSTANCE)
                addItem(ModItems.POISONOUS_APPLE)
            }

            group(RegistryKey.ItemGroup.TOOLS) {
                addItem(ModItems.GUIDITE_SWORD)
            }
        }
    }
}