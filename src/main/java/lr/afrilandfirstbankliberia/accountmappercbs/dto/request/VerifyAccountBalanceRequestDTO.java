package lr.afrilandfirstbankliberia.accountmappercbs.dto.request;

import lombok.Data;
import lr.afrilandfirstbankliberia.accountmappercbs.model.enumerator.CurrencyEnum;

import java.math.BigDecimal;

@Data
public class VerifyAccountBalanceRequestDTO {
    private String accountNumber;
    private CurrencyEnum currency;
    private BigDecimal amount;
}
