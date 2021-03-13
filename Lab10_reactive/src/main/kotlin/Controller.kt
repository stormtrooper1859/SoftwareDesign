import db.Currency
import db.Product
import db.ServiceDAO
import db.User
import rx.Observable

class Controller(private val serviceDAO: ServiceDAO) {
    fun process(name: String, params: Map<String, List<String>>): Observable<String> = try {
        when (name) {
            "/add_user" -> addUser(params)
            "/add_item" -> addItem(params)
            "/list_items" -> listItems(params)
            else -> Observable.just("Command not found")
        }
    } catch (e: Exception) {
        Observable.just("An error has occurred during command processing")
    }

    private fun addUser(params: Map<String, List<String>>): Observable<String> {
        val login = params.getParam("login")
        val name = params.getParam("name")
        val currency = params.getParam("currency")?.let { Currency.valueOf(it) }

        if (login == null || name == null || currency == null) {
            return nothing()
        }

        val user = User(login, name, currency)

        return serviceDAO.registerNewUser(user)
            .map { "Successfully added!" }
    }

    private fun addItem(params: Map<String, List<String>>): Observable<String> {
        val user = getUserByLoginParam(params)
        val name = params.getParam("name")
        val price = params.getParam("price")?.toDouble()

        if (user == null || name == null || price == null) {
            return nothing()
        }

        return user.flatMap {
            val product = Product(name, price, it.currency)
            serviceDAO.addNewProduct(product)
        }.map { "Successfully added!" }
    }

    private fun listItems(params: Map<String, List<String>>): Observable<String> {
        val user = getUserByLoginParam(params)

        return user?.flatMap { _user ->
            serviceDAO.getProducts()
                .map {
                    val userPrice = Currency.convertPrice(it.currency, _user.currency, it.price)
                    "${it.name} ${"%.2f".format(userPrice)} ${_user.currency}\n"
                }
        } ?: nothing()
    }

    private fun getUserByLoginParam(params: Map<String, List<String>>): Observable<User>? {
        return params.getParam("login")?.let {
            serviceDAO.getUserByLogin(it)
        }
    }

    private fun nothing() = Observable.just("Nothing")
}

fun Map<String, List<String>>.getParam(name: String): String? = this[name]?.get(0)
