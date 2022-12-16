package org.hyrical.spoof.lunar

import com.google.gson.GsonBuilder
import com.google.gson.JsonObject
import com.google.gson.JsonSyntaxException
import org.java_websocket.client.WebSocketClient
import org.java_websocket.drafts.Draft_6455
import org.java_websocket.handshake.ServerHandshake
import java.net.URI
import java.nio.ByteBuffer

class AssetsWebSocket(val map: Map<String, String>) :
    WebSocketClient(URI("wss://assetserver.lunarclientprod.com/connect"), Draft_6455(), map.toMutableMap(), 30000) {
    var llllIIlIIlIIlIIllIIlIIllI: String? = null
    var llIIlIlIIIllIlIlIlIIlIIll: Long = 0
    var llIIIlllIIlllIllllIlIllIl = false
    var lllllIllIllIllllIlIllllII = false

    val GSON = GsonBuilder().create()

    override fun onOpen(serverHandshake: ServerHandshake) {
        println("Opened connection to Lunar Client assets server")
        llIIlIlIIIllIlIlIlIIlIIll = System.currentTimeMillis()
    }

    override fun onClose(n: Int, s: String, b: Boolean) {
            var asBoolean = true
            var asInt = 15
            if (n == 1000) {
                try {
                    val jsonObject: JsonObject = GSON.fromJson(s, JsonObject::class.java)
                    if (jsonObject.isJsonObject) {
                        if (jsonObject.has("exponentialBackoff") && !jsonObject["exponentialBackoff"].isJsonNull) {
                            asBoolean = jsonObject.asBoolean
                        }
                        if (jsonObject.has("time") && !jsonObject["time"].isJsonNull) {
                            asInt = jsonObject["time"].asInt
                        }
                    }
                } catch (ex: JsonSyntaxException) {
                    if (s.equals("Server rebooting", ignoreCase = true)) {
                        asInt = 5
                        asBoolean = false
                    }
            } }
        llIIIlllIIlllIllllIlIllIl = false
        lllllIllIllIllllIlIllllII = false
    }

    override fun onMessage(byteBuffer: ByteBuffer) {
    }

    override fun onError(ex: Exception) {
    }

    override fun onMessage(s: String) {

    }
}