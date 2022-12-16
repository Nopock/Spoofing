package org.hyrical.spoof.player

import com.mojang.authlib.GameProfile
import net.minecraft.server.v1_8_R3.EntityPlayer
import net.minecraft.server.v1_8_R3.MinecraftServer
import org.bukkit.Bukkit
import org.hyrical.spoof.HySpoof


class SpoofPlayer(gameProfile: GameProfile) : EntityPlayer(
    MinecraftServer.getServer(),
    MinecraftServer.getServer().getWorldServer(0),
    gameProfile,
    SpoofPlayerInteractManager(MinecraftServer.getServer().getWorldServer(0))
) {
    init {
        this.playerConnection = SpoofPlayerConnection(this)

        fauxSleeping = true

    }
}