package pl.fis.springlbdday2.userstory;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import pl.fis.springlbdday2.controller.userstory.UserStoryController;
import pl.fis.springlbdday2.dto.userstory.UserStoryGetDto;
import pl.fis.springlbdday2.dto.userstory.UserStoryPostDto;
import pl.fis.springlbdday2.entity.enums.UserStoryStatus;
import pl.fis.springlbdday2.service.sprint.SprintService;
import pl.fis.springlbdday2.service.userstory.UserStoryService;

import javax.persistence.EntityNotFoundException;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class UserStoryControllerTest {

    @Autowired
    private UserStoryController userStoryController;
    @Autowired
    private UserStoryService userStoryService;
    @Autowired
    private SprintService sprintService;

    @Test
    public void givenUserStoryInSprint_whenAddingToDatabase_thenSuccess() {
        UserStoryPostDto userStoryPostDto = new UserStoryPostDto();
        userStoryPostDto.setName("User story post dto");
        userStoryPostDto.setDescription("User story post dto");
        userStoryPostDto.setStoryPoints(10);
        userStoryPostDto.setStatus(UserStoryStatus.DONE);
        userStoryController.addUserStory(userStoryPostDto, 3L);

        Long id = userStoryService.findUserStoryByName(userStoryPostDto.getName()).getId();
        List<UserStoryGetDto> userStories = sprintService.getUsersStoriesBySprintId(3L);

        assertEquals(id, userStories.get(userStories.size() - 1).getId());
    }

    @Test
    public void givenUserStory_whenDeleted_thenSuccess() {
        UserStoryPostDto userStoryPostDto = new UserStoryPostDto();
        userStoryPostDto.setName("User story post dto");
        userStoryPostDto.setDescription("User story post dto");
        userStoryPostDto.setStoryPoints(10);
        userStoryPostDto.setStatus(UserStoryStatus.DONE);
        userStoryController.addUserStory(userStoryPostDto, 3L);

        Long id = userStoryService.findUserStoryByName(userStoryPostDto.getName()).getId();
        userStoryController.deleteUserStory(id);

        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class, () -> {
            UserStoryGetDto userStory = userStoryService.getUserStoryById(id);
        });
    }
}
