package pro.darkgod

import ConfigKey
import io.javalin.Javalin
import io.javalin.apibuilder.ApiBuilder.path
import pro.darkgod.modules.AccessManager
import pro.darkgod.modules.ControllerModule
import pro.darkgod.modules.DatabaseModule
import pro.darkgod.modules.DocumentationApi
import pro.darkgod.modules.MetricsModule
import pro.darkgod.modules.ServicesModule

fun main() {
  val databaseModule = DatabaseModule.create()
  val servicesModule = ServicesModule.create(databaseModule)
  val metricsModule = MetricsModule.create()

  val app = Javalin
    .create { config ->
      metricsModule.register(config)
      DocumentationApi.register(config)
      AccessManager.register(config)
    }

  app.routes {
    path("/api") {
      ControllerModule.create(metricsModule, servicesModule)
    }
  }

  val port: Int = Config[ConfigKey.PORT, 8080]
  app.start(port)
}
