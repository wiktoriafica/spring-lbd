package pl.fis.springlbdday2.service.sprint;

import pl.fis.springlbdday2.entity.sprint.Sprint;
import pl.fis.springlbdday2.entity.userstory.UserStory;

import java.time.LocalDate;
import java.util.List;

public interface SprintService {
    void addSprint(Sprint sprint);
    Sprint getSprintById(Long id);
    List<UserStory> getUsersStoriesBySprintId(Long id);
    List<Sprint> getSprintsFromGivenTime(LocalDate startDate, LocalDate endDate);
    Integer getStoryPointsFromSprint(Long id);

    void deleteSprintById(Long id);
}
