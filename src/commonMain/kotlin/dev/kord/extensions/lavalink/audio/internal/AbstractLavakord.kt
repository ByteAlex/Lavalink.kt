package dev.kord.extensions.lavalink.audio.internal

import dev.kord.extensions.lavalink.audio.*
import io.ktor.http.*
import kotlinx.coroutines.launch
import dev.kord.extensions.lavalink.LavaKord
import dev.kord.extensions.lavalink.LavaKordOptions
import dev.kord.extensions.lavalink.computeIfAbsent

/**
 * Abstract implementation of [LavaKord].
 *
 * @property options [LavaKordOptions] object from builder
 * @property linksMap [Map] all [Link]s are stored in
 */
public abstract class AbstractLavakord(
    override val userId: Long,
    override val shardsTotal: Int,
    protected val options: LavaKordOptions
) : LavaKord {
    private var nodeCounter = 0
    private val nodesMap = mutableMapOf<String, Node>()
    protected val linksMap: MutableMap<Long, Link> = mutableMapOf()

    @Suppress("LeakingThis")
    private val loadBalancer: LoadBalancer = LoadBalancer(options.loadBalancer.penaltyProviders, this)

    override val nodes: List<Node>
        get() = nodesMap.values.toList()

    internal fun removeDestroyedLink(link: Link) = linksMap.remove(link.guildId)

    override fun getLink(guildId: Long): Link {
        return linksMap.computeIfAbsent(guildId) {
            val node = loadBalancer.determineBestNode(guildId) as NodeImpl
            buildNewLink(guildId, node)
        }
    }

    override fun addNode(serverUri: Url, password: String, name: String?) {
        if (name != null) {
            check(!nodesMap.containsKey(name)) { "Name is already in use" }
        }
        nodeCounter++
        val finalName = name ?: "Lavalink_Node_#$nodeCounter"
        val node =
            NodeImpl(serverUri, finalName, password, this, options.link.maxReconnectTries, options.link.resumeTimeout)
        nodesMap[finalName] = node
        launch {
            node.connect()
        }
    }

    override fun removeNode(name: String) {
        val node = nodesMap.remove(name)
        requireNotNull(node) { "There is no node with that name" }
        node.close()
    }

    /**
     * Forwards an voice server update event to Lavalink.
     */
    protected suspend fun forwardVoiceEvent(
        link: Link,
        guildId: String,
        sessionId: String,
        event: DiscordVoiceServerUpdateData
    ) {
        (link.node as NodeImpl).send(
            GatewayPayload.VoiceUpdateCommand(
                guildId,
                sessionId,
                event
            )
        )
    }

    /**
     * Abstract function to create a new [Link] for this [guild][guildId] using this [node].
     */
    protected abstract fun buildNewLink(guildId: Long, node: Node): Link
}