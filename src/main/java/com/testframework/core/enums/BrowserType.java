package com.testframework.core.enums;

public enum BrowserType {
    CHROME("chrome"),
    FIREFOX("firefox"),
    EDGE("edge");

    private final String name;

    BrowserType(String name) {
        this.name = name;
    }

    public static BrowserType fromString(String value) {
        for (BrowserType type : values()) {
            if (type.name.equalsIgnoreCase(value)) {
                return type;
            }
        }
        throw new IllegalArgumentException("Unknown browser: " + value);
    }
}
