package com.example.validation.rule;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class RuleDescription<T> {

    private Rule<T> rule;
    private boolean continueOnError;

}
