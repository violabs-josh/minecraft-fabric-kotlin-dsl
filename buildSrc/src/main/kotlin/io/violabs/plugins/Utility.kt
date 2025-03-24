package io.violabs.plugins

enum class Utility {
	HANDHELD,
	GENERATED;

	override fun toString(): String = name.lowercase()
}