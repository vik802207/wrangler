package io.cdap.wrangler.api.parser;

/**
 * A base class for custom tokens like ByteSize and TimeDuration.
 * Stores the original string value and provides basic functionality.
 */
public abstract class NewToken {
    protected final String value;

    public NewToken(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    @Override
    public String toString() {
        return value;
    }
}