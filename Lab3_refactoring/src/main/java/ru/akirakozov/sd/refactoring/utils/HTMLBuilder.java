package ru.akirakozov.sd.refactoring.utils;

import ru.akirakozov.sd.refactoring.Product;

import java.util.List;

public class HTMLBuilder {
    private static final String HTML_BEGINNING = "<html><body>";
    private static final String HTML_ENDING = "</body></html>";
    StringBuilder text;
    boolean isDirectText = false;
    boolean alreadyBuild = false;

    public HTMLBuilder() {
        text = new StringBuilder(HTML_BEGINNING);
    }

    public HTMLBuilder addText(String s) {
        text.append(s);
        return this;
    }

    public HTMLBuilder addNumber(Number s) {
        text.append(s);
        return this;
    }

    public HTMLBuilder addProductTable(List<Product> productList) {
        for (Product product : productList) {
            text.append(product.getName())
                    .append("\t")
                    .append(product.getPrice())
                    .append("</br>");
        }
        return this;
    }

    public HTMLBuilder setDirectText(String s) {
        isDirectText = true;
        text = new StringBuilder(s);
        return this;
    }

    public String build() {
        if (alreadyBuild) {
            throw new RuntimeException("HTML build twice");
        }
        if (!isDirectText) {
            text.append(HTML_ENDING);
        }
        alreadyBuild = true;
        return text.toString();
    }
}
