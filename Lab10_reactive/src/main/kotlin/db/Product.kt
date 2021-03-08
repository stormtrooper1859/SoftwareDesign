package db

import org.bson.Document

data class Product(val name: String, val price: Double, val currency: Currency) : Documentable {
    constructor(doc: Document) : this(
        doc.getString("name"),
        doc.getDouble("price"),
        Currency.valueOf(doc.getString("currency"))
    )

    override fun toDocument() = Document(
        mapOf(
            "name" to name,
            "price" to price,
            "currency" to currency.name
        )
    )
}
