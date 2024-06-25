package rs.ac.uns.acs.nais.ElasticSearchDatabaseService.util;

import java.util.concurrent.atomic.AtomicInteger;

public class CustomIdGenerator {
    private static final AtomicInteger counter = new AtomicInteger(1);

    public static String generateId() {
        return String.valueOf(counter.getAndIncrement());
    }
}
