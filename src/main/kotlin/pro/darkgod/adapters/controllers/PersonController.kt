package pro.darkgod.adapters.controllers

import Constants
import io.javalin.apibuilder.ApiBuilder.get
import io.javalin.apibuilder.ApiBuilder.path
import io.javalin.apibuilder.ApiBuilder.post
import io.javalin.http.Context
import io.javalin.http.InternalServerErrorResponse
import io.javalin.http.NotFoundResponse
import io.javalin.plugin.openapi.dsl.document
import io.javalin.plugin.openapi.dsl.documented
import pro.darkgod.application.domain.Either
import pro.darkgod.application.domain.Person
import pro.darkgod.application.ports.inbound.PersonService
import pro.darkgod.pathParamUUID
import java.util.UUID

class PersonController(
  private val personService: PersonService
) : Controller {

  private fun getPersonById(ctx: Context) {
    val id = ctx.pathParamUUID(Constants.ID_PARAM)

    val person = when (val it = personService.getById(id)) {
      is Either.Failure -> { throw NotFoundResponse() }
      is Either.Success -> it.value
    }

    ctx.json(person.asSuccessResponse())
  }

  private fun createPerson(ctx: Context) {
    val personDto = ctx.bodyAsClass<Person>()

    val response = when (val it = personService.create(personDto)) {
      is Either.Failure -> { throw InternalServerErrorResponse() }
      is Either.Success -> it.value
    }

    ctx.json(response.asSuccessResponse())
  }

  override fun routes() {
    path("/persons") {
      get("/{id}", documented(getPersonByIdDoc, ::getPersonById))
      post("/", documented(createPersonDoc, ::createPerson))
    }
  }

  companion object {

    private val getPersonByIdDoc = document()
      .json<ApiResponse<Person>>("200")
      .pathParam<UUID>(Constants.ID_PARAM)

    private val createPersonDoc = document()
      .json<ApiResponse<Person>>("200")
      .body<Person>()
  }
}
