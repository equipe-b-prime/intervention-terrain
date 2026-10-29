package com.interventionTerrain.interventionTerrain.service;

public abstract class AbstractClean {
    protected String cleanText(String text) {
        return text != null ? text.replaceAll("\\s+", " ").trim() : null;
    }

}
