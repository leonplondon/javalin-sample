package pro.darkgod.application.services

import pro.darkgod.application.domain.Either
import pro.darkgod.application.domain.Person
import pro.darkgod.application.ports.inbound.PersonService
import pro.darkgod.application.ports.outbound.PersonRepository
import java.util.UUID

class PersonUseCase(
  private val personRepository: PersonRepository
) : PersonService {
  override fun getById(id: UUID): Either<Error, Person> {
    return personRepository.getById(id)?.let { Either.Success(it) }
      ?: Either.Failure(Error("Person not found"))
  }

  override fun create(person: Person): Either<Error, Person> {
    return try {
      Either.Success(personRepository.save(person))
    } catch (error: Exception) {
      Either.Failure(Error(error.message))
    }
  }
}
