package io.violabs.plugins

class ModAssets(
	private val modName: String
) {
	private val items: MutableList<Item> = mutableListOf()

	fun item(name: String, config: Item.() -> Unit = {}) {
		val item = Item(name).apply(config)

		items.add(item)
	}

	fun items(): MutableList<Item> = items

	sealed class Metadata(
		val type: MinecraftModelType = MinecraftModelType.ITEM,
		open val utility: Utility? = null
	) {
		class Item(override val utility: Utility? = null) : Metadata(MinecraftModelType.ITEM)
		class Model(override val utility: Utility? = null) : Metadata(MinecraftModelType.MODEL)
	}

	inner class Item(
		private var itemName: String,
		private var metadata: Metadata = Metadata.Model(),
		private var parent: Metadata? = null
	) {
		private val textures: MutableList<Texture> = mutableListOf()

		fun parent(config: ParentMetadata.() -> Unit = {}) {
			val parentMetadata = ParentMetadata().apply(config)

			parent = parentMetadata.content
		}

		fun defaultParent() {
			parent = ParentMetadata().apply {
				itemUtility(Utility.GENERATED)
			}.content
		}

		fun toItemDetailsJson(): String = """
		|{
		|  "model": {
		|    "type": "${metadata.type}"
		|    "model": "$modName:item/$itemName"
		|  }
		|}
		""".trimMargin("|")

		fun toItemModelJson(): String {
			val parentMetadata = requireNotNull(parent) { "Parent metadata not set" }

			if (textures.isEmpty()) {
				textures.add(Texture(0, itemName))
			}

			val textureString = textures.joinToString(",") {
				"\"layer${it.index}\": \"$modName:item/${it.name}\""
			}

			return """
			|{
			|  "parent": "${parentMetadata.type}/${parentMetadata.utility}",
			|  "textures": {
			|    $textureString
			|  }
			|}
			""".trimMargin("|")
		}

		inner class ParentMetadata {
			var content: Metadata? = null
			fun itemUtility(utility: Utility? = null) = setOrThrowErrorIfExists(utility, Metadata::Item)
			fun modelUtility(utility: Utility? = null) = setOrThrowErrorIfExists(utility, Metadata::Model)

			private fun setOrThrowErrorIfExists(utility: Utility?, provider: (Utility?) -> Metadata) {
				if (content != null) {
					throw IllegalStateException("Metadata already set")
				}
				content = provider(utility)
			}
		}
	}

	class Texture(
		var index: Int = 0,
		var name: String? = null
	)
}