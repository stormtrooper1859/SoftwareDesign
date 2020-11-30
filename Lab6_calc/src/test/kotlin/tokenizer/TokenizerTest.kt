package tokenizer

import org.junit.Assert.assertEquals
import org.junit.Test
import visitor.PrintVisitor
import java.lang.RuntimeException

class TokenizerTest {
    @Test
    fun testFunctionality1() {
        val expression = "  1+(3+2)* 3452 - 6/6"
        val tokenizer = Tokenizer(expression);
        tokenizer.processInput()
        val tokens = tokenizer.getTokensList()

        val expected = "NUMBER(1) PLUS LEFT NUMBER(3) PLUS NUMBER(2) RIGHT MUL NUMBER(3452) MINUS NUMBER(6) DIV NUMBER(6)"

        val printVisitor = PrintVisitor()
        tokens.iterate(printVisitor)
        assertEquals(expected, printVisitor.getResult())
    }

    @Test(expected = RuntimeException::class)
    fun testUnexpectedSymbol() {
        val expression = "  1,+(3+2)* 3452 - 6/6"
        val tokenizer = Tokenizer(expression);
        tokenizer.processInput()
    }
}
