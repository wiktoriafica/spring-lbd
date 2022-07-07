package pl.fis.springlbdday2.userstory;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.support.TransactionSynchronizationManager;
import pl.fis.springlbdday2.entity.enums.SprintStatus;
import pl.fis.springlbdday2.entity.enums.UserStoryStatus;
import pl.fis.springlbdday2.entity.sprint.Sprint;
import pl.fis.springlbdday2.entity.userstory.UserStory;
import pl.fis.springlbdday2.exception.InvalidDataException;
import pl.fis.springlbdday2.service.userstory.UserStoryService;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class UserStoryServiceTest {

    @Autowired
    private UserStoryService userStoryService;

    @Test
    public void givenCorrectUserStory_whenAddingToDatabase_thenSuccess() {
        UserStory userStory = new UserStory();
        userStory.setName("User story 1");
        userStory.setDescription("This is user story 1");
        userStory.setStatus(UserStoryStatus.IN_PROGRESS);

        userStoryService.addUserStory(userStory);
        UserStory addedUserStory = userStoryService.getUserStoryById(userStory.getId());

        assertThat(addedUserStory.getId()).isEqualTo(userStory.getId());
        assertThat(addedUserStory.getName()).isEqualTo(userStory.getName());
        assertThat(addedUserStory.getDescription()).isEqualTo(userStory.getDescription());
        assertThat(addedUserStory.getStatus()).isEqualTo(userStory.getStatus());
    }

    @Test
    public void givenIncorrectUserStory_whenAddingToDatabase_thenThrow() {
        UserStory userStory = new UserStory();

        InvalidDataException exception = assertThrows(InvalidDataException.class, () -> {
            userStoryService.addUserStory(userStory);
        });

        assertFalse(TransactionSynchronizationManager.isActualTransactionActive());
        assertNull(userStory.getId());
    }
}
