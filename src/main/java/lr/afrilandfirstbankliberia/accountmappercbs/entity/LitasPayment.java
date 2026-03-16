package lr.afrilandfirstbankliberia.accountmappercbs.entity;

import jakarta.persistence.*;
import lombok.Data;
import lr.afrilandfirstbankliberia.accountmappercbs.model.enumerator.TransactionSourceEnum;
import lr.afrilandfirstbankliberia.accountmappercbs.model.enumerator.TransactionTypeEnum;
import lr.afrilandfirstbankliberia.accountmappercbs.model.enumerator.TransferStatusEnum;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name="LITAS_PAYMENT")
@Data
public class LitasPayment {

    /*@Id
    @Column(name="ID")
    @GeneratedValue(generator="LITAS_TRANSFER_ID_SEQUENCE")
    @SequenceGenerator(name="LITAS_TRANSFER_ID_SEQUENCE",sequenceName="LITAS_TRANSFER_ID_SEQUENCE", allocationSize=1)
    private Long id;*/

    @Column(name="CODE", unique = true)
    @Id
    private String code;

    @Column(name="CODAGE")
    private String branch;

    @Column(name="CODDEV")
    private String currencyCode;

    @Column(name="NUMCPTE")
    private String accountNumber;

    @Column(name="REQUETE")
    private String request;

    @Column(name="REPONSE")
    private String response;

    @Column(name="DATE_CREATION")
    private LocalDateTime creationDateTime;

    @Column(name="CLIENT_NOM")
    private String customerName;

    @Column(name="CLIENT_TIN")
    private String customerTIN;

    @Column(name="NUMERO_FACTURE")
    private String taxBillNumber;

    @Column(name="FRAIS")
    private BigDecimal fees;

    @Column(name="MONTANT_PRINCIPAL")
    private BigDecimal principalAmount;

    @Column(name="TAX")
    private BigDecimal tax;

    @Column(name="NOMBRE_LIGNES_OPERATION")
    private int numberOfLinesInOperationTable;

    @Column(name="ETAT")
    @Enumerated(EnumType.STRING)
    private TransferStatusEnum status;

    @Column(name="REFERENCE_EXTERNE")
    private String externalReference;

    @Column(name="REFERENCE_INTERNE")
    private String internalReference;

    @Column(name="DATE_DEBUT")
    private LocalDateTime startExecutionDateTime;

    @Column(name="DATE_FIN")
    private LocalDateTime endExecutionDateTime;

    @Column(name="CODUTI")
    private String userCode;

    @Enumerated(EnumType.STRING)
    @Column(name="TYPE_TRANSACTION")
    private TransactionTypeEnum transactionType;

    @Enumerated(EnumType.STRING)
    @Column(name="SOURCE_TRANSACTION")
    private TransactionSourceEnum transactionSource;


}
