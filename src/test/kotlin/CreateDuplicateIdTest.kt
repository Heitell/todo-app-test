import io.ktor.http.*
import org.example.TodoItem
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class CreateDuplicateIdTest : BaseTest() {

    private val testItem = TodoItem(id = 1u, text = "first item", completed = false)

    @BeforeEach
    fun prepareTestData() {
        addToDoItem(item = testItem)
    }

    @Test
    fun createDuplicateItem() {
        val response = addToDoItem(testItem)
        assertEquals(response.status, HttpStatusCode.BadRequest)
    }
}