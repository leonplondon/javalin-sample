package pro.darkgod.adapters.controllers

import io.javalin.apibuilder.ApiBuilder.get
import io.javalin.http.Context
import io.javalin.plugin.openapi.dsl.document
import io.javalin.plugin.openapi.dsl.documented
import pro.darkgod.modules.MetricsModule

class HelloController(
    private val metricsModule: MetricsModule,
) : Controller {

    override fun routes() {
        get("/hello", documented(usersDocumentation) { ctx: Context ->
            metricsModule.compositeMeterRegistry.counter("health-checks").increment()
            ctx.json("Hello world!!")
        })
    }

    companion object {
        val usersDocumentation = document()
            .json("200", String::class.java)
    }
}
