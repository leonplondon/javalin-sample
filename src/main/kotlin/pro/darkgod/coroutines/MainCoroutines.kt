// package pro.darkgod.coroutines
//
// import kotlinx.coroutines.*
// import kotlinx.coroutines.slf4j.MDCContext
// import mu.KotlinLogging
// import java.util.*
// import kotlin.time.ExperimentalTime
// import kotlin.time.measureTime
//
// val logger = KotlinLogging.logger { }
//
// data class Settings(var settingsMap: Map<String, Boolean> = emptyMap()) {
//    fun test(value: String) {}
// }
//
// data class Person(val name: String, val lastName: String, val settings: Settings)
//
// suspend fun getPersonById(id: UUID): Person? {
//    val person = Person("dario", "pena", Settings())
//
//    coroutineScope {}
//
//    return if (person.settings.settingsMap.isNotEmpty()) {
//        person.copy(settings = Settings(mapOf("x" to true)))
//    } else {
//        person
//    }
// }
//
// fun Int.secToMillis(): Long {
//    return this * 1_000L
// }
//
// suspend fun suspendFunction(character: String, times: Int = 1): String {
//    delay(times.secToMillis())
//
//    logger.info { "suspendFunction" }
//
//    if (times == 3) throw Error("Magic number error") else {
//        val value = character.repeat(times)
//        logger.info { value }
//        return value
//    }
// }
//
// @OptIn(DelicateCoroutinesApi::class)
// suspend fun testContext() {
//    val context = newFixedThreadPoolContext(
//        nThreads = 10, "custom-thread-pool"
//    )
//
//    withContext(context) {
//        val mdcContext = coroutineContext[MDCContext]
//        logger.info { "Test context inside custom thread pool" }
//    }
// }
//
// val coroutineExceptionHandler = CoroutineExceptionHandler { ctx, throwable ->
//    println("Hit to coroutineException handler; ctx: $ctx; error: $throwable")
//    println(throwable.printStackTrace())
// }
//
// @OptIn(ExperimentalTime::class)
// fun main2() = runBlocking(context = coroutineExceptionHandler) {
//    val supervisorJob = SupervisorJob()
//
//    val scope = CoroutineScope(
//        supervisorJob + Dispatchers.IO + CoroutineName("customScopeWithSupervisorJob")
//                + coroutineExceptionHandler
//    )
//
//    scope.launch { createCoroutineSupervised(1, RuntimeException("first coroutine supervised")) }
//    scope.launch {
//        createCoroutineSupervised(3)
//        print("finished")
//    }
//
//    withContext(MDCContext(mapOf("test" to true.toString(), "callId" to UUID.randomUUID().toString())) + Dispatchers.IO) {
//        val resultA = async { suspendFunction(character = "*", times = 5) }
//        val resultB = async { suspendFunction(character = "-", times = 3) }
//
//        logger.info { "This is only a test" }
//        // resultB.cancel()
//
//        launch(Dispatchers.Unconfined) {
//            logger.warn { "Unconfined dispatcher" }
//        }
//
//        coroutineScope {
//            async(Dispatchers.Default) {
//                logger.debug { "Info dispatcher" }
//
//                launch(Dispatchers.IO) {
//                    logger.debug { "IO dispatcher" }
//
//                    testContext()
//                }
//            }
//
//            println()
//        }
//
//        try {
//            val time = measureTime {
//                val a = resultA.await()
//                val b = resultB.await()
//                logger.info { "Consolidated -> $a _ $b" }
//            }
//
//            logger.trace { "Execution time combined${time.inWholeSeconds}" }
//        } catch (error: Throwable) {
//            logger.error { error }
//        }
//    }
//
//    coroutineScope {
//        val resultA = async { suspendFunction(character = "*", times = 5) }
//        val resultB = async { suspendFunction(character = "-", times = 3) }
//        // resultB.cancel()
//
//        try {
//            val time = measureTime {
//                val a = resultA.await()
//                val b = resultB.await()
//            }
//
//        } catch (error: Throwable) {
//        }
//    }
//
//    delay(15.secToMillis())
// }
//
// private suspend fun createCoroutineSupervised(delaySecs: Int, error: Exception? = null) {
//    delay(delaySecs.secToMillis())
//    error?.let { throw it }
// }
