package io.cdap.wrangler.directive;

import io.cdap.wrangler.api.Row;
import io.cdap.wrangler.api.annotation.Aggregate;
import io.cdap.wrangler.api.parser.*;
import io.cdap.wrangler.api.Directive;
import io.cdap.wrangler.api.DirectiveContext;
import io.cdap.wrangler.api.DirectiveName;
import io.cdap.wrangler.api.ExecutorContext;
import io.cdap.wrangler.api.RecipeException;
import io.cdap.wrangler.api.parser.ColumnName;
import io.cdap.wrangler.api.parser.Text;
import io.cdap.wrangler.api.parser.TokenGroup;
import io.cdap.wrangler.api.parser.DirectiveParser;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Example usage:
 * aggregate-stats :data_transfer_size :response_time total_size_mb total_time_sec
 */
@Aggregate
@DirectiveName(name = "aggregate-stats", usage = "aggregate-stats <sourceByteSize> <sourceTimeDuration> <targetByteSize> <targetTimeDuration>", description = "Aggregate byte size and time duration")
public class AggregateStats implements Directive {
    private String sourceSizeCol;
    private String sourceTimeCol;
    private String targetSizeCol;
    private String targetTimeCol;

    private long totalBytes = 0;
    private long totalMilliseconds = 0;
    private int rowCount = 0;

    @Override
    public void initialize(DirectiveContext ctx, TokenGroup args) throws RecipeException {
        if (args.size() < 4) {
            throw new RecipeException("aggregate-stats requires 4 arguments");
        }

        sourceSizeCol = ((ColumnName) args.get(0)).value();
        sourceTimeCol = ((ColumnName) args.get(1)).value();
        targetSizeCol = ((Text) args.get(2)).value();
        targetTimeCol = ((Text) args.get(3)).value();
    }

    @Override
    public List<Row> execute(List<Row> rows, ExecutorContext ctx) throws Exception {
        for (Row row : rows) {
            Object sizeVal = row.getValue(sourceSizeCol);
            Object timeVal = row.getValue(sourceTimeCol);

            if (sizeVal != null) {
                long bytes = parseByteSize(sizeVal.toString());
                totalBytes += bytes;
            }

            if (timeVal != null) {
                long millis = parseTimeDuration(timeVal.toString());
                totalMilliseconds += millis;
            }

            rowCount++;
        }

        // Convert to MB and seconds
        double totalSizeMB = totalBytes / (1024.0 * 1024.0);
        double totalTimeSec = totalMilliseconds / 1000.0;

        List<Row> result = new ArrayList<>();
        Row out = new Row();
        out.add(targetSizeCol, totalSizeMB);
        out.add(targetTimeCol, totalTimeSec);
        result.add(out);
        return result;
    }

    private long parseByteSize(String value) {
        ByteSize bs = new ByteSize(value);
        return bs.getBytes();
    }

    private long parseTimeDuration(String value) {
        TimeDuration td = new TimeDuration(value);
        return td.getMilliseconds();
    }
}
