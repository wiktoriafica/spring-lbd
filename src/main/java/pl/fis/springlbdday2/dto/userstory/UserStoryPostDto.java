package pl.fis.springlbdday2.dto.userstory;

import pl.fis.springlbdday2.entity.enums.UserStoryStatus;

import java.io.Serializable;

public class UserStoryPostDto {
    private String name;
    private String description;
    private Integer storyPoints;
    private UserStoryStatus status;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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
}
