package pl.fis.springlbdday2.repository.sprint;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import pl.fis.springlbdday2.entity.sprint.Sprint;
import pl.fis.springlbdday2.entity.userstory.UserStory;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface SprintRepository extends CrudRepository<Sprint, Long> {
    @Query("SELECT u FROM Sprint s JOIN s.userStories u WHERE s.id=:id")
    List<UserStory> findUserStoriesBySprintId(Long id);

    List<Sprint> findByStartDateBetween(LocalDate start, LocalDate end);

    @Query("SELECT sum(u.storyPoints) FROM Sprint s JOIN s.userStories u WHERE s.id=:id AND " +
            "u.status='DONE'")
    Integer getStoryPointsFromSprint(Long id);
}
