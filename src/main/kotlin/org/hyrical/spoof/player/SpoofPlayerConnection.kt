package org.hyrical.spoof.player

import net.minecraft.server.v1_8_R3.MinecraftServer
import net.minecraft.server.v1_8_R3.Packet
import net.minecraft.server.v1_8_R3.PlayerConnection

class SpoofPlayerConnection(spoofPlayer: SpoofPlayer) : PlayerConnection(
    MinecraftServer.getServer(),
    SpoofNetworkManager(),
    spoofPlayer
) {
    override fun sendPacket(packet: Packet<*>?) {

    }
}
