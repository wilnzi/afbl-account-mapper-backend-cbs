package lr.afrilandfirstbankliberia.accountmappercbs.model.data;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

//TODO Refractorise this class to use CompleteAccountNumber
@Data
public class AccountBalance {
    private String number;
    private String agencyCode;
    private String currencyCode;
    private LocalDate accountingDate;
    private BigDecimal reservedAmount;
    private BigDecimal dailyBalance;
    private BigDecimal accountingBalance;
    private BigDecimal availableBalance;
    private BigDecimal overdraftAmount;
    private LocalDate endDateOfOverdraft;
}
