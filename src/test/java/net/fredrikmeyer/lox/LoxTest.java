package net.fredrikmeyer.lox;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.*;

class LoxTest {

    @Test
    void main() throws IOException {
        var args = new String[]{};
        ByteArrayInputStream b = new ByteArrayInputStream(">".getBytes());
        System.setIn(b);

        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
        Lox.main(args);

        Assertions.assertEquals("> Token[type=GREATER, lexeme=>, literal=null, line=1]\n" + "Token[type=EOF, lexeme=, literal=null, line=1]\n" + "> ", outContent.toString());

        System.setOut(new PrintStream(new FileOutputStream(FileDescriptor.out)));
    }
}