package lr.afrilandfirstbankliberia.accountmappercbs.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Table(name="LITAS_PARAMETER")
@Data
public class LitasParameter {

    @Column(name = "ID")
    @Id
    Long id;

    @Column(name = "CODE")
    String code;

    @Column(name = "CODAGE")
    String branchCode;

    @Column(name="VALEUR")
    String value;

    @Column(name="CODDEV")
    String currencyCode;

    @Column(name="DATE_CREATION")
    LocalDateTime createdDateTime;


}
