package tokenizer

import TokensList
import token.Token

class Tokenizer(private val input: String) {
    var state: State = CharState()
    private val tokens: ArrayList<Token> = ArrayList()

    fun processChar(c: Char) {
        state.process(this, c)
    }

    internal fun addToken(token: Token) {
        tokens.add(token)
    }

    fun processInput() {
        for (element in input) {
            processChar(element)
        }
        state.endProcessing(this)
        state = TerminalState()
    }

    fun getTokensList(): TokensList {
        return TokensList(tokens)
    }
}
