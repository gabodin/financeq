package com.sb.financeq.entities.enums;

public enum Category {
    SAIDAS(1),
    PETS(2),
    IFOOD(3),
    MERCADO(4),
    UBER(5),
    ONIBUS(6),
    ASSINATURA(7),
    SAUDE(8),
    DOMESTICO(9),
    OUTROS(10);

    private int code;

    private Category(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }

    public static Category valueOf(int code) {
        for(Category value : Category.values()) {
            if(value.getCode() == code) return value;
        }

        throw new IllegalArgumentException("Invalid Category code");
    }
}
