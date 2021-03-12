package db

import org.bson.Document

interface DocumentConvertible {
    fun toDocument(): Document
}
