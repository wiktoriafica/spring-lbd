package pl.fis.springlbdday2.dto.userstory;

import com.sun.istack.NotNull;
import pl.fis.springlbdday2.dto.sprint.SprintGetDto;
import pl.fis.springlbdday2.entity.enums.UserStoryStatus;

import java.io.Serializable;
import java.util.List;

public class UserStoryGetDto implements Serializable {
    private Long id;

    private String name;
    private Integer storyPoints;

    private UserStoryStatus status;
    private List<SprintGetDto> sprints;

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

    public Integer getStoryPoints() {
        return storyPoints;
    }

    public void setStoryPoints(Integer storyPoints) {
        this.storyPoints = storyPoints;
    }

    public UserStoryStatus getStatus() {
        return status;
    }

    public void setStatus(UserStoryStatus status) {
        this.status = status;
    }

    public List<SprintGetDto> getSprints() {
        return sprints;
    }

    public void setSprints(List<SprintGetDto> sprints) {
        this.sprints = sprints;
    }
}
