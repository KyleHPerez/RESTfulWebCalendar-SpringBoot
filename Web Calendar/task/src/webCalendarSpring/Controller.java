package webCalendarSpring;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RestController
public class Controller {

    EventService eventService;

    public Controller(EventService eventService) {
        this.eventService = eventService;
    }

    @GetMapping("/event")
    public ResponseEntity<List<Event>> getEventsRange(
            @RequestParam(required = false, defaultValue = "0000-01-01") String start_time,
            @RequestParam(required = false, defaultValue = "9999-12-31") String end_time) {
        List<Event> events = eventService.findByRange(start_time, end_time);
        if (events.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(events, HttpStatus.OK);
    }

    @GetMapping("/event/{id}")
    public ResponseEntity<Event> getEventById(@PathVariable Long id) {
        Event event = eventService.findById(id).orElseThrow(() ->
                new EventNotFoundException("{\"message\": \"The event doesn't exist!\"}"));
        return new ResponseEntity<>(event, HttpStatus.OK);
    }

    @GetMapping("/event/today")
    public ResponseEntity<List<Event>> getTodaysEvents() {
        LocalDate today = LocalDate.now();
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        today.format(dtf);
        String todayStr = today.toString();
        if (eventService.findByDate(todayStr).isEmpty()) {
            List<Event> emptyList = new ArrayList<>();
            return new ResponseEntity<>(emptyList, HttpStatus.OK);
        } else {
            List<Event> events = eventService.findByDate(todayStr);
            return new ResponseEntity<>(events, HttpStatus.OK);
        }
    }

    @PostMapping("/event")
    public ResponseEntity<String> receiveEvent(@Valid @RequestBody Event event) {
        eventService.create(event);
        return new ResponseEntity<>(String.format("""
                {
                "message": "The event has been added!",
                "event": "%s",
                "date": "%s"
                }
                """, event.getEvent(), event.getDate()), HttpStatus.OK);
    }

    @DeleteMapping("/event/{id}")
    public ResponseEntity<Event> deleteEvent(@PathVariable Long id) {
        Event event = eventService.findById(id).orElseThrow(() -> new EventNotFoundException("{\"message\": \"The event doesn't exist!\"}"));
        eventService.delete(id);
        return new ResponseEntity<>(event, HttpStatus.OK);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<String> handleValidationException(MethodArgumentNotValidException ex) {
        return ResponseEntity.badRequest().body("");
    }

    @ExceptionHandler(EventNotFoundException.class)
    public ResponseEntity<String> handleEventNotFoundException(EventNotFoundException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
    }
}
