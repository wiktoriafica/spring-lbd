package pl.fis.springlbdday2.repository.userstory;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import pl.fis.springlbdday2.entity.userstory.UserStory;

@Repository
public interface UserStoryRepository extends CrudRepository<UserStory, Long> {
}
