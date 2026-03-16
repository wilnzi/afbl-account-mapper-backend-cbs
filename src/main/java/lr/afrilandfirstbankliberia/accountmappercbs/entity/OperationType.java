package lr.afrilandfirstbankliberia.accountmappercbs.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name="TYPE_OPERATION")
@Data
public class OperationType {

    @Id
    @Column(name="CODTYPEOPE")
    private String code;

    @Column(name="LIBTYPEOPE")
    private String label;

    @Column(name="LIBECRIT")
    private String writingLabel;

    @Column(name="LIBECRITCOMM")
    private String writingComment;


}
