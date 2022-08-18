package pl.fis.springlbdday2.component;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import pl.fis.springlbdday2.dto.userstory.UserStoryPostDto;
import pl.fis.springlbdday2.entity.enums.UserStoryStatus;
import pl.fis.springlbdday2.service.userstory.UserStoryService;

@Component
@Order(0)
@RequiredArgsConstructor
public class UserStoriesDataSeeder implements CommandLineRunner {

    private final UserStoryService userStoryService;

    @Override
    public void run(String... args) throws Exception {
        seedUserStories();
    }

    public void seedUserStories() {
        for(int i = 0; i < 100; i++) {
            UserStoryPostDto userStory = new UserStoryPostDto();
            userStory.setStoryPoints((int) (Math.random() * 100.0 + 1));
            userStory.setStatus(UserStoryStatus.values()[(int) (Math.random() * 4.0)]);
            userStory.setName("User story nr " + i );
            userStory.setDescription("User story description nr " + i);
            userStoryService.addUserStory(userStory);
        }
    }
}
