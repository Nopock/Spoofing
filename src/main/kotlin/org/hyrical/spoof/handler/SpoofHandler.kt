package org.hyrical.spoof.handler

import org.bukkit.Bukkit
import org.hyrical.spoof.HySpoof
import org.hyrical.spoof.storage.StoredSpoofPlayer
import org.hyrical.spoof.thread.SpoofTask
import java.io.File
import java.util.*

/**
 * This class is responsible for handling spoofed players.
 *
 * @author [Your Name]
 */
class SpoofHandler {
    /**
     * A list of all spoofed players.
     */
    val allSpoofedPlayers = mutableListOf<StoredSpoofPlayer>()

    /**
     * A list of spoofed players that have been processed.
     */
    val spoofedPlayers = mutableListOf<Pair<UUID, StoredSpoofPlayer>>()

    /**
     * The file containing the list of spoofed players.
     */
    private val file = File(HySpoof.instance.dataFolder, "spoofed_players.txt")

    /**
     * Initializes the `SpoofHandler` instance by reading the list of spoofed players from the file.
     */
    init {
        if (!file.exists()) {
            file.createNewFile()
        }

        file.readLines().forEach {
            val split = it.split(" ")

            val uuid = UUID.fromString(split[0])
            val name = split[1]
            val value = split[2]
            val signature = split[3]

            val storedSpoofPlayer = StoredSpoofPlayer(uuid, name, StoredSpoofPlayer.SkinData(value, signature))

            allSpoofedPlayers.add(storedSpoofPlayer)
        }
    }

    /**
     * Starts the thread that handles spoofed players.
     */
    fun startThread() {
        SpoofTask().start()
    }

    /**
     * Gets the next spoofed player to process.
     *
     * @return the next spoofed player to process, or `null` if all players have been processed
     */
    fun getNextSpoofPlayer(): StoredSpoofPlayer? {
        if (allSpoofedPlayers.size == spoofedPlayers.size) return null

        for (player in allSpoofedPlayers) {
            if (!spoofedPlayers.contains(Pair(player.uuid, player))) {
                spoofedPlayers.add(Pair(player.uuid, player))
                return player
            }
        }

        return null
    }
}
