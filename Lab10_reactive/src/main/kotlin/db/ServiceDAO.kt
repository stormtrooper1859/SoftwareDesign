package db

import com.mongodb.rx.client.Success
import rx.Observable

interface ServiceDAO {
    fun getUserByLogin(login: String): Observable<User>

    fun getProducts(): Observable<Product>

    fun registerNewUser(user: User): Observable<Success>

    fun addNewProduct(product: Product): Observable<Success>
}
