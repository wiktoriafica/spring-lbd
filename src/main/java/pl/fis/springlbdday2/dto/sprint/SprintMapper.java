package pl.fis.springlbdday2.dto.sprint;

import org.mapstruct.*;
import pl.fis.springlbdday2.dto.userstory.UserStoryMapper;
import pl.fis.springlbdday2.entity.sprint.Sprint;

@Mapper(componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        uses = {UserStoryMapper.class})
public interface SprintMapper {
    @Mapping(target = "userStories", ignore = true)
    SprintGetDto getDtoFromSprint(Sprint sprint);

    @Mapping(target = "userStories", source = "userStories")
    SprintGetDto getDtoFromSprintWithUserStories(Sprint sprint);

    @Mapping(target = "userStories", source = "userStories")
    Sprint sprintPostDtoToSprint(SprintPostDto sprintPostDto);

    @Mapping(target = "id", ignore = true)
    Sprint updateSprintFromSprintPostDto(SprintPostDto sprintPostDto, @MappingTarget Sprint sprint);

}
