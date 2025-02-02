package net.fredrikmeyer.lox;

import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertIterableEquals;

class ScannerTest {

    @Test
    void scanTokens() {
        String source = "<=";
        Scanner scanner = new Scanner(source);

        var res = scanner.scanTokens().stream().map(Token::type).collect(Collectors.toList());
        var expected = List.of(TokenType.LESS_EQUAL, TokenType.EOF);

        assertIterableEquals(res, expected);
    }

    @Test
    void readString() {
        String source = "\"hei\"";
        var scanner = new Scanner(source);

        var res = scanner.scanTokens();

        assertEquals(new Token(TokenType.STRING, "\"hei\"", "hei",1 ), res.get(0));
    }

    @Test
    void readNumber() {
        String source = "3.14+2";

        var res = new Scanner(source).scanTokens();

        assertEquals(4, (long) res.size());
        assertEquals(new Token(TokenType.NUMBER, "3.14", 3.14, 1), res.get(0));
        assertEquals(new Token(TokenType.PLUS, "+", null, 1), res.get(1));
        assertEquals(new Token(TokenType.NUMBER, "2", 2.0, 1), res.get(2));
    }

    @Test
    void readSomeKeywords() {
        String source = "true and false or this";
        var res = new Scanner((source)).scanTokens();

        assertEquals(6, res.size());
        assertEquals(new Token(TokenType.TRUE, "true", null, 1), res.get(0));
        assertEquals(new Token(TokenType.AND, "and", null, 1), res.get(1));
        assertEquals(new Token(TokenType.FALSE, "false", null, 1), res.get(2));
        assertEquals(new Token(TokenType.OR, "or", null, 1), res.get(3));
        assertEquals(new Token(TokenType.THIS, "this", null, 1), res.get(4));
    }
}