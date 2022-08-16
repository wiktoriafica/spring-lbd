package pl.fis.springlbdday2.entity.team;

import com.sun.istack.NotNull;
import lombok.Getter;
import lombok.Setter;
import pl.fis.springlbdday2.entity.enums.TeamDepartment;

import javax.persistence.*;

@Entity
@Table(name = "teams")
@Getter
@Setter
public class Team {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "team_id")
    private Long id;

    @Column(name = "team_department")
    @NotNull
    private TeamDepartment teamDepartment;
}