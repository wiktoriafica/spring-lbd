package pl.fis.springlbdday2.service.team;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.fis.springlbdday2.repository.team.TeamRepository;

@Service
@RequiredArgsConstructor
public class TeamServiceImpl implements TeamService{
    private final TeamRepository teamRepository;
}
