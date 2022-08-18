package pl.fis.springlbdday2.userstory;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.transaction.support.TransactionSynchronizationManager;
import pl.fis.springlbdday2.dto.userstory.UserStoryPostDto;
import pl.fis.springlbdday2.entity.enums.UserStoryStatus;
import pl.fis.springlbdday2.entity.userstory.UserStory;
import pl.fis.springlbdday2.exception.InvalidDataException;
import pl.fis.springlbdday2.repository.userstory.UserStoryRepository;
import pl.fis.springlbdday2.service.userstory.UserStoryService;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class UserStoryServiceTest {

    @Autowired
    private UserStoryService userStoryService;
    @Autowired
    private UserStoryRepository userStoryRepository;

    @Test
    public void givenCorrectUserStory_whenAddingToDatabase_thenSuccess() {
        UserStoryPostDto userStory = new UserStoryPostDto();
        userStory.setName("User story 1");
        userStory.setDescription("This is user story 1");
        userStory.setStatus(UserStoryStatus.IN_PROGRESS);

        UserStory addedUserStory = userStoryService.addUserStory(userStory);

        assertThat(addedUserStory.getName()).isEqualTo(userStory.getName());
        assertThat(addedUserStory.getDescription()).isEqualTo(userStory.getDescription());
        assertThat(addedUserStory.getStatus()).isEqualTo(userStory.getStatus());
    }

    @Test
    public void givenIncorrectUserStory_whenAddingToDatabase_thenThrow() {
        UserStoryPostDto userStory = new UserStoryPostDto();
        InvalidDataException exception = assertThrows(InvalidDataException.class, () -> {
            userStoryService.addUserStory(userStory);
        });
        assertFalse(TransactionSynchronizationManager.isActualTransactionActive());
    }

    @Test
    public void givenRandomUserStories_whenGettingPages_thenSuccess() {
        Page<UserStory> userStories = userStoryRepository.findAll(PageRequest.of(0, 50));
        assertEquals(50, userStories.getSize());
        int id = 1;
        for (UserStory userStory : userStories) {
            assertEquals(id, userStory.getId());
            id++;
        }
    }
}
