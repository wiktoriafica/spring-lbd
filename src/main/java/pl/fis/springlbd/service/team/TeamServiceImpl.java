package pl.fis.springlbd.service.team;

import org.springframework.stereotype.Service;
import pl.fis.springlbd.service.project.ProjectService;

@Service
public class TeamServiceImpl implements TeamService{
    private final ProjectService projectService;

    public TeamServiceImpl(ProjectService projectService) {
        this.projectService = projectService;
    }
}
