package pl.fis.springlbdday2.service.team;

import org.springframework.stereotype.Service;
import pl.fis.springlbdday2.repository.team.TeamRepository;

@Service
public class TeamServiceImpl implements TeamService{

    private final TeamRepository teamRepository;

    public TeamServiceImpl(TeamRepository teamRepository) {
        this.teamRepository = teamRepository;
    }
}
