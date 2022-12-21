package pro.darkgod.adapters.controllers

import java.time.ZonedDateTime
import java.util.*

data class ApiResponse<T>(
    val data: T,
    val status: Int,
    val timestamp: ZonedDateTime,
    val traceId: UUID,
)

fun <T> T.asResponse(): ApiResponse<T> = ApiResponse(this, 200, ZonedDateTime.now(), UUID.randomUUID())
