package io.pivotal.pal.tracker;

import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InMemoryTimeEntryRepository implements TimeEntryRepository {

    private long counter = 1L;
    private Map<Long, TimeEntry> map = new HashMap<>();

    @Override
    public TimeEntry create(TimeEntry any) {
        Long id = counter++;
        any.setId(id);
        map.put(id, any);
        return any;
    }

    @Override
    public TimeEntry find(Long id) {
        return map.get(id);
    }

    @Override
    public TimeEntry update(Long id, TimeEntry any) {
        any.setId(id);
        map.put(id, any);
        return any;
    }

    @Override
    public void delete(Long id) {
        map.remove(id);
    }

    @Override
    public List<TimeEntry> list() {
        return new ArrayList<>(map.values());
    }
}
