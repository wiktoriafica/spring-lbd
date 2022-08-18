package pl.fis.springlbdday2.dto.attachment;

import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;
import pl.fis.springlbdday2.entity.attachment.Attachment;

@Mapper(componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface AttachmentMapper {

    AttachmentGetDto getDtoFromAttachment(Attachment attachment);

    Attachment getAttachmentFromDto(AttachmentPostDto attachmentPostDto);
}
