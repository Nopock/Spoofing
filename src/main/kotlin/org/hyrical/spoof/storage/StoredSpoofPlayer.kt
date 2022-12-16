package org.hyrical.spoof.storage

import java.util.UUID

/**
 * This class represents a stored spoof player.
 *
 * @author [Your Name]
 */
data class StoredSpoofPlayer(
    /**
     * The unique identifier for this player.
     */
    val uuid: UUID,

    /**
     * The player's in-game name.
     */
    val name: String,

    /**
     * The player's skin data.
     */
    val skinData: SkinData
) {
    /**
     * This nested class represents the skin data for a player.
     */
    data class SkinData(
        /**
         * The value of the player's skin.
         */
        val value: String,

        /**
         * The signature of the player's skin.
         */
        val signature: String
    )
}
