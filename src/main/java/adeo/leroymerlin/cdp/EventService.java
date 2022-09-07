package adeo.leroymerlin.cdp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class EventService {

    private final EventRepository eventRepository;

    @Autowired
    public EventService(EventRepository eventRepository) {
        this.eventRepository = eventRepository;
    }

    public List<Event> getEvents() {
        return eventRepository.findAllBy();
    }

    public void delete(Long id) {
        eventRepository.deleteById(id);
    }

    public List<Event> getFilteredEvents(String query) {
        List<Event> events = eventRepository.findAllBy();
        // Filter the events list in pure JAVA here

        // Filter events which has at least one band with a member name matching with query pattern
        events = events.stream()
                .filter(event ->
                        (event.getBands().stream()
                                .filter(band -> band.getMembers()
                                        .removeIf(member -> !matchWithPattern(member.getName(), query))).count() > 0) // Delete all members witch not match with pattern
                                && event.getBands().removeIf(band -> band.getMembers().size() == 0) // Remove band with no member
                                && event.getBands().size() > 0) // Filter event with no band
                .toList();

        // Add count at each event and band
        events.stream().forEach(event -> {
            event.setTitle(addCountToElement(event.getTitle(), event.getBands().size()));
            event.getBands().stream().forEach(band -> {
                band.setName(addCountToElement(band.getName(), band.getMembers().size()));
            });
        });

        return events;
    }

    public void updateReview(Long id, Event event) {
        eventRepository.updateReview(id, event.getNbStars(), event.getComment());
    }

    private boolean matchWithPattern(String memberName, String query) {
        Pattern p = Pattern.compile(query.toLowerCase());
        Matcher m = p.matcher(memberName.toLowerCase());
        return m.find();
    }
    private String addCountToElement(String element, Integer count){
        return element+" ["+count+"]";
    }
}
