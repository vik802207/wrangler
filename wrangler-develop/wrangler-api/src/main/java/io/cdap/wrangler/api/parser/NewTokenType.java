package io.cdap.wrangler.api.parser;
public enum NewTokenType {
    COLUMN,
    STRING,
    NUMBER,
    BOOLEAN,
    // ✅ Add these two new types
    BYTE_SIZE,
    TIME_DURATION
}

