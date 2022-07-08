package pl.fis.springlbdday2.sprint;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.transaction.support.TransactionSynchronizationManager;
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

@SpringBootTest
public class SprintServiceTest {
    @Autowired
    private SprintService sprintService;
    @Autowired
    private UserStoryService userStoryService;
    @Test
    public void givenCorrectSprint_whenAddingToDatabase_thenSuccess() {
        Sprint sprint = new Sprint();
        sprint.setName("Sprint 1");
        sprint.setStatus(SprintStatus.PENDING);
        sprint.setStartDate(LocalDate.now());
        sprint.setEndDate(LocalDate.now().plusDays(21));

        sprintService.addSprint(sprint);
        Sprint addedSprint = sprintService.getSprintById(sprint.getId());

        sprintService.deleteSprintById(sprint.getId());

        assertThat(addedSprint.getId()).isEqualTo(sprint.getId());
        assertThat(addedSprint.getName()).isEqualTo(sprint.getName());
        assertThat(addedSprint.getStartDate()).isEqualTo(sprint.getStartDate());
        assertThat(addedSprint.getEndDate()).isEqualTo(sprint.getEndDate());
        assertThat(addedSprint.getStatus()).isEqualTo(sprint.getStatus());
    }

    @Test
    public void givenIncorrectSprint_whenAddingToDatabase_thenThrow() {
        Sprint sprint = new Sprint();
        sprint.setStatus(SprintStatus.PENDING);
        sprint.setStartDate(LocalDate.now());
        sprint.setEndDate(LocalDate.now().minusDays(21));

        InvalidDataException exception = assertThrows(InvalidDataException.class, () -> {
            sprintService.addSprint(sprint);
        });

        assertFalse(TransactionSynchronizationManager.isActualTransactionActive());
        assertNull(sprint.getId());
    }

    @Test
    public void givenCorrectSprintAndThreeUserStories_whenAddToDatabase_thenSuccess () {
        UserStory userStory1 = new UserStory();
        userStory1.setName("User story 11");
        userStory1.setDescription("This is user story 1");
        userStory1.setStatus(UserStoryStatus.DONE);
        UserStory userStory2 = new UserStory();
        userStory2.setName("User story 22");
        userStory2.setDescription("This is user story 2");
        userStory2.setStatus(UserStoryStatus.DONE);
        UserStory userStory3 = new UserStory();
        userStory3.setName("User story 33");
        userStory3.setDescription("This is user story 3");
        userStory3.setStatus(UserStoryStatus.DONE);
        userStoryService.addUserStory(userStory1);
        userStoryService.addUserStory(userStory2);
        userStoryService.addUserStory(userStory3);
        List<UserStory> userStories = List.of(userStory1, userStory2, userStory3);

        Sprint sprint = new Sprint();
        sprint.setName("Sprint");
        sprint.setStatus(SprintStatus.PENDING);
        sprint.setStartDate(LocalDate.now());
        sprint.setEndDate(LocalDate.now().plusDays(21));
        sprint.setUserStories(userStories);
        sprintService.addSprint(sprint);

        List<UserStory> retrievedUserStories = sprintService.getUsersStoriesBySprintId(sprint.getId());

        sprintService.deleteSprintById(sprint.getId());
        userStoryService.deleteUserStoryById(userStory1.getId());
        userStoryService.deleteUserStoryById(userStory2.getId());
        userStoryService.deleteUserStoryById(userStory3.getId());

        assertEquals(userStories.size(), retrievedUserStories.size());
    }

    @Test
    public void givenSprintsInGivenTime_whenFindSprintsFromTimePeriod_thenSuccess() {
        Sprint sprint = new Sprint();
        sprint.setName("Sprint 1");
        sprint.setStatus(SprintStatus.PENDING);
        sprint.setStartDate(LocalDate.now().plusDays(205));
        sprint.setEndDate(LocalDate.now().plusDays(214));
        Sprint sprint2 = new Sprint();
        sprint2.setName("Sprint 2");
        sprint2.setStatus(SprintStatus.PENDING);
        sprint2.setStartDate(LocalDate.now().plusDays(201));
        sprint2.setEndDate(LocalDate.now().plusDays(212));
        Sprint sprint3 = new Sprint();
        sprint3.setName("Sprint 3");
        sprint3.setStatus(SprintStatus.PENDING);
        sprint3.setStartDate(LocalDate.now().plusDays(200));
        sprint3.setEndDate(LocalDate.now().plusDays(214));
        sprintService.addSprint(sprint);
        sprintService.addSprint(sprint2);
        sprintService.addSprint(sprint3);

        List<Sprint> sprints = sprintService.getSprintsFromGivenTime(LocalDate.now().
                plusDays(200), LocalDate.now().plusDays(214));

        sprintService.deleteSprintById(sprint.getId());
        sprintService.deleteSprintById(sprint2.getId());
        sprintService.deleteSprintById(sprint3.getId());

        assertEquals(3, sprints.size());
        assertEquals(sprint.getId(), sprints.get(0).getId());
        assertEquals(sprint2.getId(), sprints.get(1).getId());
        assertEquals(sprint3.getId(), sprints.get(2).getId());
    }

    @Test
    public void givenSprintAndUserStories_whenGetUserStoriesPointsFromSprint_thenSuccess() {
        UserStory userStory1 = new UserStory();
        userStory1.setName("User story 1");
        userStory1.setDescription("This is user story 1");
        userStory1.setStatus(UserStoryStatus.DONE);
        userStory1.setStoryPoints(15);
        UserStory userStory2 = new UserStory();
        userStory2.setName("User story 2");
        userStory2.setDescription("This is user story 2");
        userStory2.setStatus(UserStoryStatus.DONE);
        userStory2.setStoryPoints(26);
        UserStory userStory3 = new UserStory();
        userStory3.setName("User story 3");
        userStory3.setDescription("This is user story 3");
        userStory3.setStatus(UserStoryStatus.IN_PROGRESS);
        userStory3.setStoryPoints(32);
        userStoryService.addUserStory(userStory1);
        userStoryService.addUserStory(userStory2);
        userStoryService.addUserStory(userStory3);
        List<UserStory> userStories = List.of(userStory1, userStory2, userStory3);

        Integer points = 0;
        for(UserStory userStory : userStories) {
            if(userStory.getStatus() == UserStoryStatus.DONE)
                points += userStory.getStoryPoints();
        }
        Sprint sprint = new Sprint();
        sprint.setName("Sprint");
        sprint.setStatus(SprintStatus.PENDING);
        sprint.setStartDate(LocalDate.now());
        sprint.setEndDate(LocalDate.now().plusDays(21));
        sprint.setUserStories(userStories);
        sprintService.addSprint(sprint);

        Integer retrievedPoints = sprintService.getStoryPointsFromSprint(sprint.getId());

        sprintService.deleteSprintById(sprint.getId());
        userStoryService.deleteUserStoryById(userStory1.getId());
        userStoryService.deleteUserStoryById(userStory2.getId());
        userStoryService.deleteUserStoryById(userStory3.getId());

        assertEquals(points, retrievedPoints);
    }

    @Test
    public void givenSprints_whenGettingPaginatedAndSortedByDate_thenSuccess() {
        Page<Sprint> sprints = sprintService
                .getPaginatedAndSortedSprints(PageRequest.of(0, 10,
                        Sort.by(Sort.Order.desc("startDate"))));
        assertEquals(10, sprints.getSize());
        assertEquals(sprintService.getLastStartDate(), sprints.getContent().get(0).getStartDate());
    }
}
