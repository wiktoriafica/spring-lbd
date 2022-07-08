package pl.fis.springlbdday2.repository.team;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import pl.fis.springlbdday2.entity.team.Team;

@Repository
public interface TeamRepository extends CrudRepository<Team, Long> {
}
