package io.pivotal.pal.tracker;

import org.springframework.boot.actuate.metrics.CounterService;
import org.springframework.boot.actuate.metrics.GaugeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class TimeEntryController {
    private TimeEntryRepository repo;

    private final CounterService counter;
    private final GaugeService gauge;

    public TimeEntryController(TimeEntryRepository repo, CounterService counter, GaugeService gauge) {
        this.repo = repo;
        this.counter = counter;
        this.gauge = gauge;
    }

    @GetMapping("/time-entries/{id}")
    public ResponseEntity<TimeEntry> read(@PathVariable("id") long id) {
        TimeEntry entry = repo.find(id);
        if (entry != null)
            return ResponseEntity.ok(entry);
        else
            return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/time-entries/{id}")
    public ResponseEntity delete(@PathVariable("id") long id) {
        repo.delete(id);
        counter.increment("TimeEntry.deleted");
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/time-entries")
    public ResponseEntity<List<TimeEntry>> list() {
        counter.increment("TimeEntry.listed");
        return ResponseEntity.ok(repo.list());
    }

    @PutMapping("/time-entries/{id}")
    public ResponseEntity update(@PathVariable("id") long id, @RequestBody TimeEntry te) {
        TimeEntry entry = repo.update(id, te);
        if (entry != null) {
            counter.increment("TimeEntry.updated");
            return ResponseEntity.ok(entry);
        }
        else
            return ResponseEntity.notFound().build();
    }

    @PostMapping("/time-entries")
    public ResponseEntity create(@RequestBody TimeEntry expected) {
        TimeEntry entry = repo.create(expected);
        counter.increment("TimeEntry.created");
        gauge.submit("timeEntries.count", repo.list().size());
        return new ResponseEntity<>(entry, HttpStatus.CREATED);
    }
}
