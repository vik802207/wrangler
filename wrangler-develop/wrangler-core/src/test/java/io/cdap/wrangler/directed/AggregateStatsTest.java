package io.cdap.wrangler.directive;

import io.cdap.wrangler.api.Row;
import io.cdap.wrangler.test.RecipeTestingRig;
import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

public class AggregateStatsTest {

    @Test
    public void testAggregateStatsWithBytesAndTime() throws Exception {
        List<Row> rows = Arrays.asList(
            new Row("data_transfer_size", "1MB").add("response_time", "100ms"),
            new Row("data_transfer_size", "512KB").add("response_time", "400ms"),
            new Row("data_transfer_size", "2MB").add("response_time", "500ms")
        );

        String[] recipe = {
            "aggregate-stats :data_transfer_size :response_time total_size_mb total_time_sec"
        };

        List<Row> result = RecipeTestingRig.execute(recipe, rows);

        Assert.assertEquals(1, result.size());

        Row output = result.get(0);

        double expectedTotalSizeInMB = (1 * 1024 * 1024 + 512 * 1024 + 2 * 1024 * 1024) / (1024.0 * 1024.0); // ~3.5 MB
        double expectedTotalTimeInSeconds = (100 + 400 + 500) / 1000.0; // 1.0 sec

        double sizeOut = (double) output.getValue("total_size_mb");
        double timeOut = (double) output.getValue("total_time_sec");

        Assert.assertEquals(expectedTotalSizeInMB, sizeOut, 0.001);
        Assert.assertEquals(expectedTotalTimeInSeconds, timeOut, 0.001);
    }
}
