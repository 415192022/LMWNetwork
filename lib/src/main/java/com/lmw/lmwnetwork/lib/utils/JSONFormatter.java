package com.lmw.lmwnetwork.lib.utils;


public class JSONFormatter {

    static final JSONFormatter FORMATTER = findJSONFormatter();

    public static String formatJSON(String source) {
        try {
            return FORMATTER.format(source);
        } catch (Exception e) {
            return "";
        }
    }

    private static JSONFormatter findJSONFormatter() {
        JSONFormatter jsonFormatter = OrgJsonFormatter.buildIfSupported();
        if (jsonFormatter != null) {
            return jsonFormatter;
        }

        return new JSONFormatter();
    }

    String format(String source) {
        return "";
    }
}