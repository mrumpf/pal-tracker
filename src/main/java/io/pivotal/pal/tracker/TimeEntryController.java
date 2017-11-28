package io.pivotal.pal.tracker;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class TimeEntryController {
    private TimeEntryRepository repo;

    public TimeEntryController(TimeEntryRepository repo) {
        this.repo = repo;
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
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/time-entries")
    public ResponseEntity<List<TimeEntry>> list() {
        return ResponseEntity.ok(repo.list());
    }

    @PutMapping("/time-entries/{id}")
    public ResponseEntity update(@PathVariable("id") long id, @RequestBody TimeEntry te) {
        TimeEntry entry = repo.update(id, te);
        if (entry != null)
            return ResponseEntity.ok(entry);
        else
            return ResponseEntity.notFound().build();
    }

    @PostMapping("/time-entries")
    public ResponseEntity create(@RequestBody TimeEntry expected) {
        TimeEntry entry = repo.create(expected);
        return new ResponseEntity<>(entry, HttpStatus.CREATED);
    }
}
