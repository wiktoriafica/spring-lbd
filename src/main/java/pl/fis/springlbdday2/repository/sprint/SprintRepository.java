package pl.fis.springlbdday2.repository.sprint;

import org.springframework.data.repository.CrudRepository;
import pl.fis.springlbdday2.entity.sprint.Sprint;

public interface SprintRepository extends CrudRepository<Sprint, Long> {
}
