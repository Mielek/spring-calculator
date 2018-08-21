package com.mielowski.calculator.expression;

import com.mielowski.calculator.core.Expression;
import com.mielowski.calculator.expression.ExpressionFactoryException;
import com.mielowski.calculator.expression.ExpressionParser;
import com.mielowski.calculator.expression.ExpressionParserException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;


import static org.assertj.core.api.Assertions.*;

public class ExpressionHandlerTest {

    private ExpressionHandler handler = new ExpressionHandler();

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
    @CsvSource({"sqr(1), 1","sqr(-1), 1","sqr(2), 4"})
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
    @CsvSource({"({[0]}), 0", "(1+1)*(1+1), 4", "2*(2+2*[2+2*{2+2}]), 44", "(((1+1)*2+1)*2+1)*2, 22"})
    public void parseExpressionWithParenthesis(String expression, String result){
        assertParsedExpressionWithResult(expression, result);
    }

    @DisplayName("Parse expression:")
    @ParameterizedTest(name = "[{index}] {0} = {1}")
    @CsvSource({"1+1*1+1, 3", "2*2+2*2+2*2+2, 14", "1+1*2+1*2+1*2, 7", "2*2*2*2+1+1+1+1, 20"})
    public void parseExpression(String expression, String result){
        assertParsedExpressionWithResult(expression, result);
    }

    @DisplayName("Parse expression with spaces:")
    @ParameterizedTest(name = "[{index}] {0} = {1}")
    @CsvSource({"1+1         *1+  1, 3", "  2   *2+   2*2 +2*2+2    , 14", "1+1*2+  1*2+1*2, 7", "2*2*2*2    +1+1+1+1, 20"})
    public void parseExpressionWithSpaces(String expression, String result){
        assertParsedExpressionWithResult(expression, result);
    }

    @Test
    public void parseEmptyString(){
        assertThatThrownBy(()->new ExpressionParser("").parse()).isExactlyInstanceOf(ExpressionParserException.class);
    }

    @Test
    public void parseWithWrongCharString(){
        assertThatThrownBy(()->new ExpressionParser("1+1+\u03A9+1").parse()).isExactlyInstanceOf(ExpressionParserException.class);
    }

    @Test
    public void noEndingParenthesis(){
        assertThatThrownBy(() -> new ExpressionParser("2*(2+2").parse()).isExactlyInstanceOf(ExpressionFactoryException.class);
    }

    @Test
    public void noStartingParenthesis(){
        assertThatThrownBy(() -> new ExpressionParser("2*2+2)").parse()).isExactlyInstanceOf(ExpressionParserException.class);
    }

    @Test
    public void wrongExpression(){
        assertThatThrownBy(() -> new ExpressionParser("2*(2+2)(8+8)").parse()).isExactlyInstanceOf(ExpressionParserException.class);
    }

    @Test
    public void mixedParenthesis(){
        assertThatThrownBy(() -> new ExpressionParser("({[0)]}").parse()).isExactlyInstanceOf(ExpressionFactoryException.class);
    }

    private void assertParsedExpressionWithResult(String expression, String result) {
        ExpressionCommand command = new ExpressionCommand();
        command.setExpression(expression);
        Expression expressionResult = handler.handle(command);

        assertThat(expressionResult.result()).isEqualByComparingTo(result);
    }

}
