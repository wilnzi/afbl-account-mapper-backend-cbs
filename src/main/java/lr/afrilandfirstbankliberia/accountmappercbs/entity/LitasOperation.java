package lr.afrilandfirstbankliberia.accountmappercbs.entity;

import jakarta.persistence.*;
import lombok.Data;
import lr.afrilandfirstbankliberia.accountmappercbs.entity.primarykey.OperationId;
import lr.afrilandfirstbankliberia.accountmappercbs.model.enumerator.OperationStatusEnum;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name="LITAS_OPERATIONS")
@Data
public class LitasOperation {

    @EmbeddedId
    OperationId operationId;

//    @Column(name="CODOPE")
//    String code;

    @Column(name="LIBOPE")
    String label;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "CODTYPEOPE", referencedColumnName = "CODTYPEOPE")
    private OperationType operationType;

    @Column(name="CODUTI")
    String userCode;

    @Column(name="DTEMOD")
    LocalDateTime updatedDate;

    @Column(name="MVTSUBIT")
    char movement;

//    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
//    @JoinColumn(name = "CODAGE", referencedColumnName = "CODAGE")
//    Branch branch;

    @Column(name="NOM_CLIENT")
    String customerName = "";

    @Column(name="NUM_PIECE_ID")
    String idCardNumber = "";

    @Column(name="DEBIT")
    BigDecimal debitAmount;

    @Column(name="CREDIT")
    BigDecimal creditAmount;

    @Column(name="DTE_VALEUR")
    LocalDateTime valueDate;

    @Column(name="REFPIECE")
    String referenceId;

//    @Column(name="DTE_COMPTABLE")
//    LocalDateTime accountingDate;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumns({
            @JoinColumn(name = "NUMCPTE", referencedColumnName = "NUMCPTE"),
            @JoinColumn(name = "CODAGE", referencedColumnName = "CODAGE"),
            @JoinColumn(name = "CODDEV", referencedColumnName = "CODDEV"),
    })
    Account account;

//    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
//    @JoinColumn(name = "CODDEV", referencedColumnName = "CODDEV", insertable=false, updatable=false)
//    Currency currency;

    @Column(name="EXERCICE")
    String exercise;

//    @Column(name="NUMORDRE")
//    int orderNumber;

    @Column(name="ETATOPE")
    @Enumerated(EnumType.STRING)
    OperationStatusEnum status;
}
