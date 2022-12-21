package pro.darkgod.modules

import io.javalin.apibuilder.ApiBuilder.path
import pro.darkgod.adapters.controllers.HealthController
import pro.darkgod.adapters.controllers.HelloController
import pro.darkgod.adapters.controllers.MetricsController
import pro.darkgod.adapters.controllers.PersonController

object ControllerModule {

    fun create(metricsModule: MetricsModule, servicesModule: ServicesModule) {
        path("/v1") {
            HealthController().routes()
            HelloController(metricsModule).routes()
            PersonController(servicesModule.personService).routes()
            MetricsController(metricsModule.prometheusMeterRegistry, metricsModule.simpleMeterRegistry).routes()
        }
        path("/v2") {
            HealthController().routes()
        }
    }
}
