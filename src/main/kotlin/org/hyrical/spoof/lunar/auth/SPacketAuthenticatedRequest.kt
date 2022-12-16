package org.hyrical.spoof.lunar.auth

import com.google.gson.JsonObject
import org.hyrical.spoof.lunar.AuthenticatorWebSocket

class SPacketAuthenticatedRequest : AbstractAuthenticatorPacket() {
    var lIlIlIlIlIIlIIlIIllIIIIIl: String? = null

    override fun lIlIlIlIlIIlIIlIIllIIIIIl(p0: AuthenticatorWebSocket) {
        p0.lIlIlIlIlIIlIIlIIllIIIIIl(this)
    }

    override fun lIlIlIlIlIIlIIlIIllIIIIIl(): String {
        return "SPacketAuthenticatedRequest"
    }

    override fun IlllIIIIIIlllIlIIlllIlIIl(jsonObject: JsonObject) {
        lIlIlIlIlIIlIIlIIllIIIIIl = jsonObject["jwtKey"].asString
    }

    override fun lIllIlIIIlIIIIIIIlllIlIll(p0: JsonObject) {}
    fun IlllIIIIIIlllIlIIlllIlIIl(): String? {
        return lIlIlIlIlIIlIIlIIllIIIIIl
    }
}