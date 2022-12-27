package pro.darkgod.modules

import pro.darkgod.application.services.PersonUseCase

class ServicesModule private constructor(
  val personService: pro.darkgod.application.ports.inbound.PersonService
) {
  companion object {
    fun create(databaseModule: DatabaseModule): ServicesModule {
      return ServicesModule(
        personService = PersonUseCase(databaseModule.personRepository),
      )
    }
  }
}
