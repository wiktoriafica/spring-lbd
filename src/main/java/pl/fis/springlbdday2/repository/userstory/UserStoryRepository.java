package pl.fis.springlbdday2.repository.userstory;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import pl.fis.springlbdday2.entity.userstory.UserStory;

@Repository
public interface UserStoryRepository extends PagingAndSortingRepository<UserStory, Long> {
}
