package com.example.application.views.main.component.customField;

public class Phone {
    private final String code;
    private final String number;

    public Phone(String code, String number) {
        this.code = code;
        this.number = number;
        this.toString();
    }

    public String getCode() {
        return code;
    }

    public String getNumber() {
        return number;
    }

    @Override
    public String toString() {
        String primeirosNumeros = number.substring(0, 4);
        String ultimosNumeros = number.substring(4, 8);
        return "("+code+") "+primeirosNumeros+"-"+ultimosNumeros;
    }
}
