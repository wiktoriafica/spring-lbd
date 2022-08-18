package pl.fis.springlbdday2.repository.attachment;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import pl.fis.springlbdday2.entity.attachment.Attachment;

@Repository
public interface AttachmentRepository extends CrudRepository<Attachment, Long> {
}
