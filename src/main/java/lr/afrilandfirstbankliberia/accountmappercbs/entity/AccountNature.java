package lr.afrilandfirstbankliberia.accountmappercbs.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name="NATURE_COMPTE")
@Data
public class AccountNature {

    @Id
    @Column(name="CODNATCPTE")
    private String code;

    @Column(name="LIBNATCPTE")
    private String name;


}
