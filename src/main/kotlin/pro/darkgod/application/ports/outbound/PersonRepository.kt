package pro.darkgod.application.ports.outbound

import pro.darkgod.application.domain.Person
import java.util.UUID

interface PersonRepository {
  fun getById(id: UUID): Person?
  fun save(person: Person): Person
}
