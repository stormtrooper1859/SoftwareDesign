package db

import rx.Observable

interface ServiceDAO {
    fun getUserByLogin(login: String) : Observable<User>

    fun getProductsForUser(user: User): Observable<Product>

    fun registerNewUser(user: User): Observable<Boolean>

    fun addNewProduct(product: Product): Observable<Boolean>
}
