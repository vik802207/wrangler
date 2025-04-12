package io.cdap.wrangler.api.parser;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ByteSize extends NewToken {
    private static final Pattern PATTERN = Pattern.compile("(?i)(\\d+(\\.\\d+)?)(B|KB|MB|GB|TB)");
    private final double value;
    private final String unit;

    public ByteSize(String value) {
        super(value);
        Matcher matcher = PATTERN.matcher(value.trim());
        if (!matcher.matches()) {
            throw new IllegalArgumentException("Invalid byte size format: " + value);
        }
        this.value = Double.parseDouble(matcher.group(1));
        this.unit = matcher.group(3).toUpperCase();
    }

    public long getBytes() {
        switch (unit) {
            case "B": return (long) value;
            case "KB": return (long) (value * 1024);
            case "MB": return (long) (value * 1024 * 1024);
            case "GB": return (long) (value * 1024 * 1024 * 1024);
            case "TB": return (long) (value * 1024L * 1024 * 1024 * 1024);
            default: throw new IllegalStateException("Unknown unit: " + unit);
        }
    }
}
