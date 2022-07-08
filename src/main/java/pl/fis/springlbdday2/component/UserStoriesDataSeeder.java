package pl.fis.springlbdday2.component;

import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import pl.fis.springlbdday2.entity.enums.UserStoryStatus;
import pl.fis.springlbdday2.entity.sprint.Sprint;
import pl.fis.springlbdday2.entity.userstory.UserStory;
import pl.fis.springlbdday2.service.sprint.SprintService;
import pl.fis.springlbdday2.service.userstory.UserStoryService;

import java.util.List;

@Component
@Order(0)
public class UserStoriesDataSeeder implements CommandLineRunner {

    private final UserStoryService userStoryService;
    private final SprintService sprintService;

    public UserStoriesDataSeeder(UserStoryService userStoryService,
                                 SprintService sprintService) {
        this.userStoryService = userStoryService;
        this.sprintService = sprintService;
    }

    @Override
    public void run(String... args) throws Exception {
        seedUserStories();
    }

    public void seedUserStories() {
        for(int i = 0; i < 100; i++) {
            UserStory userStory = new UserStory();
            userStory.setStoryPoints((int) (Math.random() * 100.0 + 1));
            userStory.setStatus(UserStoryStatus.values()[(int) (Math.random() * 4.0)]);
            userStory.setName("User story nr " + i );
            userStory.setDescription("User story description nr " + i);
            userStoryService.addUserStory(userStory);
        }
    }
}
