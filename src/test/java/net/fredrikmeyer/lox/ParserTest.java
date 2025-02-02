package net.fredrikmeyer.lox;

import net.fredrikmeyer.lox.Expr.Literal;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ParserTest {

    @Test
    void parse() {
        List<Token> tokens = new ArrayList<>();
        tokens.add(new Token(TokenType.NUMBER, "5", 5, 1));
        tokens.add(new Token(TokenType.EOF, "EOF", null, 1));
        Parser parser = new Parser(tokens);

        Expr parse = parser.parse();

        // Expect to be parsed correctly
        assertEquals(new Literal(5).value, ((Literal) parse).value);
    }

    @Test
    void test_parsing_parenthized_expression() {
        List<Token> tokens = new ArrayList<>();
        tokens.add(new Token(TokenType.LEFT_PAREN, "(", null, 1));
        tokens.add(new Token(TokenType.NUMBER, "5", null, 1));
        tokens.add(new Token(TokenType.PLUS, "+", null, 1));
        tokens.add(new Token(TokenType.NUMBER, "3", null, 1));
        tokens.add(new Token(TokenType.RIGHT_PAREN, ")", null, 1));
        tokens.add(new Token(TokenType.EOF, "EOF", null, 1));

        AstPrinter astPrinter = new AstPrinter();

        assertEquals("(group (+ 5 3))", astPrinter.visit(new Expr.Grouping(
            new Expr.Binary(new Expr.Literal(5), new Token(TokenType.PLUS, "+", "1", 1),
                new Expr.Literal(3))
        )));
    }
}