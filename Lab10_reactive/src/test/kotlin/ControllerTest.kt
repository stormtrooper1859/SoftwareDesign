import com.mongodb.rx.client.Success
import db.Currency
import db.Product
import db.ServiceDAO
import db.User
import org.junit.Assert.*
import org.junit.Test
import org.mockito.Mockito
import rx.Observable

class ControllerTest {

    @Test
    fun testAddUserSuccessfully() {
        val serviceDAOMock = Mockito.mock(ServiceDAO::class.java)
        val registerMockParams = User("alex", "Aleksandr", Currency.RUB)
        val registerMockResponse = Observable.just(Success.SUCCESS)
        Mockito.`when`(serviceDAOMock.registerNewUser(registerMockParams)).thenReturn(registerMockResponse)

        val res = Controller(serviceDAOMock).process(
            "/add_user",
            mapOf(
                "login" to listOf("alex"),
                "name" to listOf("Aleksandr"),
                "currency" to listOf("RUB"),
            )
        )

        assertEquals("Successfully added!", res.concatToBlockingString())
    }

    @Test
    fun testAddUserUnsuccessfully() {
        val serviceDAOMock = Mockito.mock(ServiceDAO::class.java)

        val res = Controller(serviceDAOMock).process(
            "/add_user",
            mapOf(
                "login" to listOf("alex"),
                "currency" to listOf("RUB"),
            )
        )

        assertEquals("Nothing", res.concatToBlockingString())
    }

    @Test
    fun testAddProductSuccessfully() {
        val serviceDAOMock = Mockito.mock(ServiceDAO::class.java)
        val getUserMockParams = "johan"
        val getUserMockResponse = Observable.just(User("johan", "Joe", Currency.EUR))
        Mockito.`when`(serviceDAOMock.getUserByLogin(getUserMockParams)).thenReturn(getUserMockResponse)
        val addProductMockParams = Product("car", 720.0, Currency.EUR)
        val addProductMockResponse = Observable.just(Success.SUCCESS)
        Mockito.`when`(serviceDAOMock.addNewProduct(addProductMockParams)).thenReturn(addProductMockResponse)

        val res = Controller(serviceDAOMock).process(
            "/add_item",
            mapOf(
                "name" to listOf("car"),
                "price" to listOf("720"),
                "login" to listOf("johan"),
            )
        )

        assertEquals("Successfully added!", res.concatToBlockingString())
    }

    @Test
    fun testAddProductUnsuccessfully() {
        val serviceDAOMock = Mockito.mock(ServiceDAO::class.java)

        val res = Controller(serviceDAOMock).process(
            "/add_item",
            mapOf(
                "name" to listOf("car"),
            )
        )

        assertEquals("Nothing", res.concatToBlockingString())
    }

    @Test
    fun testGetProducts() {
        val serviceDAOMock = Mockito.mock(ServiceDAO::class.java)
        val getUserMockParams = "vld"
        val getUserMockResponse = Observable.just(User("vld", "Volodya", Currency.RUB))
        Mockito.`when`(serviceDAOMock.getUserByLogin(getUserMockParams)).thenReturn(getUserMockResponse)
        val getProductsMockResponse = Observable.just(
            Product("carrot", 123.0, Currency.RUB),
            Product("potato", 1.2, Currency.EUR),
            Product("phone", 537.0, Currency.USD),
        )
        Mockito.`when`(serviceDAOMock.getProducts()).thenReturn(getProductsMockResponse)

        val res = Controller(serviceDAOMock).process(
            "/list_items",
            mapOf(
                "login" to listOf("vld"),
            )
        )

        val expectedResponse = """carrot 123,00 RUB
potato 106,19 RUB
phone 40006,50 RUB
"""

        assertEquals(expectedResponse, res.concatToBlockingString())
    }

    @Test
    fun testUnsupportedRoute() {
        val serviceDAOMock = Mockito.mock(ServiceDAO::class.java)

        val res = Controller(serviceDAOMock).process(
            "/remove_item",
            mapOf(
                "login" to listOf("vld"),
            )
        )

        assertEquals("Command not found", res.concatToBlockingString())
    }

}

fun Observable<String>.concatToBlockingString(): String =
    this.toList().toBlocking().first().joinToString(separator = "")
