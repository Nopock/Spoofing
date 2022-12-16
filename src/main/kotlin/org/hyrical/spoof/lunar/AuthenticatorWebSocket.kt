package org.hyrical.spoof.lunar

import com.google.gson.GsonBuilder
import com.google.gson.JsonObject
import com.google.gson.JsonParser
import com.mojang.authlib.exceptions.AuthenticationException
import com.mojang.authlib.exceptions.AuthenticationUnavailableException
import com.mojang.authlib.exceptions.InvalidCredentialsException
import com.mojang.authlib.yggdrasil.YggdrasilAuthenticationService
import org.bukkit.Bukkit
import org.hyrical.spoof.lunar.auth.AbstractAuthenticatorPacket
import org.hyrical.spoof.lunar.auth.CPacketEncryptionResponse
import org.hyrical.spoof.lunar.auth.SPacketAuthenticatedRequest
import org.hyrical.spoof.lunar.auth.SPacketEncryptionRequest
import org.java_websocket.client.WebSocketClient
import org.java_websocket.drafts.Draft_6455
import org.java_websocket.handshake.ServerHandshake
import java.io.IOException
import java.math.BigInteger
import java.net.Proxy
import java.net.URI
import java.nio.charset.StandardCharsets
import java.util.*
import java.util.function.Consumer
import java.util.logging.Level
import java.util.logging.LogRecord
import javax.crypto.SecretKey

class AuthenticatorWebSocket(val username: String, val id: String) : WebSocketClient(
    URI("wss://authenticator.lunarclientprod.com"),
    Draft_6455(),
    mapOf("username" to username, "playerId" to id),
    30000
) {

    init {
        println("Connecting to Lunar Client... $username $id")
    }

    val GSON = GsonBuilder().create()

    var lIlIlIlIlIIlIIlIIllIIIIIl: Consumer<String?>? = null
    var IlllIIIIIIlllIlIIlllIlIIl = false

    /**
     * Called after an opening handshake has been performed and the given websocket is ready to be
     * written on.
     *
     * @param handshakedata The handshake of the websocket instance
     */
    override fun onOpen(handshakedata: ServerHandshake?) {
        println("Opened connection to Lunar Client $username $id")
    }

    /**
     * Callback for string messages received from the remote host
     *
     * @param message The UTF-8 decoded message that was received.
     * @see .onMessage
     */
    override fun onMessage(message: String?) {
        println("Received message from Lunar Client $username $id")
        this.lIlIlIlIlIIlIIlIIllIIIIIl(JsonParser().parse(message).asJsonObject)
    }

    /**
     * Called after the websocket connection has been closed.
     *
     * @param code   The codes can be looked up here: [CloseFrame]
     * @param reason Additional information string
     * @param remote Returns whether or not the closing of the connection was initiated by the remote
     * host.
     */
    override fun onClose(code: Int, reason: String?, remote: Boolean) {
        println("Closed connection to Lunar Client $username $id")
    }

    /**
     * Called when errors occurs. If an error causes the websocket connection to fail [ ][.onClose] will be called additionally.<br></br> This method will be called
     * primarily because of IO or protocol errors.<br></br> If the given exception is an RuntimeException
     * that probably means that you encountered a bug.<br></br>
     *
     * @param ex The exception causing this error
     */
    override fun onError(ex: Exception?) {
        println("Error with Lunar Client $username $id")
    }

    private fun lIlIlIlIlIIlIIlIIllIIIIIl(json: JsonObject) {
        val asString: String = json.get("packetType").getAsString()
        println(asString)
        val abstractAuthenticatorPacket: AbstractAuthenticatorPacket = when (asString) {
            "SPacketEncryptionRequest" -> {
                SPacketEncryptionRequest()
            }
            "SPacketAuthenticatedRequest" -> {
                SPacketAuthenticatedRequest()
            }
            else -> {
                return
            }
        }
        try {
            abstractAuthenticatorPacket.lIllIlIIIlIIIIIIIlllIlIll(json)
            abstractAuthenticatorPacket.lIlIlIlIlIIlIIlIIllIIIIIl(this)
        } catch (ex: IOException) {
            Bukkit.getLogger().log(LogRecord(Level.INFO, "Error from: $abstractAuthenticatorPacket"))
            ex.printStackTrace()
        }
    }

    fun lIlIlIlIlIIlIIlIIllIIIIIl(abstractAuthenticatorPacket: AbstractAuthenticatorPacket) {
        if (!this.isOpen) {
            return
        }
        val jsonObject = JsonObject()
        jsonObject.addProperty("packetType", abstractAuthenticatorPacket.lIlIlIlIlIIlIIlIIllIIIIIl())
        try {
            abstractAuthenticatorPacket.IlllIIIIIIlllIlIIlllIlIIl(jsonObject)
            this.send(GSON.toJson(jsonObject).toByteArray(StandardCharsets.UTF_8))
        } catch (ex: IOException) {
            ex.printStackTrace()
        }
    }
    fun lIlIlIlIlIIlIIlIIllIIIIIl(sPacketAuthenticatedRequest: SPacketAuthenticatedRequest) {
        this.IlllIIIIIIlllIlIIlllIlIIl = true
        this.close()
        this.lIlIlIlIlIIlIIlIIllIIIIIl?.accept(sPacketAuthenticatedRequest.IlllIIIIIIlllIlIIlllIlIIl())
    }

    fun lIlIlIlIlIIlIIlIIllIIIIIl(sPacketEncryptionRequest: SPacketEncryptionRequest) {
        val `bridge$createNewSharedKey`: SecretKey = CryptManagerImplementation.`bridge$createNewSharedKey`()
        val illlIIIIIIlllIlIIlllIlIIl = sPacketEncryptionRequest.IlllIIIIIIlllIlIIlllIlIIl()
        val `bridge$getServerIdHash`: ByteArray = CryptManagerImplementation.`bridge$getServerIdHash`("", illlIIIIIIlllIlIIlllIlIIl, `bridge$createNewSharedKey`)
        val string = BigInteger(`bridge$getServerIdHash`).toString(16)
        try {

            YggdrasilAuthenticationService(Proxy.NO_PROXY, UUID.randomUUID().toString()).createMinecraftSessionService()
                .joinServer(
                    //Ref.getMinecraft().`bridge$getSession`().`bridge$getProfile`(),
                    //Ref.getMinecraft().`bridge$getSession`().`bridge$getToken`(),
                    null,
                    null,
                    string
                )


        } catch (ex: AuthenticationUnavailableException) {
            Bukkit.getLogger().info("Auth Unavailable $ex")
            return
        } catch (ex2: InvalidCredentialsException) {
            Bukkit.getLogger().info("Invalid Creds: $ex2")
            return
        } catch (ex3: AuthenticationException) {
            Bukkit.getLogger().info("Auth Error: $ex3")
            return
        } catch (ex4: NullPointerException) {
            this.close()
        }
        this.lIlIlIlIlIIlIIlIIllIIIIIl(
            CPacketEncryptionResponse(
                `bridge$createNewSharedKey`,
                illlIIIIIIlllIlIIlllIlIIl,
                sPacketEncryptionRequest.lIllIlIIIlIIIIIIIlllIlIll()
            )
        )
    }


}