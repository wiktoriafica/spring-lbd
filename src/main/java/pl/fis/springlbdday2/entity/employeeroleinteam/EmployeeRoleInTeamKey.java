package pl.fis.springlbdday2.entity.employeeroleinteam;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class EmployeeRoleInTeamKey implements Serializable {
    @Column(name = "team_id")
    Long teamId;

    @Column(name = "employee_id")
    Long employeeId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EmployeeRoleInTeamKey that = (EmployeeRoleInTeamKey) o;
        return teamId.equals(that.teamId) && employeeId.equals(that.employeeId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(teamId, employeeId);
    }
}
