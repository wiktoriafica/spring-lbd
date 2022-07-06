package pl.fis.springlbd.service.employeeroleinteam;

import org.springframework.stereotype.Service;
import pl.fis.springlbd.service.employee.EmployeeService;
import pl.fis.springlbd.service.team.TeamService;

@Service
public class EmployeeRoleInTeamServiceImpl implements EmployeeRoleInTeamService {
    private final EmployeeService employeeService;
    private final TeamService teamService;

    public EmployeeRoleInTeamServiceImpl(EmployeeService employeeService, TeamService teamService) {
        this.employeeService = employeeService;
        this.teamService = teamService;
    }
}
