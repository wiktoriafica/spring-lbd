package pl.fis.springlbdday2.entity.sprint;

import com.sun.istack.NotNull;
import pl.fis.springlbdday2.entity.enums.SprintStatus;
import pl.fis.springlbdday2.entity.userstory.UserStory;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "sprints")
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
    @Column(nullable = false, name = "status")
    @NotNull
    @Enumerated(EnumType.STRING)
    private SprintStatus status;

    @ManyToMany
    @JoinTable(
            name = "user_stories_in_sprints",
            joinColumns = @JoinColumn(name = "sprint_id"),
            inverseJoinColumns = @JoinColumn(name = "user_story_id"))
    private List<UserStory> userStories;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public String getGoalDescription() {
        return goalDescription;
    }

    public void setGoalDescription(String goalDescription) {
        this.goalDescription = goalDescription;
    }

    public SprintStatus getStatus() {
        return status;
    }

    public void setStatus(SprintStatus status) {
        this.status = status;
    }

    public List<UserStory> getUserStories() {
        return userStories;
    }

    public void setUserStories(List<UserStory> userStories) {
        this.userStories = userStories;
    }
}