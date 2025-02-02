package net.fredrikmeyer.lox;

import org.junit.jupiter.api.Test;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.junit.jupiter.api.Assertions.assertTrue;

class TokenTest {

    @Test
    void testToString() {
        Token t = new Token(TokenType.AND, "xx", new Object(), 2);
        String expectedRegex = "Token\\[type=AND, lexeme=xx, literal=java\\.lang\\.Object@[0-9a-f]+, line=2\\]";

        Pattern pattern = Pattern.compile(expectedRegex);
        Matcher matcher = pattern.matcher(t.toString());
        assertTrue(matcher.matches());
    }
}