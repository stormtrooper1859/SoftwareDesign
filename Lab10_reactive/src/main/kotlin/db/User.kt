package db

import org.bson.Document

data class User(val login: String, val name: String, val currency: Currency) : Documentable {
    constructor(doc: Document) : this(
        doc.getString("login"),
        doc.getString("name"),
        Currency.valueOf(doc.getString("currency"))
    )

    override fun toDocument() = Document(
        mapOf(
            "login" to login,
            "name" to name,
            "currency" to currency.name
        )
    )
}
