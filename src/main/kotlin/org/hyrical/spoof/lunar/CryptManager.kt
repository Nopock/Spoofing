package org.hyrical.spoof.lunar

import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger
import java.io.UnsupportedEncodingException
import java.security.*
import java.security.spec.InvalidKeySpecException
import java.security.spec.X509EncodedKeySpec
import javax.crypto.*
import javax.crypto.spec.IvParameterSpec
import javax.crypto.spec.SecretKeySpec


object CryptManager {
    private val field_180198_a: Logger = LogManager.getLogger()
    private const val __OBFID = "CL_00001483"

    /**
     * Generate a new shared secret AES key from a secure random source
     */
    fun createNewSharedKey(): SecretKey {
        return try {
            val var0: KeyGenerator = KeyGenerator.getInstance("AES")
            var0.init(128)
            var0.generateKey()
        } catch (var1: NoSuchAlgorithmException) {
            throw Error(var1)
        }
    }

    /**
     * Generates RSA KeyPair
     */
    fun generateKeyPair(): KeyPair? {
        return try {
            val var0 = KeyPairGenerator.getInstance("RSA")
            var0.initialize(1024)
            var0.generateKeyPair()
        } catch (var1: NoSuchAlgorithmException) {
            var1.printStackTrace()
            field_180198_a.error("Key pair generation failed!")
            null
        }
    }

    /**
     * Compute a serverId hash for use by sendSessionRequest()
     */
    fun getServerIdHash(p_75895_0_: String, p_75895_1_: PublicKey, p_75895_2_: SecretKey): ByteArray? {
        return try {
            digestOperation(
                "SHA-1",
                *arrayOf(p_75895_0_.toByteArray(charset("ISO_8859_1")), p_75895_2_.encoded, p_75895_1_.encoded)
            )
        } catch (var4: UnsupportedEncodingException) {
            var4.printStackTrace()
            null
        }
    }

    /**
     * Compute a message digest on arbitrary byte[] data
     */
    private fun digestOperation(p_75893_0_: String, vararg p_75893_1_: ByteArray): ByteArray? {
        return try {
            val var2 = MessageDigest.getInstance(p_75893_0_)
            val var3: Array<ByteArray> = p_75893_1_ as Array<ByteArray>
            val var4 = p_75893_1_.size
            for (var5 in 0 until var4) {
                val var6 = var3[var5]
                var2.update(var6)
            }
            var2.digest()
        } catch (var7: NoSuchAlgorithmException) {
            var7.printStackTrace()
            null
        }
    }

    /**
     * Create a new PublicKey from encoded X.509 data
     */
    fun decodePublicKey(p_75896_0_: ByteArray?): PublicKey? {
        try {
            val var1 = X509EncodedKeySpec(p_75896_0_)
            val var2: KeyFactory = KeyFactory.getInstance("RSA")
            return var2.generatePublic(var1)
        } catch (var3: NoSuchAlgorithmException) {
        } catch (var4: InvalidKeySpecException) {
        }
        field_180198_a.error("Public key reconstitute failed!")
        return null
    }

    /**
     * Decrypt shared secret AES key using RSA private key
     */
    fun decryptSharedKey(p_75887_0_: PrivateKey, p_75887_1_: ByteArray?): SecretKey {
        return SecretKeySpec(decryptData(p_75887_0_, p_75887_1_), "AES")
    }

    /**
     * Encrypt byte[] data with RSA public key
     */
    fun encryptData(p_75894_0_: Key, p_75894_1_: ByteArray?): ByteArray? {
        return cipherOperation(1, p_75894_0_, p_75894_1_)
    }

    /**
     * Decrypt byte[] data with RSA private key
     */
    fun decryptData(p_75889_0_: Key, p_75889_1_: ByteArray?): ByteArray? {
        return cipherOperation(2, p_75889_0_, p_75889_1_)
    }

    /**
     * Encrypt or decrypt byte[] data using the specified key
     */
    private fun cipherOperation(p_75885_0_: Int, p_75885_1_: Key, p_75885_2_: ByteArray?): ByteArray? {
        try {
            return createTheCipherInstance(p_75885_0_, p_75885_1_.algorithm, p_75885_1_)?.doFinal(p_75885_2_)
        } catch (var4: IllegalBlockSizeException) {
            var4.printStackTrace()
        } catch (var5: BadPaddingException) {
            var5.printStackTrace()
        }
        field_180198_a.error("Cipher data failed!")
        return null
    }

    /**
     * Creates the Cipher Instance.
     */
    private fun createTheCipherInstance(p_75886_0_: Int, p_75886_1_: String, p_75886_2_: Key): Cipher? {
        try {
            val var3: Cipher = Cipher.getInstance(p_75886_1_)
            var3.init(p_75886_0_, p_75886_2_)
            return var3
        } catch (var4: InvalidKeyException) {
            var4.printStackTrace()
        } catch (var5: NoSuchAlgorithmException) {
            var5.printStackTrace()
        } catch (var6: NoSuchPaddingException) {
            var6.printStackTrace()
        }
        field_180198_a.error("Cipher creation failed!")
        return null
    }

    fun func_151229_a(p_151229_0_: Int, p_151229_1_: Key): Cipher {
        return try {
            val var2: Cipher = Cipher.getInstance("AES/CFB8/NoPadding")
            var2.init(p_151229_0_, p_151229_1_, IvParameterSpec(p_151229_1_.getEncoded()))
            var2
        } catch (var3: GeneralSecurityException) {
            throw RuntimeException(var3)
        }
    }
}