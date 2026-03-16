package lr.afrilandfirstbankliberia.accountmappercbs.model.data;

import lr.afrilandfirstbankliberia.accountmappercbs.entity.Account;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.RequestScope;

import java.math.BigDecimal;
import java.util.HashMap;

@Component
@RequestScope
public class AccountStorage {
    private HashMap<Account, BigDecimal> accountHashMap = new HashMap<>();

    public HashMap<Account, BigDecimal> getAccountHashMap() {
        return accountHashMap;
    }
}
