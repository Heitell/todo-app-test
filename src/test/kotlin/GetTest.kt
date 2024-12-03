import io.ktor.client.statement.*
import io.ktor.http.*
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.json.Json
import mu.KotlinLogging
import org.example.TodoItem
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertAll
import kotlin.test.assertContains
import kotlin.test.assertEquals

private val logger = KotlinLogging.logger {}

class GetTest : BaseTest() {

    private val todoItems = listOf(
        TodoItem(id = 1u, text = "first item", completed = false),
        TodoItem(id = 2u, text = "second item", completed = false),
        TodoItem(id = 3u, text = "third item", completed = false),
    )

    @BeforeEach
    fun prepareTestData() {
        todoItems.forEach {
            addToDoItem(item = it)
        }
    }

    @Test
    fun shouldReturnAllItems() {
        val reply = getToDoItems()

        assertAll("Check reply result",
            { assertEquals(HttpStatusCode.OK, reply.status) },
            { assertEquals(todoItems, decode(reply)) })
    }

    @Test
    fun shouldSkipFirstItem() {
        val reply = getToDoItems(offset = 1)
        val actualItems = decode(reply)

        val shrinkedList = todoItems.drop(1)

        assertAll("Check reply result",
            { assertEquals(HttpStatusCode.OK, reply.status) },
            { assertEquals(shrinkedList.size, actualItems.size) },
            { assertEquals(shrinkedList, actualItems) })
    }

    @Test
    fun shouldSkipAllItems() {
        val reply = getToDoItems(offset = 3)

        assertAll("Check reply result",
            { assertEquals(HttpStatusCode.OK, reply.status) },
            { assertEquals(0, decode(reply).size) })
    }

    @Test
    fun shouldLimitItems() {
        val reply = getToDoItems(limit = 2)
        val actualItems = decode(reply)

        val shrinkedList = todoItems.dropLast(1)

        assertAll("Check reply result",
            { assertEquals(HttpStatusCode.OK, reply.status) },
            { assertEquals(shrinkedList.size, actualItems.size) },
            { assertEquals(shrinkedList, actualItems) })
    }

    @Test
    fun shouldLimitAllItems() {
        val reply = getToDoItems(limit = 0)

        assertAll("Check reply result",
            { assertEquals(HttpStatusCode.OK, reply.status) },
            { assertEquals(0, decode(reply).size) })
    }

    @Test
    fun shouldLimitAndOffsetItems() {
        val reply = getToDoItems(offset = 1, limit = 1)
        val actualItems = decode(reply)

        val shrinkedList = todoItems.drop(1).dropLast(1)

        assertAll("Check reply result",
            { assertEquals(HttpStatusCode.OK, reply.status) },
            { assertEquals(shrinkedList.size, actualItems.size) },
            { assertEquals(shrinkedList, actualItems) })
    }
}