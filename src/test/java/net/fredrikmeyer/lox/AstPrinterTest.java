package net.fredrikmeyer.lox;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AstPrinterTest {

    @Test
    void visitBinaryExpr_for_simple_expression() {
        // Test print binary expr
        AstPrinter printer = new AstPrinter();
        Expr.Binary expr = new Expr.Binary(
                new Expr.Literal(1),
                new Token(TokenType.PLUS, "+", null, 1),
                new Expr.Literal(2)
        );
        String result = printer.visitBinaryExpr(expr);
        assertEquals("(+ 1 2)", result);
    }

    @Test
    void visit_more_complicated_expression() {
        AstPrinter printer = new AstPrinter();
        Expr.Binary expr = new Expr.Binary(
                new Expr.Binary(
                        new Expr.Literal(1),
                        new Token(TokenType.PLUS, "+", null, 1),
                        new Expr.Literal(2)
                ),
                new Token(TokenType.STAR, "*", null, 1),
                new Expr.Binary(
                        new Expr.Literal(4),
                        new Token(TokenType.MINUS, "-", null, 1),
                        new Expr.Literal(3)
                )
        );
        String result = printer.visitBinaryExpr(expr);
        assertEquals("(* (+ 1 2) (- 4 3))", result);
    }

    @Test
    void visit_expression_involving_grouping() {
        Expr.Binary expr = new Expr.Binary(
                new Expr.Grouping(
                        new Expr.Binary(
                                new Expr.Literal(1),
                                new Token(TokenType.PLUS, "+", null, 1),
                                new Expr.Literal(2)
                        )
                ),
                new Token(TokenType.STAR, "*", null, 1),
                new Expr.Binary(
                        new Expr.Literal(4),
                        new Token(TokenType.MINUS, "-", null, 1),
                        new Expr.Literal(3)
                )
        );
        AstPrinter printer = new AstPrinter();
        String result = printer.visit(expr);
        assertEquals("(* (group (+ 1 2)) (- 4 3))", result);
    }
}