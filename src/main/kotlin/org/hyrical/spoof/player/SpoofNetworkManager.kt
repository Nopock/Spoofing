package org.hyrical.spoof.player

import net.minecraft.server.v1_8_R3.EnumProtocolDirection
import net.minecraft.server.v1_8_R3.NetworkManager
import java.net.InetAddress
import java.net.InetSocketAddress
import java.net.SocketAddress

class SpoofNetworkManager : NetworkManager(EnumProtocolDirection.SERVERBOUND) {

    init {
        channel = SpoofChannel(null)
    }

    override fun getSocketAddress(): SocketAddress? {
        return try {
            InetSocketAddress(InetAddress.getLocalHost(), 0)
        } catch (e: Throwable) {
            null
        }
    }

    override fun g(): Boolean {
        return true
    }
}