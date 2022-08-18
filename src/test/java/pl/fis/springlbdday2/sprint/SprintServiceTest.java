package pl.fis.springlbdday2.sprint;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.transaction.support.TransactionSynchronizationManager;
import pl.fis.springlbdday2.dto.sprint.SprintGetDto;
import pl.fis.springlbdday2.dto.sprint.SprintPostDto;
import pl.fis.springlbdday2.dto.userstory.UserStoryGetDto;
import pl.fis.springlbdday2.dto.userstory.UserStoryMapper;
import pl.fis.springlbdday2.dto.userstory.UserStoryPostDto;
import pl.fis.springlbdday2.entity.enums.SprintStatus;
import pl.fis.springlbdday2.entity.enums.UserStoryStatus;
import pl.fis.springlbdday2.entity.sprint.Sprint;
import pl.fis.springlbdday2.entity.userstory.UserStory;
import pl.fis.springlbdday2.exception.InvalidDataException;
import pl.fis.springlbdday2.service.sprint.SprintService;
import pl.fis.springlbdday2.service.userstory.UserStoryService;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@SpringBootTest
public class SprintServiceTest {
    @Autowired
    private SprintService sprintService;
    @Autowired
    private UserStoryService userStoryService;
    @Autowired
    private UserStoryMapper userStoryMapper;
    @Test
    public void givenCorrectSprint_whenAddingToDatabase_thenSuccess() {
        SprintPostDto sprint = new SprintPostDto();
        sprint.setName("Sprint 1");
        sprint.setStatus(SprintStatus.PENDING);
        sprint.setStartDate(LocalDate.now());
        sprint.setEndDate(LocalDate.now().plusDays(21));
        Sprint addedSprint = sprintService.addSprint(sprint);
        sprintService.deleteSprintById(addedSprint.getId());
        assertNotNull(addedSprint.getId());
        assertThat(addedSprint.getName()).isEqualTo(sprint.getName());
        assertThat(addedSprint.getStartDate()).isEqualTo(sprint.getStartDate());
        assertThat(addedSprint.getEndDate()).isEqualTo(sprint.getEndDate());
        assertThat(addedSprint.getStatus()).isEqualTo(sprint.getStatus());
    }

    @Test
    public void givenIncorrectSprint_whenAddingToDatabase_thenThrow() {
        SprintPostDto sprint = new SprintPostDto();
        sprint.setStatus(SprintStatus.PENDING);
        sprint.setStartDate(LocalDate.now());
        sprint.setEndDate(LocalDate.now().minusDays(21));

        InvalidDataException exception = assertThrows(InvalidDataException.class, () -> {
            sprintService.addSprint(sprint);
        });
        assertFalse(TransactionSynchronizationManager.isActualTransactionActive());
    }

    @Test
    public void givenCorrectSprintAndThreeUserStories_whenAddToDatabase_thenSuccess () {
        UserStoryPostDto userStory1 = new UserStoryPostDto();
        userStory1.setName("User story 11");
        userStory1.setDescription("This is user story 1");
        userStory1.setStatus(UserStoryStatus.DONE);
        UserStoryPostDto userStory2 = new UserStoryPostDto();
        userStory2.setName("User story 22");
        userStory2.setDescription("This is user story 2");
        userStory2.setStatus(UserStoryStatus.DONE);
        UserStoryPostDto userStory3 = new UserStoryPostDto();
        userStory3.setName("User story 33");
        userStory3.setDescription("This is user story 3");
        userStory3.setStatus(UserStoryStatus.DONE);
        List<UserStory> userStories = List.of(userStoryService.addUserStory(userStory1),
                userStoryService.addUserStory(userStory2), userStoryService.addUserStory(userStory3));
        SprintPostDto sprint = new SprintPostDto();
        sprint.setName("Sprint");
        sprint.setStatus(SprintStatus.PENDING);
        sprint.setStartDate(LocalDate.now());
        sprint.setEndDate(LocalDate.now().plusDays(21));
        sprint.setUserStories(userStories.stream().map(userStoryMapper::getUserStoryGetDtoFromUserStory).collect(Collectors.toList()));
        Sprint addedSprint = sprintService.addSprint(sprint);

        List<UserStoryGetDto> retrievedUserStories = sprintService.getUsersStoriesBySprintId(addedSprint.getId());

        sprintService.deleteSprintById(addedSprint.getId());
        for(UserStory userStory : userStories) {
            userStoryService.deleteUserStoryById(userStory.getId());
        }
        assertEquals(userStories.size(), retrievedUserStories.size());
    }

    @Test
    public void givenSprintsInGivenTime_whenFindSprintsFromTimePeriod_thenSuccess() {
        SprintPostDto sprint = new SprintPostDto();
        sprint.setName("Sprint 1");
        sprint.setStatus(SprintStatus.PENDING);
        sprint.setStartDate(LocalDate.now().plusDays(205));
        sprint.setEndDate(LocalDate.now().plusDays(214));
        SprintPostDto sprint2 = new SprintPostDto();
        sprint2.setName("Sprint 2");
        sprint2.setStatus(SprintStatus.PENDING);
        sprint2.setStartDate(LocalDate.now().plusDays(201));
        sprint2.setEndDate(LocalDate.now().plusDays(212));
        SprintPostDto sprint3 = new SprintPostDto();
        sprint3.setName("Sprint 3");
        sprint3.setStatus(SprintStatus.PENDING);
        sprint3.setStartDate(LocalDate.now().plusDays(200));
        sprint3.setEndDate(LocalDate.now().plusDays(214));
        Sprint addedSprint1 = sprintService.addSprint(sprint);
        Sprint addedSprint2 = sprintService.addSprint(sprint2);
        Sprint addedSprint3 = sprintService.addSprint(sprint3);

        List<SprintGetDto> sprints = sprintService.getSprintsFromGivenTime(LocalDate.now().
                plusDays(200), LocalDate.now().plusDays(214));

        sprintService.deleteSprintById(addedSprint1.getId());
        sprintService.deleteSprintById(addedSprint2.getId());
        sprintService.deleteSprintById(addedSprint3.getId());

        assertEquals(3, sprints.size());
        assertEquals(addedSprint1.getId(), sprints.get(0).getId());
        assertEquals(addedSprint2.getId(), sprints.get(1).getId());
        assertEquals(addedSprint3.getId(), sprints.get(2).getId());
    }

    @Test
    public void givenSprintAndUserStories_whenGetUserStoriesPointsFromSprint_thenSuccess() {
        UserStoryPostDto userStory1 = new UserStoryPostDto();
        userStory1.setName("User story 1");
        userStory1.setDescription("This is user story 1");
        userStory1.setStatus(UserStoryStatus.DONE);
        userStory1.setStoryPoints(15);
        UserStoryPostDto userStory2 = new UserStoryPostDto();
        userStory2.setName("User story 2");
        userStory2.setDescription("This is user story 2");
        userStory2.setStatus(UserStoryStatus.DONE);
        userStory2.setStoryPoints(26);
        UserStoryPostDto userStory3 = new UserStoryPostDto();
        userStory3.setName("User story 3");
        userStory3.setDescription("This is user story 3");
        userStory3.setStatus(UserStoryStatus.IN_PROGRESS);
        userStory3.setStoryPoints(32);
        List<UserStory> userStories = List.of(userStoryService.addUserStory(userStory1),
                userStoryService.addUserStory(userStory2), userStoryService.addUserStory(userStory3));

        Integer points = 0;
        for(UserStory userStory : userStories) {
            if(userStory.getStatus() == UserStoryStatus.DONE)
                points += userStory.getStoryPoints();
        }
        SprintPostDto sprint = new SprintPostDto();
        sprint.setName("Sprint");
        sprint.setStatus(SprintStatus.PENDING);
        sprint.setStartDate(LocalDate.now());
        sprint.setEndDate(LocalDate.now().plusDays(21));
        sprint.setUserStories(userStories.stream().map(userStoryMapper::getUserStoryGetDtoFromUserStory).collect(Collectors.toList()));
        Sprint addedSprint = sprintService.addSprint(sprint);

        Integer retrievedPoints = sprintService.getStoryPointsFromSprint(addedSprint.getId());

        sprintService.deleteSprintById(addedSprint.getId());
        for(UserStory userStory : userStories) {
            userStoryService.deleteUserStoryById(userStory.getId());
        }

        assertEquals(points, retrievedPoints);
    }

    @Test
    public void givenSprints_whenGettingPaginatedAndSortedByDate_thenSuccess() {
        Page<SprintGetDto> sprints = sprintService
                .getPaginatedAndSortedSprints(PageRequest.of(0, 10,
                        Sort.by(Sort.Order.desc("startDate"))));
        assertEquals(10, sprints.getSize());
        assertEquals(sprintService.getLastStartDate(), sprints.getContent().get(0).getStartDate());
    }
}
