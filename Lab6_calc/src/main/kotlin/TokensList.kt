import token.Token
import visitor.TokenVisitor

class TokensList(private val list: List<Token>) {
    fun iterate(visitor: TokenVisitor) {
        for (token in list) {
            visitor.visit(token)
        }
    }
}
