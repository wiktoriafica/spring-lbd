package pl.fis.springlbdday2.dto.sprint;

import org.mapstruct.*;
import pl.fis.springlbdday2.entity.sprint.Sprint;

@Mapper(componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface SprintMapper {
    @Mapping(target = "userStories", ignore = true)
    SprintGetDto getDtoFromSprint(Sprint sprint);

    SprintGetDto getDtoFromSprintWithUserStories(Sprint sprint);

    Sprint sprintPostDtoToSprint(SprintPostDto sprintPostDto);

    @Mapping(target = "id", ignore = true)
    Sprint updateSprintFromSprintPostDto(SprintPostDto sprintPostDto, @MappingTarget Sprint sprint);

}
