package pl.fis.springlbdday2.entity.attachment;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "attachments")
@Getter
@Setter
public class Attachment {
    @Id
    @Column(name = "attachment_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "attachment")
    @Lob
    private byte[] attachment;
}
