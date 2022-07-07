package pl.fis.springlbdday2.entity.userstory;

import com.sun.istack.NotNull;
import pl.fis.springlbdday2.entity.enums.UserStoryStatus;
import pl.fis.springlbdday2.entity.sprint.Sprint;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "user_stories")
public class UserStory {
    @Id
    @Column(name = "user_story_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, name = "name")
    @NotNull
    private String name;

    @Column(nullable = false, name = "description")
    @NotNull
    private String description;

    @Column(name = "story_points")
    private Integer storyPoints;

    @Column(name = "attachments")
    @Lob
    private List<Byte> attachments;

    @Column(nullable = false, name = "status")
    @NotNull
    @Enumerated(EnumType.STRING)
    private UserStoryStatus status;

    @ManyToMany(mappedBy = "userStories")
    private List<Sprint> sprints;

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

    public List<Byte> getAttachments() {
        return attachments;
    }

    public void setAttachments(List<Byte> attachments) {
        this.attachments = attachments;
    }

    public UserStoryStatus getStatus() {
        return status;
    }

    public void setStatus(UserStoryStatus status) {
        this.status = status;
    }

    public List<Sprint> getSprints() {
        return sprints;
    }

    public void setSprints(List<Sprint> sprints) {
        this.sprints = sprints;
    }
}