package com.emp.exception;


public class IllegalStateMachineException extends Exception {
    private static final long serialVersionUID = 1L;

    public IllegalStateMachineException(String message) {
        super(message);
    }
    
    public IllegalStateMachineException(String message, Throwable e) {
        super(message, e);
    }
}