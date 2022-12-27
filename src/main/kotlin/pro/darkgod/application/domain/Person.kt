package pro.darkgod.application.domain

import org.bson.codecs.pojo.annotations.BsonId
import java.util.UUID

data class Person(
  @BsonId val id: UUID,
  val name: String,
)
