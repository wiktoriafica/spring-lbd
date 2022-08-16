package pl.fis.springlbdday2.dto.userstory;

import lombok.Data;
import java.io.Serializable;

@Data
public class UserStoryDto implements Serializable {
    private String name;
    private Integer storyPoints;
}
