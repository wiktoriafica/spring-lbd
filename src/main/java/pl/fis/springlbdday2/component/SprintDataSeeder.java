package pl.fis.springlbdday2.component;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import pl.fis.springlbdday2.entity.enums.SprintStatus;
import pl.fis.springlbdday2.entity.sprint.Sprint;
import pl.fis.springlbdday2.entity.userstory.UserStory;
import pl.fis.springlbdday2.service.sprint.SprintService;
import pl.fis.springlbdday2.service.userstory.UserStoryService;

import java.time.LocalDate;
import java.util.List;

@Component
@Order(1)
@RequiredArgsConstructor
public class SprintDataSeeder implements CommandLineRunner {

    private final SprintService sprintService;
    private final UserStoryService userStoryService;

    @Override
    public void run(String... args) throws Exception {
        seedSprints();
    }

    public void seedSprints() {
        for(int i = 0; i < 30; i ++) {
            Sprint sprint = new Sprint();
            sprint.setStatus(SprintStatus.values()[(int) (Math.random() * 4.0)]);
            sprint.setName("Sprint nr " + i);
            sprint.setStartDate(LocalDate.now().plusDays((long) (Math.random() * 100.0)));
            sprint.setEndDate(sprint.getStartDate().plusDays((long) (Math.random() * 21.0) + 7));
            List<UserStory> userStories = List.of(userStoryService.getUserStoryById((long) (Math.random() * 40.0 + 10)),
                    userStoryService.getUserStoryById((long) (Math.random() * 40.0 + 50)));
            sprint.setUserStories(userStories);
            sprintService.addSprint(sprint);
        }
    }
}
