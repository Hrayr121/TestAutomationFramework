package com.testframework.core.enums;

public enum Environment {
    DEV("dev"),
    STAGING("staging"),
    PROD("prod"),
    CI("ci");

    private final String name;

    Environment(String name) {
        this.name = name;
    }

    public static Environment fromString(String value) {
        for (Environment env : values()) {
            if (env.name.equalsIgnoreCase(value)) {
                return env;
            }
        }
        throw new IllegalArgumentException("Unknown environment: " + value);
    }

    public String getValue() {
        return name;
    }
}
