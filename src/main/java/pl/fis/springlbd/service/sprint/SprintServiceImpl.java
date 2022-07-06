package pl.fis.springlbd.service.sprint;

import org.springframework.stereotype.Service;
import pl.fis.springlbd.service.project.ProjectService;

@Service
public class SprintServiceImpl implements SprintService {
    private final ProjectService projectService;

    public SprintServiceImpl(ProjectService projectService) {
        this.projectService = projectService;
    }
}
