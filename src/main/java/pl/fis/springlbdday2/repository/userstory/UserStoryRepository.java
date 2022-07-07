package pl.fis.springlbdday2.repository.userstory;

import org.springframework.data.repository.CrudRepository;
import pl.fis.springlbdday2.entity.userstory.UserStory;

public interface UserStoryRepository extends CrudRepository<UserStory, Long> {
}
