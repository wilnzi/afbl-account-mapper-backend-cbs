package lr.afrilandfirstbankliberia.accountmappercbs.repository;

import jakarta.transaction.Transactional;
import lr.afrilandfirstbankliberia.accountmappercbs.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;

@Repository
public interface AccountRepository extends JpaRepository<Account, String> {


    @Query(value =" SELECT c.numcpte,c.codage,c.coddev,c.solde_comptable,c.codmut,c.solde_journalier,c.libcpte,c.codnatcpte,c.etatcpte " +
            " FROM comptes c " +
            " INNER JOIN devise d ON d.coddev = c.coddev " +
            " LEFT JOIN LITAS_PARAMS pp ON pp.CODE LIKE 'BANK_LITAS_TELLER_ACCOUNT%' AND pp.CODAGE = c.CODAGE AND pp.VALEUR = c.NUMCPTE " +
            " WHERE  c.codage = :agencyCode AND c.numcpte = :accountNumber AND c.CODDEV = :currencyCode" +
            " AND (c.codmut IS NOT NULL OR pp.VALEUR IS NOT NULL)" , nativeQuery = true)
    Account findCustomerAndTellerAccountByAccountNumber(@Param("agencyCode") String agencyCode, @Param("accountNumber") String accountNumber,
                                          @Param("currencyCode")String currencyCode);



    @Query(value = " SELECT a FROM Account a " +
            "  WHERE accountId.branch = :branchCode " +
            "  AND accountId.accountNumber = :accountNumber " +
            "  AND accountId.currency = :currencyCode ")
    Account findAnyAccountWithCompleteAccountNumber(@Param("branchCode") String branchCode,
                                                    @Param("accountNumber") String accountNumber, @Param("currencyCode") String currencyCode);


    @Query(value = "SELECT a FROM Account a " +
            " INNER JOIN Currency c ON a.accountId.currency = c.code AND c.shortCode = :currencyShortCode " +
            " WHERE (accountId.branch  || accountId.accountNumber) = :accountNumber")
    Account findAnyAccountByAccountNumber(@Param("accountNumber") String accountNumber, @Param("currencyShortCode")String currencyShortCode);





    @Modifying
    @Transactional(Transactional.TxType.MANDATORY)
    @Query(value=" update comptes  set solde_journalier = solde_journalier + (:amount) " +
            " where  numcpte = :accountNumber  and codage = :branchCode and coddev = :currencyCode ", nativeQuery = true)
    void updateDailyBalance(@Param("branchCode")String branchCode, @Param("accountNumber")String accountNumber,
                            @Param("currencyCode")String currencyCode, @Param("amount") BigDecimal amount);

}
