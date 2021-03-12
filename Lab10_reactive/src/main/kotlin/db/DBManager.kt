package db

import com.mongodb.client.model.Filters
import com.mongodb.rx.client.MongoDatabase
import com.mongodb.rx.client.Success
import rx.Observable

class DBManager(private val db: MongoDatabase) : ServiceDAO {
    override fun getUserByLogin(login: String): Observable<User> =
        db.getCollection("users")
            .find(Filters.eq("login", login))
            .toObservable()
            .map(::User)

    override fun getProducts(): Observable<Product> =
        db.getCollection("products")
            .find()
            .toObservable()
            .map(::Product)

    override fun registerNewUser(user: User): Observable<Success> {
        return db.getCollection("users")
            .insertOne(user.toDocument())
            .asObservable()
    }

    override fun addNewProduct(product: Product): Observable<Success> {
        return db.getCollection("products")
            .insertOne(product.toDocument())
            .asObservable()
    }
}
