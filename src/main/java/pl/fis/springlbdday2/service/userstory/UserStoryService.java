package pl.fis.springlbdday2.service.userstory;

import pl.fis.springlbdday2.entity.userstory.UserStory;

public interface UserStoryService {
    void addUserStory(UserStory userStory);

    UserStory getUserStoryById(Long id);
}
