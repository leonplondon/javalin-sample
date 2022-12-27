package pro.darkgod.adapters.mongo

import DatabaseConstants
import com.mongodb.client.MongoDatabase
import mu.KLogging
import org.litote.kmongo.eq
import org.litote.kmongo.findOne
import org.litote.kmongo.getCollection
import pro.darkgod.application.domain.Person
import pro.darkgod.application.ports.outbound.PersonRepository
import java.util.UUID

class PersonMongoRepository(database: MongoDatabase) : PersonRepository {

  private val collection = database.getCollection<Person>(DatabaseConstants.PERSON_COLLECTION)

  override fun getById(id: UUID): Person? {
    logger.info { "Going to read a person with id=$id" }

    return collection.findOne {
      Person::id eq id
    }
  }

  override fun save(person: Person): Person {
    logger.info { "Going to create a new person $person" }
    collection.insertOne(person)
    return person
  }

  companion object : KLogging() {}
}
