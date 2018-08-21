package com.mielowski.calculator;

import com.mielowski.calculator.core.CommandGateway;
import com.mielowski.calculator.core.Expression;
import com.mielowski.calculator.expression.ExpressionCommand;
import com.mielowski.calculator.expression.ExpressionFactoryException;
import com.mielowski.calculator.expression.ExpressionParserException;
import com.mielowski.calculator.integrate.IntegrateCommand;
import com.mielowski.calculator.integrate.IntegrateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

@Controller
public class CalculatorController {

    @Autowired
    private CommandGateway gateway;

    @GetMapping("/calculator")
    public String getCalculator(HttpSession session, Model model) {
        initializeNewSession(session, model);
        return "calculator";
    }

    private void initializeNewSession(HttpSession session, Model model) {
        if(session.isNew()) {
            session.setAttribute("history", new ArrayList<>());
        }
    }

    @PostMapping("/calculator/evaluate")
    public String evalExpression(@ModelAttribute ExpressionCommand command, HttpSession session, Model model){
        initializeNewSession(session, model);
        Expression expression = gateway.execute(command);
        List<Expression> history = (List<Expression>) session.getAttribute("history");
        history.add(expression);
        model.addAttribute("result", expression.result());
        return "evaluate";
    }

    @GetMapping("/calculator/history")
    public String getHistory(HttpSession session, Model model){
        initializeNewSession(session, model);
        model.addAttribute("history", session.getAttribute("history"));
        return "history";
    }

    @PostMapping("/calculator/integral")
    public String evalIntegral(@ModelAttribute IntegrateCommand command, HttpSession session, Model model){
        initializeNewSession(session, model);
        Double result = gateway.execute(command);
        model.addAttribute("result", result);
        return "evaluate";
    }

    @ExceptionHandler({ExpressionParserException.class, ExpressionFactoryException.class, ArithmeticException.class, IntegrateException.class})
    public String handleError(Model model, Exception ex) {
        model.addAttribute("message", ex.getMessage());
        return "error";
    }

}
