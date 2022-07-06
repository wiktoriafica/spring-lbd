package pl.fis.springlbd.service.project;

import org.springframework.stereotype.Service;
import pl.fis.springlbd.service.clientdepartment.ClientDepartmentService;

@Service
public class ProjectServiceImpl implements ProjectService {
    private final ClientDepartmentService clientDepartmentService;

    public ProjectServiceImpl(ClientDepartmentService clientDepartmentService) {
        this.clientDepartmentService = clientDepartmentService;
    }
}
