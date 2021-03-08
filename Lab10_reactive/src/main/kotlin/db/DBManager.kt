package db

import com.mongodb.client.model.Filters
import com.mongodb.rx.client.MongoDatabase
import rx.Observable

class DBManager(private val db: MongoDatabase) : ServiceDAO {
    override fun getUserByLogin(login: String): Observable<User> =
        db.getCollection("users")
            .find(Filters.eq("login", login))
            .toObservable()
            .map(::User)

    override fun getProductsForUser(user: User): Observable<Product> =
        db.getCollection("products")
            .find()
            .toObservable()
            .map(::Product)
            .map {
                Product(
                    it.name,
                    Currency.convertPrice(it.currency, user.currency, it.price),
                    user.currency
                )
            }

    override fun registerNewUser(user: User): Observable<Boolean> {
        return db.getCollection("users")
            .insertOne(user.toDocument())
            .map {
                true
            }
    }

    override fun addNewProduct(product: Product): Observable<Boolean> {
        return db.getCollection("products")
            .insertOne(product.toDocument())
            .map {
                true
            }
    }
}
