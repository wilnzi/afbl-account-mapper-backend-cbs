package lr.afrilandfirstbankliberia.accountmappercbs.controller;

import lr.afrilandfirstbankliberia.accountmappercbs.dto.ResponseDTO;
import lr.afrilandfirstbankliberia.accountmappercbs.dto.request.AccountOpenedListRequestDTO;
import lr.afrilandfirstbankliberia.accountmappercbs.dto.request.VerifyAccountBalanceRequestDTO;
import lr.afrilandfirstbankliberia.accountmappercbs.service.AccountService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/accounts")
public class AccountController extends AbstractController{

    private static final Logger logger = LoggerFactory.getLogger(AccountController.class);
    @Autowired
    AccountService service;
    @GetMapping("/{accountNumber}")
    public ResponseEntity<ResponseDTO> getAccountInformation(@PathVariable("accountNumber") String accountNumber){
        logRequest(logger, "/accounts/{accountNumber", accountNumber);
        return generateResponseEntity(service.getAccountInformation(accountNumber), logger);
    }

    @PostMapping("/canTransact")
    public ResponseEntity<ResponseDTO> canTransact(@RequestBody VerifyAccountBalanceRequestDTO requestDTO){
        logRequest(logger, "/accounts/canTransact", requestDTO);
        return generateResponseEntity(service.canTransact(requestDTO), logger);
    }


    @PostMapping("/getAccountsOpenedInPeriod")
    public ResponseEntity<ResponseDTO> getAccountsOpenedInPeriod(@RequestBody AccountOpenedListRequestDTO requestDTO){
        logRequest(logger, "/accounts/getAccountsOpenedInPeriod", requestDTO);
        return generateResponseEntity(service.getAccountsOpenedInPeriod(requestDTO), logger);
    }

    @GetMapping("/getAccountDetail/{accountNumber}")
    public ResponseEntity<ResponseDTO> getAccountDetail(@PathVariable("accountNumber") String accountNumber){
        logRequest(logger, "/accounts/getAccountDetail/{accountNumber", accountNumber);
        return generateResponseEntity(service.getAccountDetail(accountNumber), logger);
    }


}
