package lr.afrilandfirstbankliberia.accountmappercbs.repository;

import lr.afrilandfirstbankliberia.accountmappercbs.dto.AccountDetailDTO;
import lr.afrilandfirstbankliberia.accountmappercbs.model.data.CompleteAccountNumber;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Repository;

import java.sql.Types;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@Repository
public class AccountNativeRepository {
    private static final Logger logger = LogManager.getLogger(AccountNativeRepository.class);

    @Autowired
    JdbcTemplate jdbcTemplate;


    public List<AccountDetailDTO> getAccountsOpenedInPeriod(LocalDate beginDate, LocalDate endDate) throws Exception {

        SimpleJdbcCall simpleJdbcCall = new SimpleJdbcCall(jdbcTemplate)
                .withProcedureName("ps_getAccountsOpenedInPeriod")
                .declareParameters(
                        new SqlParameter("p_beginDate", Types.DATE),
                        new SqlParameter("p_endDate", Types.DATE)
                )
//                .returningResultSet("p_results", new ColumnMapRowMapper());
                .returningResultSet("p_results", BeanPropertyRowMapper.newInstance(AccountDetailDTO.class));
        ;

        SqlParameterSource parameterValueSource = new MapSqlParameterSource()
                .addValue("p_beginDate", beginDate)
                .addValue("p_endDate", endDate);

        Map<String, Object> result = simpleJdbcCall.execute(parameterValueSource);

        @SuppressWarnings("unchecked")
        List<AccountDetailDTO> resultList = (List<AccountDetailDTO>) result.get("p_results");
        return resultList;
    }

    public AccountDetailDTO getAccountDetail(CompleteAccountNumber completeAccountNumber) throws Exception {
        SimpleJdbcCall simpleJdbcCall = new SimpleJdbcCall(jdbcTemplate)
                .withProcedureName("ps_getAccountInformation")
                .declareParameters(
                        new SqlParameter("p_branchCode", Types.VARCHAR),
                        new SqlParameter("p_currencyCode", Types.VARCHAR),
                        new SqlParameter("p_accountNumber", Types.VARCHAR)
                )
//                .returningResultSet("p_results", new ColumnMapRowMapper());
                .returningResultSet("p_results", BeanPropertyRowMapper.newInstance(AccountDetailDTO.class));
        ;

        SqlParameterSource parameterValueSource = new MapSqlParameterSource()
                .addValue("p_branchCode", completeAccountNumber.getBranchCode())
                .addValue("p_currencyCode", completeAccountNumber.getCurrencyCode())
                .addValue("p_accountNumber", completeAccountNumber.getAccountNumber());

        Map<String, Object> result = simpleJdbcCall.execute(parameterValueSource);

        @SuppressWarnings("unchecked")
        List<AccountDetailDTO> resultList = (List<AccountDetailDTO>) result.get("p_results");
        if(resultList.size() !=1){
            throw new Exception("list has more than one element ## "+resultList.size());
        }

        return (AccountDetailDTO) resultList.get(0);
    }

}
