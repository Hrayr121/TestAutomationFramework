package com.testframework.core.data;

import com.google.gson.JsonArray;
import com.google.gson.JsonParser;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

public class JsonReader {

    /**
     * Reads a JSON array file from the classpath and returns a TestNG @DataProvide} compatible Object[][].
     * Each row contains a single JsonObject.
     */
    public static Object[][] readAsDataProvider(String classpathResource) throws IOException {
        JsonArray array = readJsonArray(classpathResource);
        Object[][] data = new Object[array.size()][1];
        for (int i = 0; i < array.size(); i++) {
            data[i][0] = array.get(i).getAsJsonObject();
        }
        return data;
    }

    public static JsonArray readJsonArray(String classpathResource) throws IOException {
        String content = readFile(classpathResource);
        return JsonParser.parseString(content).getAsJsonArray();
    }

    private static String readFile(String classpathResource) throws IOException {
        try (InputStream input = JsonReader.class
                .getClassLoader()
                .getResourceAsStream(classpathResource)) {
            if (input == null) {
                throw new IOException("Classpath resource not found: " + classpathResource);
            }
            return new String(input.readAllBytes(), StandardCharsets.UTF_8);
        }
    }
}
