package pl.fis.springlbdday2.dto.attachment;

import lombok.Data;

@Data
public class AttachmentGetDto {
    private Long id;
    private byte[] attachment;
    private String contentType;
    private String fileName;
}
