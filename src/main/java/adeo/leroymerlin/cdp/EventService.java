package adeo.leroymerlin.cdp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Iterator;
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

        Iterator<Event> eventIterator = events.iterator();

        while (eventIterator.hasNext()){

            Event event = eventIterator.next();

            // Remove members not matching with pattern
            event.getBands().forEach(band -> band.getMembers()
                    .removeIf(member -> !matchWithPattern(member.getName(), query)));
            // Remove bands with no member
            event.getBands().removeIf(band -> band.getMembers().size() == 0);
            // Remove events with no band
            if (event.getBands().size() == 0) eventIterator.remove();

        }

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
}
