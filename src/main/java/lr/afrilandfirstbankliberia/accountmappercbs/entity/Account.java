package lr.afrilandfirstbankliberia.accountmappercbs.entity;

import jakarta.persistence.*;
import lombok.Data;
import lr.afrilandfirstbankliberia.accountmappercbs.entity.primarykey.AccountId;

import java.math.BigDecimal;
import java.util.Objects;

@Entity
@Table(name="COMPTES")
@Data
public class Account {

    @EmbeddedId
    private AccountId accountId;

//    @Id
//    @Column(name="NUMCPTE")
//    private String accountNumber;

    @Column(name="LIBCPTE")
    private String name;

//    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
//    @JoinColumn(name = "CODAGE", referencedColumnName = "CODAGE")
//    private Branch branch;

    @Column(name="ETATCPTE")
    private String status;

//    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
//    @JoinColumn(name = "CODDEV", referencedColumnName = "CODDEV")
//    private Currency currency;

//    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
//    @JoinColumn(name = "CODNATCPTE", referencedColumnName = "CODNATCPTE")
//    private AccountNature nature;

    @Column(name="CODNATCPTE")
    private String nature;

    @Column(name="CODMUT")
    private String customerCode;

    @Column(name="SOLDE_JOURNALIER")
    private BigDecimal dailyBalance;

    @Column(name="SOLDE_COMPTABLE")
    private BigDecimal accountingBalance;

    public String getBranch() throws Exception {
        if(this.getAccountId().getBranch() == null) throw new Exception("Branche entity is not present");
        return this.getAccountId().getBranch();
    }

    public String getCurrency() throws Exception {
        if(this.getAccountId().getCurrency() == null) throw new Exception("Currency entity is not present");
        return this.getAccountId().getCurrency();
    }

    public void generateAccountId(){
        if(this.accountId == null){
            this.accountId = new AccountId();
        }
    }

    public void setAccountNumber(String accountNumber){
        this.generateAccountId();
        this.accountId.setAccountNumber(accountNumber);
    }

    public String getAccountNumber(){
        return this.accountId.getAccountNumber();
    }



//
//    public void setBranch(Branch branch){
//        this.generateAccountId();
//        this.accountId.setBranch(branch);
//    }
//
//    public Branch getBranch(){
//        return this.accountId.getBranch();
//    }
//
//
//    public void setCurrency(Currency currency){
//        this.generateAccountId();
//        this.accountId.setCurrency(currency);
//    }
//
//    public Currency getCurrency(){
//        return this.accountId.getCurrency();
//    }


    public void setBranch(String branch){
        this.generateAccountId();
        this.accountId.setBranch(branch);
    }



    public void setCurrency(String currency){
        this.generateAccountId();
        this.accountId.setCurrency(currency);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Account account = (Account) o;
        return Objects.equals(getAccountId(), account.getAccountId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getAccountId());
    }
}
