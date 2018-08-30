package com.mielowski.calculator;

import com.mielowski.calculator.core.CommandGateway;
import com.mielowski.calculator.core.Expression;
import com.mielowski.calculator.expression.ExpressionCommand;
import com.mielowski.calculator.expression.ExpressionParserException;
import com.mielowski.calculator.expression.factory.ExpressionFactoryException;
import com.mielowski.calculator.integrate.IntegrateCommand;
import com.mielowski.calculator.integrate.IntegrateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Controller
@SessionAttributes("history")
public class CalculatorController {

    @Autowired
    private CommandGateway gateway;

    @GetMapping("/calculator")
    public String getCalculator(HttpSession session, Model model) {
        return "calculator";
    }

    @ModelAttribute
    private void initializeNewSession(Model model) {
        if (!model.containsAttribute("history"))
            model.addAttribute("history", new ArrayList<Expression>());
    }

    @PostMapping("/calculator/evaluate")
    public String evalExpression(@ModelAttribute ExpressionCommand command, @SessionAttribute("history") List<Expression> history, Model model) {
        Instant now = Instant.now();
        Expression expression = gateway.execute(command);
        model.addAttribute("time", Duration.between(now, Instant.now()));
        history.add(expression);
        model.addAttribute("result", expression.result());
        return "evaluate";
    }

    @GetMapping("/calculator/history")
    public String getHistory() {
        return "history";
    }

    @PostMapping("/calculator/integral")
    public String evalIntegral(@ModelAttribute IntegrateCommand command, Model model) {
        Instant now = Instant.now();
        Double result = gateway.execute(command);
        model.addAttribute("time", Duration.between(now, Instant.now()));
        model.addAttribute("result", result);
        return "evaluate";
    }

    @ExceptionHandler({ExpressionParserException.class, ExpressionFactoryException.class, ArithmeticException.class, IntegrateException.class})
    public String handleError(Model model, Exception ex) {
        model.addAttribute("message", ex.getMessage());
        return "error";
    }

}
