package pro.darkgod

import ConfigKey
import io.javalin.http.Context
import java.util.Properties
import java.util.UUID

fun Context.pathParamUUID(param: String): UUID {
  val uuid = this.pathParam(param)
  return UUID.fromString(uuid)
}

object Config {
  private val systemEnvironment by lazy { System.getenv() }

  val environment: Map<String, String> by lazy {
    if (isContainer()) {
      systemEnvironment
    } else {
      mergeEnvironment()
    }
  }

  private fun isContainer(): Boolean = systemEnvironment
    .getOrDefault(ConfigKey.CONTAINER, false.toString())
    .toBoolean()

  private fun mergeEnvironment(): Map<String, String> {
    val overrides = Properties()
      .mapKeys { entry -> entry.key as String }
      .mapValues { entry -> entry.value as String }
    return systemEnvironment.plus(overrides)
  }

  inline operator fun <reified T> get(key: String, default: T): T {
    val rawValue = environment[key]
    return coerceValue<T>(rawValue) ?: default
  }

  inline operator fun <reified T> get(key: String): T {
    val rawValue = environment[key]
    return coerceValue<T>(rawValue) ?: throw RuntimeException()
  }

  inline fun <reified T> coerceValue(rawValue: String?): T? {
    return when (T::class) {
      Boolean::class -> rawValue.toBoolean()
      String::class -> rawValue
      else -> null
    }?.let { it as T }
  }
}
