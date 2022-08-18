package pl.fis.springlbdday2.dto.attachment;

import lombok.Data;

@Data
public class AttachmentPostDto {
    private byte[] attachment;
    private String contentType;
    private String fileName;
}
