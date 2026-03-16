package lr.afrilandfirstbankliberia.accountmappercbs.entity.primarykey;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Objects;

@Embeddable
@Data
public class AccountId {

    @Column(name="NUMCPTE")
    private String accountNumber;

//    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
//    @JoinColumn(name = "CODAGE", referencedColumnName = "CODAGE")
//    private Branch branch;

    @Column(name="CODAGE")
    private String branch;

//    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
//    @JoinColumn(name = "CODDEV", referencedColumnName = "CODDEV")
//    private Currency currency;

    @Column(name = "CODDEV")
    private String currency;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AccountId accountId = (AccountId) o;
        return Objects.equals(getAccountNumber(), accountId.getAccountNumber()) && Objects.equals(getBranch(), accountId.getBranch()) && Objects.equals(getCurrency(), accountId.getCurrency());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getAccountNumber(), getBranch(), getCurrency());
    }
}
