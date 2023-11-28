package com.nextsolutions.keyysafe.common.util

import java.util.Random
import java.util.UUID

object IdentifierGenerator {
    fun generateUUID() : String {
        return UUID.randomUUID().toString()
    }
    fun generateNumberId() : Int {
        return Random().nextInt() + Random().nextLong().toInt()
    }
}