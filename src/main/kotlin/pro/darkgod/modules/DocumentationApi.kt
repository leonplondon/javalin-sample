package pro.darkgod.modules

import Constants
import io.javalin.core.JavalinConfig
import io.javalin.plugin.openapi.DefaultDocumentation
import io.javalin.plugin.openapi.OpenApiOptions
import io.javalin.plugin.openapi.OpenApiPlugin
import io.javalin.plugin.openapi.dsl.OpenApiDocumentation
import io.javalin.plugin.openapi.ui.ReDocOptions
import io.javalin.plugin.openapi.ui.SwaggerOptions
import io.swagger.v3.oas.models.info.Contact
import io.swagger.v3.oas.models.info.Info
import pro.darkgod.adapters.controllers.ApiResponse
import java.util.UUID

object DocumentationApi {

  private fun getOpenApiOptions(): OpenApiOptions = Info()
    .version("v1.0")
    .description("Description")
    .contact(Contact().email("dario_pena@epam.com"))
    .let { info ->
      OpenApiOptions(info)
        .path("/docs")
        .defaultDocumentation(object : DefaultDocumentation {
          override fun apply(documentation: OpenApiDocumentation) {
            documentation.json<String>("200")
            documentation.header<String>("Authorization") { it.required = true }
            documentation.header<UUID>(Constants.X_CLIENT_ID) { header ->
              header.description = "App's client id"
              header.required = true
            }

            ((400..409).toList() + (500..504).toList())
              .forEach {
                // println(it)
                documentation.json<ApiResponse<String>>(it.toString())
              }
          }
        })
        .reDoc(ReDocOptions("/redoc"))
        .swagger(SwaggerOptions("/swagger-docs"))
        .disableCaching()
    }

  fun register(config: JavalinConfig) {
    config.registerPlugin(OpenApiPlugin(getOpenApiOptions()))
  }
}
