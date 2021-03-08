package db

import org.bson.Document

interface Documentable {
    fun toDocument(): Document
}
