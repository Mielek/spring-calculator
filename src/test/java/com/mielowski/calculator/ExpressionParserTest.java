package com.mielowski.calculator;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;


import static org.assertj.core.api.Assertions.*;

public class ExpressionParserTest {

    @Test
    public void parseEmptyString(){
        ExpressionParser parser = new ExpressionParser("");
        assertThatThrownBy(parser::parse);
    }

    @Test
    public void parseWithWrongCharString(){
        ExpressionParser parser = new ExpressionParser("1+1+\u03A9+1");
        assertThatThrownBy(parser::parse);
    }

    @DisplayName("Parse positive constant expressions:")
    @ParameterizedTest
    @ValueSource(strings = {"1", "5.0", "123.123"})
    public void parsePositiveConstantExpressionWith(String expression){
        assertParsedExpressionWithResult(expression, expression);
    }

    @DisplayName("Parse negative constant expressions:")
    @ParameterizedTest
    @ValueSource(strings = {"-1", "-5.0", "-123.123"})
    public void parseNegativeConstantExpressionWith(String expression){
        assertParsedExpressionWithResult(expression, expression);
    }

    @DisplayName("Parse simple addition expressions:")
    @ParameterizedTest(name = "[{index}] {0} = {1}")
    @CsvSource({"1+1, 2","1+-1, 0","-1+1, 0", "2+2, 4", "32+68, 100"})
    public void parseSimpleAdditionExpression(String expression, String result) {
        assertParsedExpressionWithResult(expression, result);
    }

    @DisplayName("Parse simple subtraction expression:")
    @ParameterizedTest(name = "[{index}] {0} = {1}")
    @CsvSource({"1-1, 0","1--1, 2","-1-1, -2", "-2-2, -4"})
    public void parseSimpleSubtractionExpression(String expression, String result) {
        assertParsedExpressionWithResult(expression, result);
    }

    @DisplayName("Parse simple multiplication expression:")
    @ParameterizedTest(name = "[{index}] {0} = {1}")
    @CsvSource({"1*1, 1","1*-1, -1","2*1, 2", "-2*2, -4"})
    public void parseSimpleMultiplicationExpression(String expression, String result) {
        assertParsedExpressionWithResult(expression, result);
    }

    @DisplayName("Parse simple division expression:")
    @ParameterizedTest(name = "[{index}] {0} = {1}")
    @CsvSource({"1/1, 1","1/-1, -1","2/1, 2", "-2/2, -1"})
    public void parseSimpleDivisionExpression(String expression, String result) {
        assertParsedExpressionWithResult(expression, result);
    }

    @DisplayName("Parse square expression:")
    @ParameterizedTest(name = "[{index}] {0} = {1}")
    @CsvSource({"sqrt(1), 1","sqrt(-1), 1","sqrt(2), 4"})
    public void parseSquareFunctionExpression(String expression, String result) {
        assertParsedExpressionWithResult(expression, result);
    }

    @DisplayName("Parse square root expression:")
    @ParameterizedTest(name = "[{index}] {0} = {1}")
    @CsvSource({"root(1), 1","root(4), 2", "root(9), 3"})
    public void parseSquareRootFunctionExpression(String expression, String result) {
        assertParsedExpressionWithResult(expression, result);
    }

    @DisplayName("Parse expression with parenthesis:")
    @ParameterizedTest(name = "[{index}] {0} = {1}")
    @CsvSource({"(1+1)*(1+1), 4", "2*(2+2*[2+2*{2+2}]), 44", "(((1+1)*2+1)*2+1)*2, 22"})
    public void parseExpressionWithParenthesis(String expression, String result){
        assertParsedExpressionWithResult(expression, result);
    }

    @DisplayName("Parse expression:")
    @ParameterizedTest(name = "[{index}] {0} = {1}")
    @CsvSource({"1+1*1+1, 3", "2*2+2*2+2*2+2, 14", "1+1*2+1*2+1*2, 7", "2*2*2*2+1+1+1+1, 20"})
    public void parseExpression(String expression, String result){
        assertParsedExpressionWithResult(expression, result);
    }

    @Test
    public void noEndingParenthesis(){
        ExpressionParser parser = new ExpressionParser("2*(2+2");
        assertThatThrownBy(parser::parse);
    }

    @Test
    public void noStartingParenthesis(){
        ExpressionParser parser = new ExpressionParser("2*2+2)");
        assertThatThrownBy(parser::parse);
    }

    @Test
    public void wrongExpression(){
        ExpressionParser parser = new ExpressionParser("2*(2+2)(8+8)");
        assertThatThrownBy(parser::parse);
    }

    private void assertParsedExpressionWithResult(String expression, String result) {
        ExpressionParser parser = new ExpressionParser(expression);
        Expression parsedExpression = parser.parse();

        assertThat(parsedExpression.result()).isEqualByComparingTo(result);
    }

}