package net.fredrikmeyer.lox;

public class AstPrinter implements Expr.Visitor<String> {

    public String visit(Expr expr) {
        return expr.accept(this);
    }

    @Override
    public String visitBinaryExpr(Expr.Binary expr) {
        return parenthize(expr.operator.lexeme(), expr.left, expr.right);
    }

    private String parenthize(String name, Expr... exprs) {
        StringBuilder builder = new StringBuilder();

        builder.append("(");
        builder.append(name);

        for (Expr expr: exprs) {
            builder.append(" ");
            builder.append(expr.accept(this));
        }

        builder.append(")");

        return builder.toString();
    }

    @Override
    public String visitGroupingExpr(Expr.Grouping expr) {
        return parenthize("group", expr.expression);
    }

    @Override
    public String visitLiteralExpr(Expr.Literal expr) {
        if (expr.value == null) return "nil";
        return expr.value.toString();
    }

    @Override
    public String visitUnaryExpr(Expr.Unary expr) {
        return parenthize(expr.operator.lexeme(), expr.right);
    }
}
