package lr.afrilandfirstbankliberia.accountmappercbs.service;

import lr.afrilandfirstbankliberia.accountmappercbs.dto.ResponseDTO;


public abstract class AbstractService {

    public static final int SUCCESS_CODE = 0;
    public static final int ERROR_CODE = 500;
    protected <T> ResponseDTO generateResponseDTO(){
        return new ResponseDTO<T>();
    }


}
