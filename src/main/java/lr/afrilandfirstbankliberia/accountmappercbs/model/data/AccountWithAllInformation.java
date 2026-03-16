package lr.afrilandfirstbankliberia.accountmappercbs.model.data;

import lombok.Data;
import lr.afrilandfirstbankliberia.accountmappercbs.dto.PersonDTO;
import lr.afrilandfirstbankliberia.accountmappercbs.entity.AccountNature;
import lr.afrilandfirstbankliberia.accountmappercbs.entity.Branch;
import lr.afrilandfirstbankliberia.accountmappercbs.entity.Currency;

import java.math.BigDecimal;

//TODO Refractorise this class to use CompleteAccountNumber
@Data
public class AccountWithAllInformation {

    private BigDecimal numberOfSleepingTypeOfOpposition;

    private String accountNumber;


    private String name;

    private Branch branch;


    private String status;

    private Currency currency;

    private AccountNature nature;


    private String customerCode;

    private PersonDTO person;


//    private BigDecimal dailyBalance;
//
//
//    private BigDecimal accountingBalance;

    private boolean customerDebitOpposition;
    private boolean customerCreditOpposition;
    private boolean customerAllOpposition;

    private boolean accountDebitOpposition;
    private boolean accountCreditOpposition;
    private boolean accountAllOpposition;


    public boolean hasCreditOpposition(){
        return (customerCreditOpposition || accountCreditOpposition);
    }

    public boolean hasDebitOpposition(){
        return (customerDebitOpposition || accountDebitOpposition);
    }

    public boolean hasAllOpposition(){
        return (customerAllOpposition || accountAllOpposition || (hasDebitOpposition() && hasCreditOpposition()));
    }
}
