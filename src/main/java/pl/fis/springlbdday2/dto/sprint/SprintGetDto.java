package pl.fis.springlbdday2.dto.sprint;

import com.sun.istack.NotNull;
import pl.fis.springlbdday2.dto.userstory.UserStoryDto;
import pl.fis.springlbdday2.entity.enums.SprintStatus;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

public class SprintGetDto {
    private Long id;
    private String name;
    private LocalDate startDate;
    private LocalDate endDate;
    private String goalDescription;
    private SprintStatus status;
    private List<UserStoryDto> userStories;

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

    public List<UserStoryDto> getUserStories() {
        return userStories;
    }

    public void setUserStories(List<UserStoryDto> userStories) {
        this.userStories = userStories;
    }
}
