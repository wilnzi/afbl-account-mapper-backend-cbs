package lr.afrilandfirstbankliberia.accountmappercbs.service;

import jakarta.transaction.Transactional;
import lr.afrilandfirstbankliberia.accountmappercbs.dto.AccountDTO;
import lr.afrilandfirstbankliberia.accountmappercbs.dto.AccountDetailDTO;
import lr.afrilandfirstbankliberia.accountmappercbs.dto.ResponseDTO;
import lr.afrilandfirstbankliberia.accountmappercbs.dto.request.AccountOpenedListRequestDTO;
import lr.afrilandfirstbankliberia.accountmappercbs.dto.request.VerifyAccountBalanceRequestDTO;
import lr.afrilandfirstbankliberia.accountmappercbs.entity.Account;
import lr.afrilandfirstbankliberia.accountmappercbs.model.data.AccountStorage;
import lr.afrilandfirstbankliberia.accountmappercbs.model.data.AccountWithAllInformation;
import lr.afrilandfirstbankliberia.accountmappercbs.mapper.AccountMapper;
import lr.afrilandfirstbankliberia.accountmappercbs.model.data.AccountBalance;
import lr.afrilandfirstbankliberia.accountmappercbs.model.data.CompleteAccountNumber;
import lr.afrilandfirstbankliberia.accountmappercbs.repository.AccountNativeRepository;
import lr.afrilandfirstbankliberia.accountmappercbs.repository.AccountRepository;
import lr.afrilandfirstbankliberia.accountmappercbs.repository.AccountWithAllInformationRepository;
import lr.afrilandfirstbankliberia.accountmappercbs.repository.BalanceRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;

@Service
public class AccountService extends AbstractService{

    private static final Logger logger = LogManager.getLogger(AccountService.class);

    @Autowired
    AccountRepository repository;

    @Autowired
    AccountWithAllInformationRepository accountWithAllInformationRepository;
    @Autowired
    AccountMapper accountMapper;

    @Autowired
    BalanceRepository balanceRepository;

    @Autowired
    AccountStorage accountStorage;

    @Autowired
    AccountNativeRepository accountNativeRepository;


    @Transactional
    public ResponseDTO<AccountDTO> getAccountInformation(String accountNumber){
        ResponseDTO<AccountDTO> responseDTO = this.<AccountDTO>generateResponseDTO(); //TODO Review the call with the generic parameter

        try{
            CompleteAccountNumber completeAccountNumber = CompleteAccountNumber.generateCompleteAccountNumber(accountNumber);
            AccountWithAllInformation accountWithAllInformation = getAccountInformationWithOpposition(completeAccountNumber);
            responseDTO.setResult(accountMapper.accountWithOppositionEntityToDTO(accountWithAllInformation));

            if(accountWithAllInformation.getStatus().equals("O") ){
                if(accountWithAllInformation.hasAllOpposition()){
                    responseDTO.getResult().setAcStatus("BLOCK");
                }else{
                    if(accountWithAllInformation.hasCreditOpposition()){
                        responseDTO.getResult().setAcStatus("BLOCK_FOR_CREDIT");
                    }else if(accountWithAllInformation.hasDebitOpposition()){
                        responseDTO.getResult().setAcStatus("BLOCK_FOR_DEBT");
                    }else{
                        responseDTO.getResult().setAcStatus("ACTIVE");
                    }
                }
            }else{
                responseDTO.getResult().setAcStatus("CLOSE");
            }

            return responseDTO;
        }catch(Exception e){
            e.printStackTrace();
            responseDTO.setErrMsg(e.getMessage());
            responseDTO.setResult(null);
            responseDTO.setErrCode(ERROR_CODE);
            return responseDTO;
        }
    }


    public ResponseDTO<List<AccountDetailDTO>> getAccountsOpenedInPeriod(AccountOpenedListRequestDTO requestDTO){
        ResponseDTO<List<AccountDetailDTO>> responseDTO = this.<AccountDTO>generateResponseDTO();

        try{
            List<AccountDetailDTO> accountDetailDTOList = accountNativeRepository.getAccountsOpenedInPeriod(requestDTO.getBeginDate(), requestDTO.getEndDate());
            responseDTO.setResult(accountDetailDTOList);
            return responseDTO;
        }catch(Exception e){
            logger.error(e);
            responseDTO.setErrMsg(e.getMessage());
            responseDTO.setResult(null);
            responseDTO.setErrCode(ERROR_CODE);
            return responseDTO;
        }
    }

    public ResponseDTO<AccountDetailDTO> getAccountDetail(String accountNumber){
        ResponseDTO<AccountDetailDTO> responseDTO = this.<AccountDTO>generateResponseDTO(); //TODO Review the call with the generic parameter

        try{
            CompleteAccountNumber completeAccountNumber = CompleteAccountNumber.generateCompleteAccountNumber(accountNumber);
            AccountDetailDTO accountDetailDTO = accountNativeRepository.getAccountDetail(completeAccountNumber);
            responseDTO.setResult(accountDetailDTO);
            return responseDTO;
        }catch(Exception e){
            logger.error(e);
            responseDTO.setErrMsg(e.getMessage());
            responseDTO.setResult(null);
            responseDTO.setErrCode(ERROR_CODE);
            return responseDTO;
        }
    }


    private Account findCustomerAccountByAccountNumber(CompleteAccountNumber completeAccountNumber) throws Exception{

        Account account = repository.findCustomerAndTellerAccountByAccountNumber(completeAccountNumber.getBranchCode(),
                completeAccountNumber.getAccountNumber(), completeAccountNumber.getCurrencyCode());

        if(account == null) throw new Exception("Account with the accountNumber "+completeAccountNumber+" was not found");
        return account;
    }

    public AccountWithAllInformation getAccountInformationWithOpposition(CompleteAccountNumber completeAccountNumber ) throws Exception{
        AccountWithAllInformation accountWithAllInformation = accountWithAllInformationRepository.getAccountInformationWithALLOpposition(completeAccountNumber);
        if(accountWithAllInformation == null) throw new Exception("Account with the accountNumber "+completeAccountNumber+" was not found");
        return accountWithAllInformation;
    }


    public boolean canTransact(Account account, BigDecimal amount){
        Boolean canTransact = Boolean.FALSE;
        try {

            AccountBalance accountBalance = balanceRepository.getBalance(account.getBranch(),
                    account.getAccountId().getAccountNumber(), account.getCurrency(), LocalDate.now());

            if(accountBalance.getAvailableBalance().compareTo(amount) == 1){
                canTransact = Boolean.TRUE;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return canTransact;
    }


    public ResponseDTO<Boolean> canTransact(VerifyAccountBalanceRequestDTO requestDTO){
        Boolean canTransact;
        ResponseDTO<Boolean> responseDTO = generateResponseDTO();
        try {
            logger.info("Web service /canTransact starts  ==> Parameter : "+requestDTO);
            CompleteAccountNumber completeAccountNumber = CompleteAccountNumber.generateCompleteAccountNumber(requestDTO.getAccountNumber(), requestDTO.getCurrency());
            Account account = findCustomerAccountByAccountNumber(completeAccountNumber);
            canTransact = canTransact(account, requestDTO.getAmount());
            responseDTO.setResult(canTransact);
            logger.info("Web service /canTransact executed successfully ==> Parameter : "+requestDTO + " Response : "+responseDTO);
        } catch (Exception e) {
            e.printStackTrace();
            responseDTO.setErrMsg(e.getMessage());
            responseDTO.setResult(null);
            responseDTO.setErrCode(ERROR_CODE);
            logger.error(e.getMessage());
        }
        return responseDTO;
    }

    public void updateDailyBalance(Account account) throws Exception {
        try{
            repository.updateDailyBalance(account.getBranch(), account.getAccountNumber(), account.getCurrency(), account.getDailyBalance());
        }catch (Exception e){
            e.printStackTrace();
            throw new Exception(e);
        }
    }

    public Account findCustomerAccountWithCompleteAccountNumber(CompleteAccountNumber completeAccountNumber){
        return repository.findCustomerAndTellerAccountByAccountNumber(completeAccountNumber.getBranchCode(),
                completeAccountNumber.getAccountNumber(), completeAccountNumber.getCurrencyCode());
    }

    public Account findAnyAccountWithCompleteAccountNumber(CompleteAccountNumber completeAccountNumber){
        return repository.findAnyAccountWithCompleteAccountNumber(completeAccountNumber.getBranchCode(),
                completeAccountNumber.getAccountNumber(), completeAccountNumber.getCurrencyCode());
    }


    public void creditAccount(Account account, BigDecimal amount){

        if(accountStorage.getAccountHashMap().containsKey(account)){
            accountStorage.getAccountHashMap().put(account, accountStorage.getAccountHashMap().get(account).add(amount));
        }else{
            accountStorage.getAccountHashMap().put(account, BigDecimal.ZERO.add(amount));
        }
    }

    public void debitAccount(Account account, BigDecimal amount){

        if(accountStorage.getAccountHashMap().containsKey(account)){
            accountStorage.getAccountHashMap().put(account, accountStorage.getAccountHashMap().get(account).subtract(amount));
        }else{
            accountStorage.getAccountHashMap().put(account, BigDecimal.ZERO.subtract(amount));
        }
    }

    //TODO Make a bulkUpdate
    public void flushUpdatedDailyBalanceAccounts() throws Exception{
        try {
            HashMap<Account, BigDecimal>  hashMap = accountStorage.getAccountHashMap();
            hashMap.forEach((account, amount) -> {
                try {
                    repository.updateDailyBalance(account.getBranch(), account.getAccountNumber(), account.getCurrency(), amount);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            });

//            for(int i=0; i< hashMap.size(); i++){
//                Map.Entry<Account, BigDecimal> entry = accountStorage.getAccountHashMap().entrySet().iterator().next();
//                Account account = entry.getKey();
//                BigDecimal amount = entry.getValue();
//                repository.updateDailyBalance(account.getBranch(), account.getAccountNumber(), account.getCurrency(), amount);
//            }
        }catch (Exception e){
            e.printStackTrace();
            throw new Exception(e);
        }
    }

}
