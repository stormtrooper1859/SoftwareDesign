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
            else -> Observable.just("Not found")
        }
    } catch (e: Exception) {
        Observable.just("An error has occurred during command processing")
    }

    private fun addUser(params: Map<String, List<String>>): Observable<String> {
        val login = params["login"]?.get(0)
        val name = params["name"]?.get(0) ?: ""
        val currency = params["currency"]?.get(0)?.let { Currency.valueOf(it) }

        if (login == null || currency == null) {
            return Observable.just("Nothing")
        }

        val user = User(login, name, currency)

        return serviceDAO.registerNewUser(user)
            .map { "Successfully added!" }
    }

    private fun addItem(params: Map<String, List<String>>): Observable<String> {
        val user: Observable<User> = params["login"]?.get(0)?.let {
            serviceDAO.getUserByLogin(it)
        } ?: return Observable.just("Nothing")

        val name = params["name"]?.get(0)
        val price = params["price"]?.get(0)?.toDouble()

        if (name == null || price == null) {
            return Observable.just("Nothing")
        }

        return user.flatMap {
            val product = Product(name, price, it.currency)
            serviceDAO.addNewProduct(product)
        }.map { "Successfully added!" }
    }

    private fun listItems(params: Map<String, List<String>>): Observable<String> {
        val user: Observable<User> = params["login"]?.get(0)?.let {
            serviceDAO.getUserByLogin(it)
        } ?: return Observable.just("Nothing")

        return user.flatMap {
            serviceDAO.getProductsForUser(it)
        }.map {
            "${it.name} ${"%.2f".format(it.price)} ${it.currency}\n"
        }
    }
}
