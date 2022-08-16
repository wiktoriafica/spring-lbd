package pl.fis.springlbdday2.event;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class UserStoryCreatedEvent {
    private Long userCreatedStoryId;
}
