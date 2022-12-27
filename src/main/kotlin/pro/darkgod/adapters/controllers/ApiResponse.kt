package pro.darkgod.adapters.controllers

import java.time.ZonedDateTime
import java.util.UUID

data class ApiResponse<out T>(
  val data: T,
  val status: Int,
  val timestamp: ZonedDateTime = ZonedDateTime.now(),
  val traceId: UUID = UUID.randomUUID(),
)

fun <T> T.asSuccessResponse(): ApiResponse<T> = ApiResponse(this, 200)
