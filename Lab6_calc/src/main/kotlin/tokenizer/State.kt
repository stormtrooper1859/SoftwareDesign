package tokenizer

import token.*

abstract class State {
    abstract fun process(tokenizer: Tokenizer, c: Char)
    abstract fun endProcessing(tokenizer: Tokenizer)
}

class NumberState : State() {
    var value = 0

    override fun process(tokenizer: Tokenizer, c: Char) {
        if (c.isDigit()) {
            value *= 10
            value += c.toInt() - '0'.toInt()
        } else {
            tokenizer.addToken(NumberToken(value))
            tokenizer.state = CharState()
            tokenizer.processChar(c)
        }
    }

    override fun endProcessing(tokenizer: Tokenizer) {
        tokenizer.addToken(NumberToken(value))
    }
}

class CharState : State() {
    override fun process(tokenizer: Tokenizer, c: Char) {
        if (c.isWhitespace()) {
            return
        }
        if (c.isDigit()) {
            tokenizer.state = NumberState()
            tokenizer.processChar(c)
            return
        }
        when (c) {
            '(' -> tokenizer.addToken(OpenBracketToken())
            ')' -> tokenizer.addToken(CloseBracketToken())
            '+' -> tokenizer.addToken(PlusToken())
            '-' -> tokenizer.addToken(MinusToken())
            '*' -> tokenizer.addToken(MultiplyToken())
            '/' -> tokenizer.addToken(DivideToken())
            else -> {
                throw RuntimeException()
            }
        }
    }

    override fun endProcessing(tokenizer: Tokenizer) {
    }
}

class TerminalState : State() {
    override fun process(tokenizer: Tokenizer, c: Char) {
        throw RuntimeException()
    }

    override fun endProcessing(tokenizer: Tokenizer) {
        throw RuntimeException()
    }
}
