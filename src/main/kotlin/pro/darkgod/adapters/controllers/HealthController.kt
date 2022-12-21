package pro.darkgod.adapters.controllers

import io.javalin.apibuilder.ApiBuilder.get
import io.javalin.apibuilder.ApiBuilder.path
import io.javalin.http.Context
import io.javalin.plugin.openapi.dsl.document
import io.javalin.plugin.openapi.dsl.documented

class HealthController : Controller {
    override fun routes() {
        path("/health") {
            get(documented(echoDoc, Companion::echo))
        }
    }

    companion object {
        private val echoDoc = document()
            .operation {
                it.summary = "Just echoing everything tha comes"
                it.description = "None"
            }
            .json<String>("200")
            .queryParam<String>("name")

        private fun echo(ctx: Context) {
            ctx.json("Health controller hit")
        }
    }
}
