package pl.fis.springlbdday2.dto.sprint;

import lombok.Data;
import pl.fis.springlbdday2.dto.userstory.UserStoryGetDto;
import pl.fis.springlbdday2.entity.enums.SprintStatus;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

@Data
public class SprintPostDto implements Serializable {
    private String name;
    private LocalDate startDate;
    private LocalDate endDate;
    private String goalDescription;
    private SprintStatus status;
    private List<UserStoryGetDto> userStories;
}
