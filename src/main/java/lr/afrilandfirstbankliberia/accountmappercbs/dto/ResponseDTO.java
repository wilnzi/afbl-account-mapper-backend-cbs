package lr.afrilandfirstbankliberia.accountmappercbs.dto;

import lombok.Data;

@Data
public class  ResponseDTO <T> {
    private T result;
    private String errMsg = "SUCCESS";
    private int errCode = 0;
}
