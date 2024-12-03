import io.ktor.http.*
import io.qameta.allure.Issue
import org.example.TodoItem
import org.junit.jupiter.api.Assumptions.assumeTrue
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertAll
import kotlin.test.assertEquals

class UpdateTest : BaseTest() {

    private val testItem = TodoItem(id = 1u, text = "first item", completed = false)

    @Test
    fun updateItem() {
        assumeTrue(addToDoItem(testItem).status == HttpStatusCode.Created)
        val updatedItem = TodoItem(id = 2u, text = "first item update", completed = true)

        val reply = updateToDoItem(id = testItem.id, updatedItem = updatedItem)
        val resultItem = decode(getToDoItems())

        assertAll("check reply",
            { assertEquals(HttpStatusCode.OK, reply.status) },
            { assertEquals(1, resultItem.size)},
            { assertEquals(updatedItem, resultItem.first()) } )
    }

    @Test
    fun updateNotExistingItem() {
        val updatedItem = TodoItem(id = 2u, text = "first item update", completed = true)

        val reply = updateToDoItem(id = 10u, updatedItem = updatedItem)
        val resultItem = decode(getToDoItems())

        assertAll("check reply",
            { assertEquals(HttpStatusCode.NotFound, reply.status) },
            { assertEquals(0, resultItem.size)})
    }

    @Test
    @Issue("Probably bug should be raised")
    fun updateToExistingId() {
        assumeTrue(addToDoItem(testItem).status == HttpStatusCode.Created)
        val updatedItem = TodoItem(id = 2u, text = "first item update", completed = true)
        assumeTrue(addToDoItem(updatedItem).status == HttpStatusCode.Created)

        val reply = updateToDoItem(id = 1u, updatedItem = updatedItem)
        val resultItem = decode(getToDoItems())

        assertAll("check reply",
            { assertEquals(HttpStatusCode.NotFound, reply.status) },
            { assertEquals(0, resultItem.size)})
    }
}