package pl.fis.springlbdday2.service.employeeroleinteam;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.fis.springlbdday2.repository.employeeroleinteam.EmployeeRoleInTeamRepository;

@Service
@RequiredArgsConstructor
public class EmployeeRoleInTeamServiceImpl implements EmployeeRoleInTeamService {
    private final EmployeeRoleInTeamRepository employeeRoleInTeamRepository;
}
