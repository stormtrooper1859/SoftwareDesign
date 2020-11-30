package visitor

import token.*

interface TokenVisitor {
    fun visit(token: Token)
}
