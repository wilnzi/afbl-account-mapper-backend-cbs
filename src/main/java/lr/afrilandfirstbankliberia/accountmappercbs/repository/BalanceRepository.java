package lr.afrilandfirstbankliberia.accountmappercbs.repository;

import lr.afrilandfirstbankliberia.accountmappercbs.model.data.AccountBalance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Repository;


import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Types;
import java.time.LocalDate;
import java.util.Map;

@Repository
public class BalanceRepository {

    @Autowired
    JdbcTemplate jdbcTemplate;

    public AccountBalance getBalance( String agencyCode, String accountNumber, String currencyCode, LocalDate accountingDate) throws Exception{
        SimpleJdbcCall simpleJdbcCall = new SimpleJdbcCall(jdbcTemplate)
                .withProcedureName("PS_SOLDE_COMPTE")
                .declareParameters(
                        new SqlParameter("P_CCOMPTE", Types.VARCHAR)
                        ,new SqlParameter("P_CCODEAGENCE", Types.VARCHAR)
                        ,new SqlParameter("P_CCODEDEVISE", Types.VARCHAR)
                        ,new SqlParameter("P_DDTECOMPTABLE", Types.DATE)
                        ,new SqlOutParameter("P_MONTANTRESERVE", Types.NUMERIC)
                        ,new SqlOutParameter("P_NSOLDEJOURNALIER", Types.NUMERIC)
                        ,new SqlOutParameter("P_NSOLDECOMPTABLE", Types.NUMERIC)
                        ,new SqlOutParameter("P_NSOLDEDISPONIBLE", Types.NUMERIC)
                        ,new SqlOutParameter("P_MONTANTDECOUVERT", Types.NUMERIC)
                        ,new SqlOutParameter("P_DDTEFINDECOUVERT", Types.DATE)
                        ,new SqlOutParameter("P_ERREUR", Types.NUMERIC)
                )
                ;

        SqlParameterSource parameterValueSource = new MapSqlParameterSource()
                .addValue("P_CCOMPTE", accountNumber)
                .addValue("P_CCODEAGENCE", agencyCode)
                .addValue("P_CCODEDEVISE", currencyCode)
                .addValue("P_DDTECOMPTABLE", accountingDate)
                ;

        Map<String, Object> result = simpleJdbcCall.execute(parameterValueSource);
        BigDecimal errorValue = (BigDecimal) result.get("P_ERREUR");

        if( errorValue == null || errorValue.compareTo(BigDecimal.ZERO) != 0){
            throw new Exception(String.format("Balance for account %s-%s on currency %s not present in the accounting date %s", agencyCode, accountNumber, currencyCode, accountingDate ));
        }
        AccountBalance accountBalance = new AccountBalance();
        accountBalance.setNumber(accountNumber);
        accountBalance.setAgencyCode(agencyCode);
        accountBalance.setCurrencyCode(currencyCode);
        accountBalance.setAccountingDate(accountingDate);
        if(result.get("P_NSOLDEDISPONIBLE") != null){
            accountBalance.setAvailableBalance((BigDecimal)result.get("P_NSOLDEDISPONIBLE"));
        }else{
            throw new Exception(String.format("Error when getting the available balance for account %s-%s on currency %s  in the accounting date %s", agencyCode, accountNumber, currencyCode, accountingDate ));
        }
        if(result.get("P_MONTANTRESERVE") != null) accountBalance.setReservedAmount((BigDecimal)(result.get("P_MONTANTRESERVE")));
        if(result.get("P_NSOLDEJOURNALIER") != null) accountBalance.setDailyBalance((BigDecimal)result.get("P_NSOLDEJOURNALIER"));
        if(result.get("P_NSOLDECOMPTABLE") != null) accountBalance.setAccountingBalance((BigDecimal)result.get("P_NSOLDECOMPTABLE"));
        if(result.get("P_MONTANTDECOUVERT") != null) accountBalance.setOverdraftAmount((BigDecimal)result.get("P_MONTANTDECOUVERT"));
        if(result.get("P_DDTEFINDECOUVERT") != null) accountBalance.setEndDateOfOverdraft(((Date)result.get("P_DDTEFINDECOUVERT")).toLocalDate());
        return accountBalance;
    }
}
