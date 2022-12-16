package org.hyrical.spoof

import com.avaje.ebean.EbeanServer
import org.bukkit.Bukkit
import org.bukkit.GameMode
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerJoinEvent
import org.bukkit.plugin.java.JavaPlugin
import org.hyrical.spoof.handler.SpoofHandler
import org.hyrical.spoof.listeners.DeathListener

class HySpoof : JavaPlugin() {

    companion object {
        lateinit var instance: HySpoof
    }

    lateinit var handler: SpoofHandler

    override fun onEnable() {
        instance = this

        saveDefaultConfig()

        handler = SpoofHandler()

        Bukkit.getPluginManager().registerEvents(object : Listener {

            @EventHandler
            fun onJoin(event: PlayerJoinEvent) {
                handler.startThread()
            }

        }, this)

        //handler.startThread()

        server.pluginManager.registerEvents(DeathListener(), this)

        println("HySpoof has been enabled!")
    }

}