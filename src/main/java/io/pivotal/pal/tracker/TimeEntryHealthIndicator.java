package io.pivotal.pal.tracker;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.health.AbstractHealthIndicator;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.boot.actuate.metrics.CounterService;
import org.springframework.stereotype.Component;

@Component
public class TimeEntryHealthIndicator extends AbstractHealthIndicator {

    private final CounterService counterService;
    @Autowired
    private TimeEntryRepository repo;

    @Autowired
    public TimeEntryHealthIndicator(CounterService counterService) {
        this.counterService = counterService;
    }

    @Override
    protected void doHealthCheck(Health.Builder builder) throws Exception {
        int count = repo.list().size();
        if (count < 5)
            builder.up();
        else
            builder.down();
    }
}
