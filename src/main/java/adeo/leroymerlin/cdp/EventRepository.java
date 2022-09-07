package adeo.leroymerlin.cdp;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional(readOnly = false)
public interface EventRepository extends Repository<Event, Long> {

    void deleteById(Long eventId);

    List<Event> findAllBy();

    @Modifying
    @Query("update Event e set e.nbStars = :nbStars, e.comment = :comment where e.id = :id")
    void updateReview(@Param(value = "id") long id, @Param(value = "nbStars") Integer nbStars, @Param(value = "comment") String comment);

}
