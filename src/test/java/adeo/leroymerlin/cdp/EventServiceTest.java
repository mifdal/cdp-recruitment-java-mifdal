package adeo.leroymerlin.cdp;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class EventServiceTest {

    private EventService eventService;

    @Mock
    private EventRepository eventRepository;

    @BeforeEach
    void init() {
        eventService = new EventService(eventRepository);
    }


    @Test
    void getFilteredEvents() {

        // initialization Event List
        List<Event> events = new ArrayList<>();

        //Create Member
        Member member1 = new Member();
        member1.setName("Test One Wallet");

        Member member2 = new Member();
        member2.setName("Test Two member");

        Member member3 = new Member();
        member3.setName("Test three member");

        Member member4 = new Member();
        member4.setName("Test for member");

        Member member5 = new Member();
        member5.setName("Test Member X");

        Member member6 = new Member();
        member6.setName("Test Member X");

        //Create Members Set

        Set<Member> membersSet1 = new HashSet<>();
        membersSet1.add(member1);
        membersSet1.add(member2);
        membersSet1.add(member3);
        membersSet1.add(member4);

        Set<Member> membersSet2 = new HashSet<>();
        membersSet2.add(member5);

        Set<Member> membersSet3 = new HashSet<>();
        membersSet3.add(member6);

        //Create Bands

        Band band1 = new Band();
        band1.setName("Test band 1");
        band1.setMembers(membersSet1);

        Band band2 = new Band();
        band2.setName("Test Band 2");
        band2.setMembers(membersSet2);

        Band band3 = new Band();
        band3.setName("Test Band 2");
        band3.setMembers(membersSet3);

        //Create Bands Set

        Set<Band> bandsSet1 = new HashSet<>();
        bandsSet1.add(band1);
        bandsSet1.add(band2);

        Set<Band> bandsSet2 = new HashSet<>();
        bandsSet2.add(band3);

        //Create Event

        Event event1 = new Event();
        event1.setId(1L);
        event1.setTitle("Event Title");
        event1.setNbStars(2);
        event1.setComment("This is a comment test");
        event1.setBands(bandsSet1);


        Event event2 = new Event();
        event2.setId(2L);
        event2.setTitle("Event Title");
        event2.setNbStars(2);
        event2.setComment("This is a comment test");
        event2.setBands(bandsSet2);

        events.add(event1);
        events.add(event2);

        when(eventRepository.findAllBy()).thenReturn(events);

        String searchedPattern = "wa";

        List<Event> eventsFind = eventService.getFilteredEvents(searchedPattern);

        assertEquals(event1.getId(), eventsFind.get(0).getId());
    }
}
