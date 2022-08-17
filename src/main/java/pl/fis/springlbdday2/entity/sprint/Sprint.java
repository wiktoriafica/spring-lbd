package pl.fis.springlbdday2.entity.sprint;

import com.sun.istack.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pl.fis.springlbdday2.entity.enums.SprintStatus;
import pl.fis.springlbdday2.entity.userstory.UserStory;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "sprints")
@Getter
@Setter
@NoArgsConstructor
public class Sprint {
    @Id
    @Column(name = "sprint_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, name = "name")
    @NotNull
    private String name;

    @Column(nullable = false, name = "start_date")
    @NotNull
    private LocalDate startDate;
    @Column(nullable = false, name = "end_date")
    @NotNull
    private LocalDate endDate;
    @Column(name = "goal_description")
    private String goalDescription;
    @Column(nullable = false, name = "sprint_status")
    @NotNull
    @Enumerated(EnumType.STRING)
    private SprintStatus status;

    @ManyToMany
    @JoinTable(
            name = "user_stories_in_sprints",
            joinColumns = @JoinColumn(name = "sprint_id"),
            inverseJoinColumns = @JoinColumn(name = "user_story_id"))
    private List<UserStory> userStories = new ArrayList<>();

    public Sprint(String name,
                  LocalDate startDate,
                  LocalDate endDate,
                  String goalDescription,
                  SprintStatus status,
                  List<UserStory> userStories) {
        this.name = name;
        this.startDate = startDate;
        this.endDate = endDate;
        this.goalDescription = goalDescription;
        this.status = status;
        this.userStories = userStories;
    }
}