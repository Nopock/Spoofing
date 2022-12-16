package org.hyrical.spoof.lunar

import java.security.PublicKey
import javax.crypto.SecretKey

object CryptManagerImplementation  {
    fun `bridge$encryptData`(var1: PublicKey?, var2: ByteArray?): ByteArray {
        return CryptManager.encryptData(var1!!, var2)!!
    }

    fun `bridge$decodePublicKey`(var1: ByteArray?): PublicKey {
        return CryptManager.decodePublicKey(var1)!!
    }

    fun `bridge$createNewSharedKey`(): SecretKey {
        return CryptManager.createNewSharedKey()
    }

    fun `bridge$getServerIdHash`(var1: String?, var2: PublicKey?, var3: SecretKey?): ByteArray {
        return CryptManager.getServerIdHash(var1!!, var2!!, var3!!)!!
    }
}