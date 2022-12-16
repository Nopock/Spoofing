package org.hyrical.spoof.lunar.auth

import com.google.gson.JsonObject
import org.hyrical.spoof.lunar.AuthenticatorWebSocket
import java.io.IOException

abstract class AbstractAuthenticatorPacket {

    @Throws(IOException::class)
    abstract fun IlllIIIIIIlllIlIIlllIlIIl(p0: JsonObject)
    @Throws(IOException::class)
    abstract fun lIllIlIIIlIIIIIIIlllIlIll(p0: JsonObject)
    abstract fun lIlIlIlIlIIlIIlIIllIIIIIl(p0: AuthenticatorWebSocket)
    abstract fun lIlIlIlIlIIlIIlIIllIIIIIl(): String
}