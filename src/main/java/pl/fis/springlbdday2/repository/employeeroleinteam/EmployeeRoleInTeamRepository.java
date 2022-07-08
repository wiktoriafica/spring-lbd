package pl.fis.springlbdday2.repository.employeeroleinteam;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import pl.fis.springlbdday2.entity.employeeroleinteam.EmployeeRoleInTeam;

@Repository
public interface EmployeeRoleInTeamRepository extends CrudRepository<EmployeeRoleInTeam, Long> {
}
