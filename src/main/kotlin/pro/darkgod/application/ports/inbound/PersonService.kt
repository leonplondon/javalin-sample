package pro.darkgod.application.ports.inbound

import pro.darkgod.application.domain.Either
import pro.darkgod.application.domain.Person
import java.util.UUID

interface PersonService {
  fun getById(id: UUID): Either<Error, Person>
  fun create(person: Person): Either<Error, Person>
}
