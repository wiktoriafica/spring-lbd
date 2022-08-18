package pl.fis.springlbdday2.service.sprint;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import pl.fis.springlbdday2.dto.sprint.SprintGetDto;
import pl.fis.springlbdday2.dto.sprint.SprintPostDto;
import pl.fis.springlbdday2.dto.userstory.UserStoryGetDto;
import pl.fis.springlbdday2.entity.enums.SprintStatus;
import pl.fis.springlbdday2.entity.sprint.Sprint;
import pl.fis.springlbdday2.event.UserStoryCreatedEvent;

import java.time.LocalDate;
import java.util.List;

public interface SprintService {
    Sprint addSprint(SprintPostDto sprint);

    void addSprintWithUserStories();

    SprintGetDto getSprintById(Long id);

    List<UserStoryGetDto> getUsersStoriesBySprintId(Long id);

    List<SprintGetDto> getSprintsFromGivenTime(LocalDate startDate, LocalDate endDate);

    List<SprintGetDto> getSprintsFromGivenTime(String startDate, String endDate);

    Integer getStoryPointsFromSprint(Long id);

    void deleteSprintById(Long id);

    Page<SprintGetDto> getPaginatedAndSortedSprints(Pageable page);

    LocalDate getLastStartDate();

    List<SprintGetDto> getSprints(boolean tasks);

    void updateSprint(Long id, SprintPostDto sprintPostDto);
    void updateSprintStatus(Long id, SprintStatus status);

    void userStoryCreatedEventHandler(UserStoryCreatedEvent userStoryCreatedEvent);
}
