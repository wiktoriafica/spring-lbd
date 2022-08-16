package pl.fis.springlbdday2.dto.userstory;

import lombok.Data;
import pl.fis.springlbdday2.entity.enums.UserStoryStatus;

@Data
public class UserStoryPostDto {
    private String name;
    private String description;
    private Integer storyPoints;
    private UserStoryStatus status;
}
