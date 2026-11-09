package com.mathwhiz.service;

import java.util.Map;

public class AnswerValidationException extends Exception {
    private final Map<Integer, String> errors;

    public AnswerValidationException(Map<Integer, String> errors) {
        super("One or more answers are invalid.");
        this.errors = Map.copyOf(errors);
    }

    public Map<Integer, String> getErrors() {
        return errors;
    }
}
