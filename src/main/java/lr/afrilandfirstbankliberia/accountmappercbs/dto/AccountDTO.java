package lr.afrilandfirstbankliberia.accountmappercbs.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class AccountDTO {
    @JsonProperty("accountNumber")
    private String acctNum;

    @JsonProperty("typeCode")
    private String acctTypeCode;

    @JsonProperty("typeDescription")
    private String acctTypeDesc;

    @JsonProperty("description")
    private String acDesc;

    @JsonProperty("status")
    private String acStatus;

    @JsonProperty("branch")
    private String branch;

    @JsonProperty("currency")
    private String ccy;
    PersonDTO person;
}
