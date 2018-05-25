package com.example.validation.rule;

import org.springframework.validation.Errors;

import java.util.Map;

public interface RuleEngine<T> {

    void addRule(Rule<T> rule, boolean continueOnError);
    void execute(T target, Map<Object, Object> context);


}
