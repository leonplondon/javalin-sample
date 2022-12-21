package pro.darkgod.modules

import pro.darkgod.adapters.controllers.PersonService

class ServicesModule private constructor(val personService: PersonService) {

    companion object {
        fun create(): ServicesModule {
            val personService = object : PersonService {}
            return ServicesModule(
                personService = personService,
            )
        }
    }
}
