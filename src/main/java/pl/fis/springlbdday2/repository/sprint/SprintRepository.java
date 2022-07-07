package pl.fis.springlbdday2.repository.sprint;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import pl.fis.springlbdday2.entity.sprint.Sprint;

@Repository
public interface SprintRepository extends CrudRepository<Sprint, Long> {
}
