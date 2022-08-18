package pl.fis.springlbdday2.dto.userstory;

import lombok.Data;
import pl.fis.springlbdday2.dto.sprint.SprintGetDto;
import pl.fis.springlbdday2.entity.attachment.Attachment;
import pl.fis.springlbdday2.entity.enums.UserStoryStatus;

import java.io.Serializable;
import java.util.List;

@Data
public class UserStoryGetDto implements Serializable {
    private Long id;

    private String name;
    private Integer storyPoints;
    private UserStoryStatus status;
    private String description;
    private List<SprintGetDto> sprints;
    private List<Attachment> attachments;
}
