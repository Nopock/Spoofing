package org.hyrical.spoof.lunar.auth

import com.google.gson.JsonObject
import org.hyrical.spoof.lunar.AuthenticatorWebSocket
import org.hyrical.spoof.lunar.CryptManagerImplementation
import java.security.PublicKey
import java.util.*

class SPacketEncryptionRequest : AbstractAuthenticatorPacket() {
    var lIlIlIlIlIIlIIlIIllIIIIIl: PublicKey? = null
    lateinit var IlllIIIIIIlllIlIIlllIlIIl: ByteArray

    override fun lIlIlIlIlIIlIIlIIllIIIIIl(p0: AuthenticatorWebSocket) {
        p0.lIlIlIlIlIIlIIlIIllIIIIIl(this)
    }

    override fun lIlIlIlIlIIlIIlIIllIIIIIl(): String {
        return "SPacketEncryptionRequest"
    }

    override fun IlllIIIIIIlllIlIIlllIlIIl(jsonObject: JsonObject) {}
    override fun lIllIlIIIlIIIIIIIlllIlIll(jsonObject: JsonObject) {
        val urlDecoder = Base64.getUrlDecoder()
        lIlIlIlIlIIlIIlIIllIIIIIl = CryptManagerImplementation.`bridge$decodePublicKey`(
            urlDecoder.decode(
                jsonObject["publicKey"].asString
            )
        )
        IlllIIIIIIlllIlIIlllIlIIl = urlDecoder.decode(jsonObject["randomBytes"].asString)
    }

    fun IlllIIIIIIlllIlIIlllIlIIl(): PublicKey? {
        return lIlIlIlIlIIlIIlIIllIIIIIl
    }

    fun lIllIlIIIlIIIIIIIlllIlIll(): ByteArray {
        return IlllIIIIIIlllIlIIlllIlIIl
    }
}