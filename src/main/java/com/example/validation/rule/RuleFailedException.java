package com.example.validation.rule;

import org.springframework.validation.BindingResult;

public class RuleFailedException extends RuntimeException {
    private String message;
    private BindingResult bindingResult;

    public RuleFailedException(String message, BindingResult bindingResult){
        super(message);
        this.message = message;
        this.bindingResult = bindingResult;
    }

    public final BindingResult getBindingResult(){
        return this.bindingResult;
    }
}
