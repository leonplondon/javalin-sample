package pro.darkgod.adapters.controllers

import Constants
import pro.darkgod.application.domain.Person
import io.javalin.apibuilder.ApiBuilder.get
import io.javalin.apibuilder.ApiBuilder.path
import io.javalin.core.security.RouteRole
import io.javalin.http.Context
import io.javalin.plugin.openapi.dsl.document
import io.javalin.plugin.openapi.dsl.documented
import pro.darkgod.pathParamUUID
import java.util.*

interface PersonService

object Admin : RouteRole {
    fun allow(): Boolean {
        return true
    }
}

class PersonController(
    private val personService: PersonService
) : Controller {

    override fun routes() {
        path("/persons") {
            get("/") { ctx ->
                ctx.json("")
            }
            get("/{id}", documented(getPersonByIdDoc, Companion::getPersonById), Admin)
        }
    }

    companion object {
        private val getPersonByIdDoc = document()
            .json<ApiResponse<Person>>("200")
            .pathParam<UUID>(Constants.ID_PARAM)

        private fun getPersonById(ctx: Context) {
            val param = ctx.pathParamUUID(Constants.ID_PARAM)
            val person = Person(param, "name")
            ctx.json(person.asResponse())
        }
    }
}
