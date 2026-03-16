package lr.afrilandfirstbankliberia.accountmappercbs.entity;

import jakarta.persistence.*;
import lombok.Data;
import lr.afrilandfirstbankliberia.accountmappercbs.entity.primarykey.LiaisonAccountId;

@Entity
@Table(name="COMPTES_LIAISON")
@Data
public class LiaisonAccount {

    @EmbeddedId
    LiaisonAccountId liaisonAccountId;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumns({
            @JoinColumn(name = "NUMCPTE", referencedColumnName = "NUMCPTE"),
            @JoinColumn(name = "CODAGE", referencedColumnName = "CODAGE"),
            @JoinColumn(name = "CODDEV", referencedColumnName = "CODDEV"),
    })
    private Account account;



}
