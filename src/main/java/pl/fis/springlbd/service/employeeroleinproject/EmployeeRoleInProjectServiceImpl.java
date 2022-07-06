package pl.fis.springlbd.service.employeeroleinproject;

import org.springframework.stereotype.Service;
import pl.fis.springlbd.service.employee.EmployeeService;
import pl.fis.springlbd.service.project.ProjectService;

@Service
public class EmployeeRoleInProjectServiceImpl implements EmployeeRoleInProjectService {
    private final EmployeeService employeeService;
    private final ProjectService projectService;

    public EmployeeRoleInProjectServiceImpl(EmployeeService employeeService, ProjectService projectService) {
        this.employeeService = employeeService;
        this.projectService = projectService;
    }
}
