import io.ktor.http.*
import org.example.TodoItem
import org.junit.jupiter.api.Assumptions.assumeTrue
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertAll
import kotlin.test.assertEquals

class DeleteTest : BaseTest() {

    @Test
    fun deleteTodoItem() {
        val todoItem = TodoItem(id = 1u, text = "buy bread", completed = false)
        assumeTrue(addToDoItem(todoItem).status == HttpStatusCode.Created)

        val reply = deleteToDoItem(todoItem.id)
        assertAll("check reply",
            { assertEquals(HttpStatusCode.NoContent, reply.status) },
            { assertEquals(0, decode(getToDoItems()).size) })
    }

    @Test
    fun deleteNonExistingItem() {
        val reply = deleteToDoItem(10u)
        assertAll("check reply",
            { assertEquals(HttpStatusCode.NotFound, reply.status) })
    }

    @Test
    fun deleteLastTodoItem() {
        val todoItem = TodoItem(id = 18446744073709551615u, text = "buy bread", completed = false)
        assumeTrue(addToDoItem(todoItem).status == HttpStatusCode.Created)

        val reply = deleteToDoItem(todoItem.id)
        assertAll("check reply",
            { assertEquals(HttpStatusCode.NoContent, reply.status) },
            { assertEquals(0, decode(getToDoItems()).size) })
    }

    @Test
    fun deleteFirstTodoItem() {
        val todoItem = TodoItem(id = 0u, text = "buy bread", completed = false)
        assumeTrue(addToDoItem(todoItem).status == HttpStatusCode.Created)

        val reply = deleteToDoItem(todoItem.id)
        assertAll("check reply",
            { assertEquals(HttpStatusCode.NoContent, reply.status) },
            { assertEquals(0, decode(getToDoItems()).size) })
    }
}