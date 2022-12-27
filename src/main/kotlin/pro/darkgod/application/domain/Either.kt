package pro.darkgod.application.domain

sealed class Either<out E, out R> {
  data class Failure<E>(val error: E) : Either<E, Nothing>()
  data class Success<R>(val value: R) : Either<Nothing, R>()
}
