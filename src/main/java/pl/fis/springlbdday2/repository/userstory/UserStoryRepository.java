package pl.fis.springlbdday2.repository.userstory;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import pl.fis.springlbdday2.entity.userstory.UserStory;

import java.util.List;

@Repository
public interface UserStoryRepository extends PagingAndSortingRepository<UserStory, Long> {
    @Query("SELECT u FROM Sprint s JOIN s.userStories u WHERE s.id=:id")
    List<UserStory> getUserStoriesBySprintId(Long id);
    @Override
    List<UserStory> findAll();

    UserStory findByName(String name);
}
