package org.hyrical.spoof.lunar.auth

import com.google.gson.JsonObject
import org.hyrical.spoof.lunar.AuthenticatorWebSocket
import org.hyrical.spoof.lunar.CryptManagerImplementation
import java.security.PublicKey
import java.util.*
import javax.crypto.SecretKey

class CPacketEncryptionResponse(secretKey: SecretKey, publicKey: PublicKey?, array: ByteArray?) :
    AbstractAuthenticatorPacket() {
    val lIlIlIlIlIIlIIlIIllIIIIIl: ByteArray
    val IlllIIIIIIlllIlIIlllIlIIl: ByteArray

    init {
        lIlIlIlIlIIlIIlIIllIIIIIl = CryptManagerImplementation.`bridge$encryptData`(publicKey, secretKey.encoded)
        IlllIIIIIIlllIlIIlllIlIIl = CryptManagerImplementation.`bridge$encryptData`(publicKey, array)
    }

    override fun lIlIlIlIlIIlIIlIIllIIIIIl(p0: AuthenticatorWebSocket) {}
    override fun lIlIlIlIlIIlIIlIIllIIIIIl(): String {
        return "CPacketEncryptionResponse"
    }

    override fun IlllIIIIIIlllIlIIlllIlIIl(p0: JsonObject) {
        val urlEncoder = Base64.getUrlEncoder()
        p0.addProperty("secretKey", String(urlEncoder.encode(lIlIlIlIlIIlIIlIIllIIIIIl)))
        p0.addProperty("publicKey", String(urlEncoder.encode(IlllIIIIIIlllIlIIlllIlIIl)))
    }

    override fun lIllIlIIIlIIIIIIIlllIlIll(p0: JsonObject) {}
}