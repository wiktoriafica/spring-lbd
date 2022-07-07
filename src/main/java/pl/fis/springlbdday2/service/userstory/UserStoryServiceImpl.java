package pl.fis.springlbdday2.service.userstory;

import org.hibernate.exception.ConstraintViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.fis.springlbdday2.entity.enums.UserStoryStatus;
import pl.fis.springlbdday2.entity.userstory.UserStory;
import pl.fis.springlbdday2.exception.InvalidDataException;
import pl.fis.springlbdday2.repository.userstory.UserStoryRepository;

import javax.annotation.PostConstruct;
import javax.validation.Valid;

@Service
public class UserStoryServiceImpl implements UserStoryService{
    private final UserStoryRepository userStoryRepository;

    public UserStoryServiceImpl(UserStoryRepository userStoryRepository) {
        this.userStoryRepository = userStoryRepository;
    }

    @Override
    @Transactional(rollbackFor = InvalidDataException.class)
    public void addUserStory(UserStory userStory) {
        if(userStory.getStatus() == null)
            userStory.setStatus(UserStoryStatus.TO_DO);
        if(userStory.getName() != null && userStory.getDescription() != null)
            userStoryRepository.save(userStory);
        else throw new InvalidDataException("Violations of constraints in user stories");
    }

    @PostConstruct
    private void addUserStories() {
        UserStory userStory = new UserStory();
        userStory.setName("User story");
        userStory.setStoryPoints(15);
        userStory.setDescription("This is another user story");
        userStory.setStatus(UserStoryStatus.DONE);

        UserStory userStory1 = new UserStory();
        userStory1.setName("User story1");
        userStory1.setStoryPoints(54);
        userStory1.setDescription("This is yet another user story");

        UserStory userStory2 = new UserStory();
        userStory2.setName("User story2");
        userStory2.setDescription("This is another another user story");
        userStory2.setStatus(UserStoryStatus.IN_PROGRESS);
        try {
            addUserStory(userStory);
            addUserStory(userStory1);
            addUserStory(userStory2);
        } catch(InvalidDataException exception) {}
    }
}
