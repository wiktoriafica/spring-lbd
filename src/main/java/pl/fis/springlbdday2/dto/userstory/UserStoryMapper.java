package pl.fis.springlbdday2.dto.userstory;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValuePropertyMappingStrategy;
import pl.fis.springlbdday2.entity.userstory.UserStory;

@Mapper(componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface UserStoryMapper {
    UserStoryDto getDtoFromUserStory(UserStory userStory);

    @Mapping(target = "sprints", ignore = true)
    UserStoryGetDto getUserStoryGetDtoFromUserStory(UserStory userStory);
    UserStory convertDtoToUserStory(UserStoryPostDto userStoryPostDto);
}
