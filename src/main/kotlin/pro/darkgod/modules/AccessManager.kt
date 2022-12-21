package pro.darkgod.modules

import io.javalin.core.JavalinConfig
import mu.KLogging

object AccessManager: KLogging() {

    fun register(config: JavalinConfig) {
        config.accessManager { handler, ctx, routeRoles ->
            // TODO: Implement access manager for app
            logger.warn { "Executing access manager $routeRoles" }
            handler.handle(ctx)
        }
    }
}
