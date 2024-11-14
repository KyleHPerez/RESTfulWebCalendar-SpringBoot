package webCalendarSpring;

import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class EventService {

    private final EventRepository eventRepository;

    public EventService(EventRepository eventRepository) {
        this.eventRepository = eventRepository;
    }

    public Event create(Event event) {
        return eventRepository.save(event);
    }

    public Optional<Event> findById(Long id) {
        return eventRepository.findById(id);
    }

    public List<Event> findByRange(String startDate, String endDate) {
        return eventRepository.findByRange(startDate, endDate);
    }

    public List<Event> findByDate(String date) {
        return eventRepository.findByDate(date);
    }

    public void delete(Long id) {
        eventRepository.deleteById(id);
    }
}
