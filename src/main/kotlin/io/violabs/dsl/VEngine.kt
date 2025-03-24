package io.violabs.dsl

import io.violabs.config.RegistryKey
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents
import net.minecraft.item.Item
import org.slf4j.LoggerFactory

class VEngine(
    val modId: String
) {
    val logger = LoggerFactory.getLogger(modId)

    fun modifyMenu(scope: MenuEntryBuilder.() -> Unit) {
        val menuBuilder = MenuEntryBuilder()
        scope(menuBuilder)
        menuBuilder.register()
    }

    class MenuEntryBuilder {
        private val mapping: MutableMap<RegistryKey.ItemGroup, MenuTableBuilder> = mutableMapOf()

        fun group(group: RegistryKey.ItemGroup, scope: MenuTableBuilder.() -> Unit) {
            val table = MenuTableBuilder()
            scope(table)
            if (!mapping.containsKey(group)) mapping[group] = table
        }

        fun register() {
            mapping.forEach { (group, table) ->
                ItemGroupEvents.modifyEntriesEvent(group.nativeKey).register { registry ->
                    table.items().forEach { item -> registry.add(item) }
                }
            }
        }

        class MenuTableBuilder {
            private val items: MutableList<Item> = mutableListOf()

            fun addItem(item: Item) {
                items.add(item)
            }

            fun items(): MutableList<Item> = items
        }
    }
}