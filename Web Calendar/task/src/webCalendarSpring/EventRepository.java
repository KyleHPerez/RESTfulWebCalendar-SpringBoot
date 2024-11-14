package webCalendarSpring;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface EventRepository extends JpaRepository<Event, Long> {

    @Query("SELECT e FROM Event e WHERE e.date >= :startDate AND e.date <= :endDate")
    List<Event> findByRange(@Param("startDate") String startDate, @Param("endDate") String endDate);

    @Override
    Optional<Event> findById(Long id);

    List<Event> findByDate(String date);
}
