package com.covid.correlation.exception;

public abstract class AbstractException extends RuntimeException{

    private final String errorMsg;

    public AbstractException(String errorMsg){
        this.errorMsg=errorMsg;
    }


    public String getErrorMsg() {
        return errorMsg;
    }

    @Override
    public String toString() {
        return getClass().getSimpleName()+"{" +
                "errorMsg='" + errorMsg + '\'' +
                '}';
    }
}
