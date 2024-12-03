import io.ktor.client.plugins.websocket.*
import io.ktor.client.statement.*
import io.qameta.allure.Step
import kotlinx.coroutines.runBlocking
import mu.KotlinLogging
import org.example.TodoItem
import org.junit.jupiter.api.TestInstance
import kotlinx.serialization.json.Json
import org.example.RestClient
import org.example.WebSocketClient
import org.junit.jupiter.api.AfterEach

private val logger = KotlinLogging.logger {}

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
abstract class BaseTest {

    private val url = "http://localhost"

    private val port = 8080
    private val path = "/todos"

    private val webSocketPort = 4242

    private val restClient : RestClient = RestClient("$url:$port$path")
    val webSocketClient = WebSocketClient(host = "localhost", port = webSocketPort)

    @Step
    fun addToDoItem(item: TodoItem) : HttpResponse {
        logger.info { "addTodoItem start" }
        return restClient.post(item = item)
    }

    @Step
    fun deleteToDoItem(id: ULong) : HttpResponse {
        logger.info { "delete item $id" }
        return restClient.delete(id = id)
    }

    @Step
    fun getToDoItems(offset: Int? = null, limit: Int? = null) : HttpResponse {
        logger.info { "getAll items start" }
        return restClient.get(offset = offset, limit = limit)
    }

    @Step
    fun updateToDoItem(id: ULong, updatedItem: TodoItem) : HttpResponse {
        logger.info { "updateItem started" }
        return restClient.put(id = id, updatedItem = updatedItem)
    }

    @AfterEach
    fun dataCleanup() {
        logger.info { "dataCleanup started" }
        runBlocking {
            Json.decodeFromString<List<TodoItem>>(getToDoItems().bodyAsText())
                .forEach { item ->
                deleteToDoItem(item.id)
            }
        }
    }


}

fun decode(response: HttpResponse) : List<TodoItem> {
    return runBlocking {
        Json.decodeFromString<List<TodoItem>>(response.bodyAsText())
    }
}