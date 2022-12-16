package org.hyrical.spoof.thread

import com.google.common.collect.ImmutableMap
import com.mojang.authlib.GameProfile
import com.mojang.authlib.properties.Property
import net.minecraft.server.v1_8_R3.MinecraftServer
import org.bukkit.Bukkit
import org.hyrical.spoof.HySpoof
import org.hyrical.spoof.lunar.AssetsWebSocket
import org.hyrical.spoof.lunar.AuthenticatorWebSocket
import org.hyrical.spoof.player.SpoofPlayer
import java.net.URISyntaxException
import java.util.*
import java.util.concurrent.ThreadLocalRandom
import java.util.function.Consumer

class SpoofTask : Thread("HySpoof-Task") {

    val handler = HySpoof.instance.handler

    val spoofPlayers = mutableListOf<SpoofPlayer>()

    override fun run() {
        while (true) {
            tick()

            sleep(10L)
        }
    }

    private fun tick() {
        if (!HySpoof.instance.isEnabled) return

        val nextPlayer = handler.getNextSpoofPlayer() ?: return

        val profile = GameProfile(nextPlayer.uuid, nextPlayer.name).apply {
            this.properties.put("textures", Property("textures", nextPlayer.skinData.value, nextPlayer.skinData.signature))
        }

        val spoofedPlayer = SpoofPlayer(profile).apply {
            this.ping = ThreadLocalRandom.current().nextInt(10, 130)
        }

        spoofedPlayer.bukkitEntity.apply {
            this.displayName = nextPlayer.name
        }

        Bukkit.getScheduler().runTask(HySpoof.instance, Runnable {
            val server = MinecraftServer.getServer()

            if (server == null) {
                Bukkit.getLogger().warning("MinecraftServer is null!")
                return@Runnable
            }

            val playerList = server.playerList

            if (playerList == null) {
                println("PlayerList is null!")
                return@Runnable
            }

            MinecraftServer.getServer().playerList.onPlayerJoin(spoofedPlayer, null)

            /*
            AuthenticatorWebSocket(nextPlayer.name, convertUUID(nextPlayer.uuid.toString())).apply {
                this.lIlIlIlIlIIlIIlIIllIIIIIl = Consumer {
                    try {
                        val assetsWebsocket = AssetsWebSocket(
                            mapOf(
                                "accountType" to "MOJANG",
                                "version" to "v1_8",
                                "gitCommit" to "2ef0680874b1d11cea10a4b969c6f5ec1f804d70",
                                "branch" to "master",
                                "os" to "Windows 10",
                                "arch" to "amd64",
                                "server" to "localhost",
                                "launcherVersion" to "2.8.9",
                                "username" to nextPlayer.name,
                                "playerId" to convertUUID(nextPlayer.uuid.toString()),
                                "Authorization" to "",
                                "clothCloak" to "false",
                                "hwid" to "00000000000000000000000000000000",
                            )
                        ).connect()
                    } catch (uRISyntaxException: URISyntaxException) {
                        uRISyntaxException.printStackTrace()
                    }

                }
            }
                .connect()

             */
        })
    }

    fun convertUUID(uuid: String): String {
        if (uuid.contains("-")) {
            return uuid
        }

        return uuid.replace("-", "").replaceFirst("(\\p{XDigit}{8})(\\p{XDigit}{4})(\\p{XDigit}{4})(\\p{XDigit}{4})(\\p{XDigit}+)", "$1-$2-$3-$4-$5");
    }
}