package pl.fis.springlbdday2.service.employeeroleinteam;

import org.springframework.stereotype.Service;
import pl.fis.springlbdday2.repository.employeeroleinteam.EmployeeRoleInTeamRepository;

@Service
public class EmployeeRoleInTeamServiceImpl implements EmployeeRoleInTeamService {
    private final EmployeeRoleInTeamRepository employeeRoleInTeamRepository;

    public EmployeeRoleInTeamServiceImpl(EmployeeRoleInTeamRepository employeeRoleInTeamRepository) {
        this.employeeRoleInTeamRepository = employeeRoleInTeamRepository;
    }
}
