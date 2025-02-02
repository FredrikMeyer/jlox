package net.fredrikmeyer.lox;

import net.fredrikmeyer.lox.Expr.Binary;
import net.fredrikmeyer.lox.Expr.Grouping;
import net.fredrikmeyer.lox.Expr.Literal;
import net.fredrikmeyer.lox.Expr.Unary;

public class Interpreter implements Expr.Visitor<Object> {

    void interpret(Expr expression) {
        try {
            var value = evaluate(expression);
            System.out.println(stringify(value));
        } catch (RuntimeException error) {
            Lox.runtimeError(error);
        }
    }

    private String stringify(Object object) {
        if (object == null) {
            return "nil";
        }
        if (object instanceof Double) {
            String text = object.toString();
            if (text.endsWith(".0")) {
                text = text.substring(0, text.length() - 2);
            }
            return text;
        }

        return object.toString();
    }

    @Override
    public Object visitBinaryExpr(Binary expr) {
        var left = evaluate(expr.left);
        var right = evaluate(expr.right);

        switch (expr.operator.type()) {
            case MINUS:
                return (double) left - (double) right;
            case SLASH:
                return (double) left / (double) right;
            case STAR:
                return (double) left * (double) right;
            case PLUS:
                if (left instanceof Double && right instanceof Double) {
                    return (double) left + (double) right;
                }

                if (left instanceof String && right instanceof String) {
                    return left + (String) right;
                }
                break;

            case GREATER:
                return (double) left > (double) right;
            case GREATER_EQUAL:
                return (double) left >= (double) right;
            case LESS:
                return (double) left < (double) right;
            case LESS_EQUAL:
                return (double) left <= (double) right;
            case BANG_EQUAL:
                return !isEqual(left, right);
            case EQUAL_EQUAL:
                return isEqual(left, right);
        }

        // Unreachable?
        return null;
    }

    private boolean isEqual(Object a, Object b) {
        if (a == null && b == null) {
            return true;
        }
        if (a == null) {
            return false;
        }
        return a.equals(b);
    }

    @Override
    public Object visitGroupingExpr(Grouping expr) {
        return evaluate(expr.expression);
    }

    private Object evaluate(Expr expression) {
        // På dette punktet vet vi ikke hva slags type expression er
        // visitor pattern gjør at vi slipper switch/etc
        return expression.accept(this);
    }

    @Override
    public Object visitLiteralExpr(Literal expr) {
        return expr.value;
    }

    @Override
    public Object visitUnaryExpr(Unary expr) {
        Object right = evaluate(expr.right);

        return switch (expr.operator.type()) {
            case MINUS -> -(double) right;
            case BANG -> !isTruthy(right);
            default -> null;
        };
    }

    private boolean isTruthy(Object object) {
        if (object == null) {
            return false;
        }
        if (object instanceof Boolean) {
            return (boolean) object;
        }
        return true;
    }
}
