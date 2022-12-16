package org.hyrical.spoof.listeners

import org.bukkit.GameMode
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.entity.EntityDamageEvent
import org.bukkit.event.entity.PlayerDeathEvent
import org.hyrical.spoof.HySpoof

class DeathListener : Listener {

    val handler = HySpoof.instance.handler

    @EventHandler
    fun onDeath(event: PlayerDeathEvent) {
        if (handler.spoofedPlayers.map { it.first }.contains(event.entity.uniqueId)) {
            event.entity.spigot().respawn()
        }
    }

    @EventHandler
    fun onDamage(event: EntityDamageEvent) {
        if (event.entity is Player) {
            val player = event.entity as Player
            if (handler.spoofedPlayers.map { it.first }.contains(player.uniqueId)) {
                if (player.gameMode == GameMode.SURVIVAL) {
                    player.velocity = player.velocity.multiply(-1);
                }
            }
        }
    }
    // entity.getVelocity().multiply(-1);
}