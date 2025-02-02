package net.fredrikmeyer.lox;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.*;

class LoxTest {

    @Test
    void main() throws IOException {
        var args = new String[]{};
        ByteArrayInputStream b = new ByteArrayInputStream("2 + 3".getBytes());
        System.setIn(b);

        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
        Lox.main(args);

        Assertions.assertEquals("""
            > (+ 2.0 3.0)
            Token[type=NUMBER, lexeme=2, literal=2.0, line=1]
            Token[type=PLUS, lexeme=+, literal=null, line=1]
            Token[type=NUMBER, lexeme=3, literal=3.0, line=1]
            Token[type=EOF, lexeme=, literal=null, line=1]
            >\s""", outContent.toString());

        System.setOut(new PrintStream(new FileOutputStream(FileDescriptor.out)));
    }
}