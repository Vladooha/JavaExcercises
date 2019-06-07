package com.vladooha;

import net.objecthunter.exp4j.Expression;
import net.objecthunter.exp4j.ExpressionBuilder;

public class MathCounter {
    public Double calcIntegral(String function, Double leftBound, Double rightBound) {
        if (function == null || leftBound == null || rightBound == null) {
            return null;
        }

        double midValue = (rightBound + leftBound) / 2;
        double partLength = rightBound - leftBound;
        Expression e = new ExpressionBuilder(function)
                .variables("x")
                .build()
                .setVariable("x", midValue);
        double result = e.evaluate() * partLength;

        return result;
    }
}
