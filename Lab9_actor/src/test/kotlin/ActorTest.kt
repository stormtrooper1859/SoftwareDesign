import actor.MasterActor
import actor.Query
import actor.Response
import akka.actor.ActorSystem
import akka.actor.Props
import akka.pattern.Patterns
import net.URLReaderStub
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import scala.concurrent.Await
import java.time.Duration


class ActorTest {

    @Test
    fun testAllOK() {
        actorWrapper(URLReaderStub()) {
            Assertions.assertEquals(it.list.size, 15)
        }
    }

    @Test
    fun testOneTimeout() {
        testTimeouts(listOf("yahoo"), 10)
    }

    @Test
    fun testTwoTimeout() {
        testTimeouts(listOf("yandex", "bing"), 5)
    }

    private fun testTimeouts(timeoutServices: List<String>, expectedSize: Int) {
        actorWrapper(URLReaderStub(timeoutServices)) { response ->
            Assertions.assertEquals(response.list.size, expectedSize)
            response.list.forEach {
                for (name in timeoutServices) {
                    Assertions.assertFalse(it.title.startsWith(name))
                }
            }
        }
    }

    private fun actorWrapper(stub: URLReaderStub, responseChecker: (resp: Response) -> Unit) {
        val actorSystem = ActorSystem.create("TestSystem")
        val actor = actorSystem.actorOf(
            Props.create(MasterActor::class.java, stub, Duration.ofMillis(1500)), "master"
        )
        try {
            val response = Await.result(
                Patterns.ask(actor, Query("java"), 3000),
                scala.concurrent.duration.Duration.Inf()
            ) as Response

            responseChecker(response)
        } catch (e: Exception) {
            Assertions.assertTrue(false)
        }
    }

}