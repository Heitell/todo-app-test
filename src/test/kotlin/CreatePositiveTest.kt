import io.ktor.http.*
import kotlinx.coroutines.runBlocking
import mu.KotlinLogging
import org.example.TodoItem
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.MethodSource
import java.util.stream.Stream
import kotlin.test.assertEquals

private val logger = KotlinLogging.logger {}

class CreatePositiveTest : BaseTest() {

    private fun testMethodSource(): Stream<Pair<TodoItem, HttpStatusCode>> {
        return Stream.of(
            Pair(TodoItem(id = 1u, text = "buy bread", completed = false), HttpStatusCode.Created),
            Pair(TodoItem(id = 0u, text = "zero index", completed = false), HttpStatusCode.Created),
            Pair(TodoItem(id = 2u, text = "", completed = false), HttpStatusCode.Created),
            Pair(TodoItem(id = 18446744073709551615u, text = "buy bread", completed = false), HttpStatusCode.Created),
            Pair(TodoItem(id = 3u, text = "buy bread", completed = true), HttpStatusCode.Created)
        )
    }

    @ParameterizedTest
    @MethodSource("testMethodSource")
    fun createTodoItem(testData: Pair<TodoItem, HttpStatusCode>) {
        val response = addToDoItem(testData.first)

        assertEquals(response.status, testData.second)
    }
}