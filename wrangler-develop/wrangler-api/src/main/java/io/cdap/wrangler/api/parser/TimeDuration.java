package io.cdap.wrangler.api.parser;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TimeDuration extends NewToken {
    private static final Pattern PATTERN = Pattern.compile("(?i)(\\d+(\\.\\d+)?)(ms|s|m|h)");
    private final double value;
    private final String unit;

    public TimeDuration(String value) {
        super(value);
        Matcher matcher = PATTERN.matcher(value.trim());
        if (!matcher.matches()) {
            throw new IllegalArgumentException("Invalid time duration format: " + value);
        }
        this.value = Double.parseDouble(matcher.group(1));
        this.unit = matcher.group(3).toLowerCase();
    }

    public long getMilliseconds() {
        switch (unit) {
            case "ms": return (long) value;
            case "s": return (long) (value * 1000);
            case "m": return (long) (value * 60 * 1000);
            case "h": return (long) (value * 60 * 60 * 1000);
            default: throw new IllegalStateException("Unknown unit: " + unit);
        }
    }
}

