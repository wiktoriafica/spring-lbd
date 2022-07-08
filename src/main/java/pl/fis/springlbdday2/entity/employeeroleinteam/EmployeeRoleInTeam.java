package pl.fis.springlbdday2.entity.employeeroleinteam;

import pl.fis.springlbdday2.entity.employee.Employee;
import pl.fis.springlbdday2.entity.enums.EmployeeRole;
import pl.fis.springlbdday2.entity.team.Team;

import javax.persistence.*;

@Entity
@Table(name = "employee_role_in_team")
public class EmployeeRoleInTeam {

    @EmbeddedId
    private EmployeeRoleInTeamKey id;

    @ManyToOne
    @MapsId("teamId")
    @JoinColumn(name = "student_id")
    private Team team;

    @ManyToOne
    @MapsId("employeeId")
    @JoinColumn(name = "course_id")
    private Employee employee;

    @Column(name = "employee_role")
    private EmployeeRole employeeRole;

}