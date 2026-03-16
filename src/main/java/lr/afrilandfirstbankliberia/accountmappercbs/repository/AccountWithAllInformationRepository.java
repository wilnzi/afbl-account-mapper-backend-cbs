package lr.afrilandfirstbankliberia.accountmappercbs.repository;

import lr.afrilandfirstbankliberia.accountmappercbs.dto.PersonDTO;
import lr.afrilandfirstbankliberia.accountmappercbs.entity.*;
import lr.afrilandfirstbankliberia.accountmappercbs.model.data.AccountWithAllInformation;
import lr.afrilandfirstbankliberia.accountmappercbs.model.data.CompleteAccountNumber;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.Map;
import java.util.List;

@Repository
public class AccountWithAllInformationRepository  {

    @Autowired
    JdbcTemplate jdbcTemplate;

    public AccountWithAllInformation getAccountInformationWithOpposition(String accountNumber) throws Exception{
        String query = " SELECT c.NUMCPTE, c.LIBCPTE, c.ETATCPTE, c.CODMUT, c.SOLDE_JOURNALIER, c.SOLDE_COMPTABLE, a.CODAGE," +
                " a.NOMAGE, d.CODDEV, d.LIBCOURTDEV, nc.CODNATCPTE, nc.LIBNATCPTE, " +
                " (SELECT COUNT(*) FROM OPPO_CPTE oc WHERE  oc.NUMCPTE = c.NUMCPTE AND oc.CODAGE = c.CODAGE AND oc.CODTYPEOPP = 'DORM' ) AS OPPO_DORMANT" +
                " FROM COMPTES c " +
                " INNER JOIN AGENCE a ON a.CODAGE = c.CODAGE  " +
                " INNER JOIN DEVISE d ON d.CODDEV = c.CODDEV " +
                " INNER JOIN NATURE_COMPTE nc ON nc.CODNATCPTE = c.CODNATCPTE " +
                " WHERE c.NUMCPTE = ? AND CODMUT IS NOT NULL";

        Map<String, Object> accountInformation = this.jdbcTemplate.queryForMap(query, new Object[] { accountNumber });

        AccountWithAllInformation accountWithAllInformation = new AccountWithAllInformation();
        accountWithAllInformation.setNumberOfSleepingTypeOfOpposition((BigDecimal)accountInformation.get("OPPO_DORMANT"));
        accountWithAllInformation.setAccountNumber((String)accountInformation.get("NUMCPTE"));
        accountWithAllInformation.setName((String)accountInformation.get("LIBCPTE"));
        accountWithAllInformation.setStatus((String)accountInformation.get("ETATCPTE"));

        Branch branch = new Branch();
        branch.setCode((String)accountInformation.get("CODAGE"));
        branch.setName((String)accountInformation.get("NOMAGE"));

        accountWithAllInformation.setBranch(branch);

        Currency currency = new Currency();
        currency.setCode((String)accountInformation.get("CODDEV"));
        currency.setShortCode((String)accountInformation.get("LIBCOURTDEV"));

        accountWithAllInformation.setCurrency(currency);

        AccountNature nature = new AccountNature();
        nature.setCode((String)accountInformation.get("CODNATCPTE"));
        nature.setName((String)accountInformation.get("LIBNATCPTE"));

        accountWithAllInformation.setNature(nature);

        accountWithAllInformation.setCustomerCode((String)accountInformation.get("CODMUT"));

//        accountWithAllInformation.setDailyBalance((BigDecimal)accountInformation.get("SOLDE_JOURNALIER"));
//        accountWithAllInformation.setAccountingBalance((BigDecimal)accountInformation.get("SOLDE_COMPTABLE"));

        return accountWithAllInformation;
    }


    public AccountWithAllInformation getAccountInformationWithALLOpposition(CompleteAccountNumber completeAccountNumber) throws Exception{
        String query = "SELECT  c.CODAGE, c.NUMCPTE, c.CODDEV,  c.LIBCPTE, c.ETATCPTE, c.CODMUT, a.NOMAGE, d.CODDEV, d.LIBCOURTDEV, nc.CODNATCPTE, " +
                " nc.LIBNATCPTE, om.SENSOPPO AS SENS_MUT, oc.SENS AS SENS_CPTE, " +
                " m.NOMCLT, m.PRENOMCLT, m.E_MAILCLT, m.PHONE1CLT, m.PHONE2CLT " +
                " FROM COMPTES c  " +
                " INNER JOIN MUTUALISTES m ON m.CODMUT = c.CODMUT " +
                " INNER JOIN AGENCE a ON a.CODAGE = c.CODAGE  " +
                " INNER JOIN DEVISE d ON d.CODDEV = c.CODDEV " +
                " INNER JOIN NATURE_COMPTE nc ON nc.CODNATCPTE = c.CODNATCPTE " +
                " LEFT JOIN OPPO_MUT om ON om.CODMUT = c.CODMUT  " +
                " LEFT JOIN OPPO_CPTE oc ON oc.CODAGE = c.CODAGE AND oc.NUMCPTE = c.NUMCPTE AND oc.CODDEV = c.CODDEV  " +
                " WHERE c.CODAGE = ? AND c.NUMCPTE = ? AND c.CODDEV = ? AND c.ETATCPTE = 'O'" +
                " GROUP BY c.CODAGE, c.NUMCPTE, c.CODDEV, c.LIBCPTE, c.ETATCPTE, c.CODMUT, a.NOMAGE, d.CODDEV, d.LIBCOURTDEV, " +
                " nc.CODNATCPTE, nc.LIBNATCPTE, om.SENSOPPO,  oc.SENS, " +
                " m.NOMCLT, m.PRENOMCLT, m.E_MAILCLT, m.PHONE1CLT, m.PHONE2CLT " +
                " ORDER BY SENS_CPTE DESC, SENS_MUT DESC ";

        List<Map<String, Object>> accountInformationList = this.jdbcTemplate.queryForList(query, new Object[] { completeAccountNumber.getBranchCode(),
                completeAccountNumber.getAccountNumber(), completeAccountNumber.getCurrencyCode() });

        if(accountInformationList.size() == 0){
            throw new Exception("Account not found! completeAccountNumber : "+completeAccountNumber);
        }

        Map<String, Object> accountInformation = accountInformationList.get(0);
        AccountWithAllInformation accountWithAllInformation = new AccountWithAllInformation();

        accountWithAllInformation.setAccountNumber((String)accountInformation.get("NUMCPTE"));
        accountWithAllInformation.setName((String)accountInformation.get("LIBCPTE"));
        accountWithAllInformation.setStatus((String)accountInformation.get("ETATCPTE"));

        Branch branch = new Branch();
        branch.setCode((String)accountInformation.get("CODAGE"));
        branch.setName((String)accountInformation.get("NOMAGE"));

        accountWithAllInformation.setBranch(branch);

        Currency currency = new Currency();
        currency.setCode((String)accountInformation.get("CODDEV"));
        currency.setShortCode((String)accountInformation.get("LIBCOURTDEV"));

        accountWithAllInformation.setCurrency(currency);

        AccountNature nature = new AccountNature();
        nature.setCode((String)accountInformation.get("CODNATCPTE"));
        nature.setName((String)accountInformation.get("LIBNATCPTE"));

        accountWithAllInformation.setNature(nature);

        accountWithAllInformation.setCustomerCode((String)accountInformation.get("CODMUT"));

        // Set the person
        PersonDTO personDTO = PersonDTO.builder()
                .name((String)accountInformation.get("NOMCLT"))
                .firstName((String)accountInformation.get("PRENOMCLT"))
                .email((String)accountInformation.get("E_MAILCLT"))
                .firstPhoneNumber((String)accountInformation.get("PHONE1CLT"))
                .secondPhoneNumber((String)accountInformation.get("PHONE2CLT"))
                .build();
        accountWithAllInformation.setPerson(personDTO);

        accountInformationList.forEach(lineResult -> {
            String customerOpposition = (String)lineResult.get("SENS_MUT");
            String accountOpposition = (String)lineResult.get("SENS_CPTE");

            if (accountOpposition != null){
                switch (accountOpposition) {
                    case "T" :
                        accountWithAllInformation.setAccountAllOpposition(true);
                        break;
                    case "D":
                        accountWithAllInformation.setAccountDebitOpposition(true);
                        break;
                    case "C":
                        accountWithAllInformation.setAccountCreditOpposition(true);
                        break;
                    default:
                        throw new RuntimeException("Unknown account opposition direction "+accountOpposition);
                }
            }

            if (customerOpposition != null){
                switch (customerOpposition) {
                    case "T" :
                        accountWithAllInformation.setCustomerAllOpposition(true);
                        break;
                    case "D":
                        accountWithAllInformation.setCustomerDebitOpposition(true);
                        break;
                    case "C":
                        accountWithAllInformation.setCustomerCreditOpposition(true);
                        break;
                    default:
                        throw new RuntimeException("Unknown customer opposition direction "+customerOpposition);
                }
            }
        });

        return accountWithAllInformation;
    }


}
