package io.violabs.plugins

enum class MinecraftModelType {
	ITEM,
	MODEL;

	override fun toString(): String {
		return "$TYPE_ORIGIN:${name.lowercase()}"
	}

	companion object {
		const val TYPE_ORIGIN = "minecraft"
	}
}