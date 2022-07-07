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
    @GeneratedValue
    private Long id;

    @Column(nullable = false, name = "description")
    @NotNull
    private String description;

    @Column(nullable = false, name = "story_points")
    @NotNull
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
}