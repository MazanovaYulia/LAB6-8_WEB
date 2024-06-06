package com.example.demo.config

import org.springframework.security.crypto.password.PasswordEncoder
import java.security.MessageDigest

class SHA256PasswordEncoder : PasswordEncoder {

    override fun encode(rawPassword: CharSequence): String {
        val digest = MessageDigest.getInstance("SHA-256")
        val hash = digest.digest(rawPassword.toString().toByteArray(Charsets.UTF_8))
        return bytesToHex(hash)
    }

    override fun matches(rawPassword: CharSequence, encodedPassword: String): Boolean {
        println("matches")
        println(rawPassword)
        println(encode(rawPassword))
        println(encodedPassword)
        return encode(rawPassword) == encodedPassword
    }

    private fun bytesToHex(hash: ByteArray): String {
        val hexString = StringBuilder(2 * hash.size)
        for (byte in hash) {
            val hex = Integer.toHexString(0xff and byte.toInt())
            if (hex.length == 1) {
                hexString.append('0')
            }
            hexString.append(hex)
        }
        return hexString.toString()
    }
}