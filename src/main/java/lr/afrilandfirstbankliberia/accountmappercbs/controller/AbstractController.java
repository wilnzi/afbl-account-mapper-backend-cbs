package lr.afrilandfirstbankliberia.accountmappercbs.controller;

import lr.afrilandfirstbankliberia.accountmappercbs.dto.ResponseDTO;
import lr.afrilandfirstbankliberia.accountmappercbs.service.AbstractService;
import org.slf4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public abstract class AbstractController {
    protected ResponseEntity<ResponseDTO> generateResponseEntity(ResponseDTO responseDTO, Logger logger){
        ResponseEntity<ResponseDTO> response = null;
        if(responseDTO.getErrCode() == AbstractService.SUCCESS_CODE){
            response =  ResponseEntity.ok(responseDTO);
        }else{
            response =  ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseDTO);
        }
        // logger.info("End of executing web service with response "+responseDTO.toString());
        return response;
    }

    public void logRequest(Logger logger, String requestPath, Object dto){
        logger.info("Begin of executing web service ( "+requestPath+" ) with parameter "+dto.toString());
    }

    public void logRequest(Logger logger, String requestPath){
        logger.info("Begin of executing web service service ( "+requestPath+" )without parameter ");
    }
}
