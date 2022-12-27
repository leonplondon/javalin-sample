package pro.darkgod.adapters.controllers

import io.javalin.apibuilder.ApiBuilder.get
import io.javalin.apibuilder.ApiBuilder.path
import io.javalin.http.Context
import io.micrometer.core.instrument.simple.SimpleMeterRegistry
import io.micrometer.prometheus.PrometheusMeterRegistry
import io.prometheus.client.exporter.common.TextFormat

class MetricsController(
  private val prometheusMeterRegistry: PrometheusMeterRegistry,
  private val simpleMeterRegistry: SimpleMeterRegistry,
) : Controller {

  private fun smr(ctx: Context) {
    ctx.contentType(TextFormat.CONTENT_TYPE_004)
      .result(simpleMeterRegistry.metersAsString)
  }

  private fun prometheus(ctx: Context) {
    ctx.contentType(TextFormat.CONTENT_TYPE_004)
      .result(prometheusMeterRegistry.scrape())
  }

  override fun routes() {
    path("/") {
      get("/smr", ::smr)
      get("/prometheus", ::prometheus)
    }
  }

  companion object
}
