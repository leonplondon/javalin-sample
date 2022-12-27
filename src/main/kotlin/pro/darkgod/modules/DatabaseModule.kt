package pro.darkgod.modules

import DatabaseConstants
import com.mongodb.ConnectionString
import com.mongodb.MongoClientSettings
import com.mongodb.WriteConcern
import com.mongodb.client.MongoClient
import com.mongodb.client.MongoDatabase
import org.bson.UuidRepresentation
import org.litote.kmongo.KMongo
import org.litote.kmongo.util.KMongoJacksonFeature
import pro.darkgod.adapters.mongo.PersonMongoRepository
import pro.darkgod.application.ports.outbound.PersonRepository

class DatabaseModule private constructor(
  val personRepository: PersonRepository
) {

  companion object {

    private val settings: MongoClientSettings by lazy { getClientSettings() }
    private val mongoClient: MongoClient by lazy { KMongo.createClient(settings) }

    private val database: MongoDatabase by lazy {
      mongoClient.getDatabase(DatabaseConstants.DATABASE)
    }

    private fun getClientSettings(): MongoClientSettings = MongoClientSettings.builder()
      .writeConcern(WriteConcern.MAJORITY)
      .uuidRepresentation(UuidRepresentation.STANDARD)
      .applyConnectionString(ConnectionString("mongodb://localhost:27017"))
      .applyToConnectionPoolSettings {
        it.minSize(5)
        it.maxSize(10)
      }
      .build()

    init {
      KMongoJacksonFeature.setUUIDRepresentation(UuidRepresentation.STANDARD)
    }

    fun create(): DatabaseModule {
      return DatabaseModule(
        personRepository = PersonMongoRepository(database)
      )
    }
  }
}
