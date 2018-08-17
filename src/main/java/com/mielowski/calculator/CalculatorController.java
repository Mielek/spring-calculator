package com.mielowski.calculator;

import com.mielowski.calculator.expressions.ExpressionFactoryException;
import com.mielowski.calculator.expressions.ExpressionParserException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

@Controller
public class CalculatorController {

    @Autowired
    private Calculator calculator;

    @GetMapping("/calculator")
    public String getCalculator(HttpSession session, Model model) {
        if(session.getAttribute("expression")==null) {
            model.addAttribute("expression", new Data());
            model.addAttribute("result", "");
        } else {
            model.addAttribute(session.getAttribute("expression"));
            model.addAttribute("result", session.getAttribute("result"));
        }
        return "calculator";
    }

    @PostMapping("/calculator")
    public String evalExpression(@ModelAttribute Data data, HttpSession session, Model model){
        Expression expression = calculator.evalExpression(data.expression);
        List<Expression> history = (List<Expression>) session.getAttribute("history");
        if(history==null)
            history = new ArrayList<>();
        history.add(expression);
        session.setAttribute("history", history);
        model.addAttribute("result", expression.result());
        return "evaluate";
    }

    @GetMapping("/calculator/history")
    public String getHistory(HttpSession session, Model model){
        model.addAttribute("history", session.getAttribute("history"));
        return "history";
    }

    @ExceptionHandler({ExpressionParserException.class, ExpressionFactoryException.class, ArithmeticException.class})
    public ModelAndView handleError(HttpServletRequest req, Exception ex) {
        ModelAndView mav = new ModelAndView();
        mav.addObject("result", ex.getMessage());
        mav.setViewName("evaluate");
        return mav;
    }


    static class Data {
        String expression;

        public String getExpression() {
            return expression;
        }

        public void setExpression(String expression) {
            this.expression = expression;
        }
    }

}
