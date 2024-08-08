package salaba.entity;

import lombok.Getter;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;

@EntityListeners(AuditingEntityListener.class)
@MappedSuperclass
@Getter
public class FileBaseEntity extends BaseEntity {
    @Column(nullable = false)
    private String originalFileName;

    @Column(nullable = false, unique = true)
    private String uuidFileName;

}
