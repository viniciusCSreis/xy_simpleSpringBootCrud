package com.zup.xy_simpleSpringBootCrud.util;

public class FieldErrorMessage {

    private  String field;
    private String message;

    public FieldErrorMessage(String field, String message) {
        this.field = field;
        this.message = message;
    }

    public String getField() {
        return field;
    }


    public String getMessage() {
        return message;
    }

}
