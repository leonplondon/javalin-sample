package pro.darkgod

import ConfigKey
import io.javalin.Javalin
import io.javalin.apibuilder.ApiBuilder.path
import pro.darkgod.modules.*

fun main() {
  val servicesModule = ServicesModule.create()
  val metricsModule = MetricsModule.create()

  val app = Javalin
    .create { config ->
      // config.enableDevLogging()
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

