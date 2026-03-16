package lr.afrilandfirstbankliberia.accountmappercbs.entity.primarykey;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;

@Embeddable
@Data
public class OperationId {

    @Column(name="CODOPE")
    String code;

//    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
//    @JoinColumn(name = "CODAGE", referencedColumnName = "CODAGE", insertable=false, updatable=false)
//    Branch branch;

//    @Column(name="CODAGE", insertable=false, updatable=false)
//    String branchCode;

    @Column(name="DTE_COMPTABLE")
    LocalDate accountingDate;

    @Column(name="NUMORDRE")
    int orderNumber;
}
