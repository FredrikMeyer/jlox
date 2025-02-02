package net.fredrikmeyer.tools;

import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;

public class GenerateAst {
    public static void main(String[] args) {
        if (args.length != 1) {
            System.err.println("Usage: generate_ast output-dir");
            System.exit(64);
        }

        String outputDir = args[0];

        defineAst(outputDir, "Expr", Arrays.asList(
                "Binary : Expr left, Token operator, Expr right",
                "Grouping : Expr expression",
                "Literal : Object value",
                "Unary : Token operator, Expr right"
        ));
    }

    private static void defineAst(String outputdir, String baseName, List<String> types) {
        String path = outputdir + "/" + baseName + ".java";

        try (PrintWriter writer = new PrintWriter(path, StandardCharsets.UTF_8)) {
            writer.println("/* Generated code. To regenerate, run: ");
            writer.println(
                    " mvn compile exec:java -Dexec.mainClass=net.fredrikmeyer.tools.GenerateAst -Dexec.args=src/main/java/net/fredrikmeyer/lox\n */");
            writer.println("package net.fredrikmeyer.lox;");
            writer.println();
            writer.println("import java.util.List;");

            writer.println("abstract class " + baseName + "{");

            defineVisitor(writer, baseName, types);

            for (String type : types) {
                String className = type.split(":")[0].trim();
                String fields = type.split(":")[1].trim();
                defineType(writer, baseName, className, fields);
            }

            writer.println();
            writer.println("    abstract <R> R accept(Visitor<R> visitor);");
            writer.println("}");
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    private static void defineVisitor(PrintWriter writer, String baseName, List<String> types) {
        writer.println("    interface Visitor<R> {");

        for (String type : types) {
            String typeName = type.split(":")[0].trim();
            writer.println("      R visit" + typeName + baseName + "(" + typeName + " " + baseName.toLowerCase() + ");");
        }
        writer.println("    }");
    }

    private static void defineType(PrintWriter writer, String baseName, String className, String fieldList) {
        writer.println("public static class " + className + " extends " + baseName + " {");

        writer.println("    " + className + "(" + fieldList + ") {");

        String[] fields = fieldList.split(", ");
        for (String field : fields) {
            String name = field.split(" ")[1].trim();
            writer.println("        this." + name + " = " + name + ";");
        }

        writer.println("    }");

        writer.println();
        writer.println("    @Override");
        writer.println("    <R> R accept(Visitor<R> visitor) {");
        writer.println("      return visitor.visit" + className + baseName + "(this);");
        writer.println("    }");

        for (String field : fields) {
            writer.println("    final " + field + ";");
        }
        writer.println("     }");
    }
}
