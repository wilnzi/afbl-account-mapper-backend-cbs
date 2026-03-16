package lr.afrilandfirstbankliberia.accountmappercbs.entity.primarykey;

import jakarta.persistence.*;
import lombok.Data;

@Embeddable
@Data
public class LiaisonAccountId {


    @Column(name = "CODAGE", insertable = false, updatable = false)
    private String sourceBranch;

    @Column(name = "CODAGEL")
    private String destinationBranch;

    @Column(name = "CODDEV", insertable = false, updatable = false)
    private String currency;

    @Column(name = "CODTYPEOPE")
    private String operationType;


//    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
//    @JoinColumn(name = "CODAGE", referencedColumnName = "CODAGE", insertable = false, updatable = false)
//    private Branch sourceBranch;
//
//    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
//    @JoinColumn(name = "CODAGEL", referencedColumnName = "CODAGE")
//    private Branch destinationBranch;
//
//    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
//    @JoinColumn(name = "CODDEV", referencedColumnName = "CODDEV", insertable = false, updatable = false)
//    private Currency currency;
//
//    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
//    @JoinColumn(name = "CODTYPEOPE", referencedColumnName = "CODTYPEOPE")
//    private OperationType operationType;
}
