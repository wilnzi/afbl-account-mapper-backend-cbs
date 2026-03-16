package lr.afrilandfirstbankliberia.accountmappercbs.model.data;

import lombok.Data;
import lr.afrilandfirstbankliberia.accountmappercbs.entity.Account;
import lr.afrilandfirstbankliberia.accountmappercbs.entity.Currency;
import lr.afrilandfirstbankliberia.accountmappercbs.model.enumerator.CurrencyEnum;

@Data
public class CompleteAccountNumber {
    private String accountNumber;
    private String branchCode;
    private String currencyCode;
    private String currencyLabel;
    private String customerCode;
    private String wholeAccountNumber;

    public static CompleteAccountNumber generateCompleteAccountNumber(String accountNumber, Currency currency) throws Exception{
        CompleteAccountNumber completeAccountNumber = new CompleteAccountNumber();
        if(accountNumber.length() != 15){
            throw new Exception(String.format("The accountNumber %s is different than 15 characters which is the actual length " +
                    "in the date of 06/16/2024 of one account number in the system ", accountNumber));
        }
        completeAccountNumber.setBranchCode(accountNumber.substring(0,3));
        completeAccountNumber.setAccountNumber(accountNumber.substring(3,15));
        completeAccountNumber.setCurrencyCode(currency.getCode());
        return completeAccountNumber;
    }

    public static CompleteAccountNumber generateCompleteAccountNumber(String accountNumber, CurrencyEnum currencyEnum) throws Exception{
        CompleteAccountNumber completeAccountNumber = new CompleteAccountNumber();
        if(accountNumber.length() != 15 ){
            throw new Exception(String.format("The accountNumber %s is different than 15 characters which is the actual length " +
                    "in the date of 06/16/2024 of one account number in the system ", accountNumber));
        }
        completeAccountNumber.setBranchCode(accountNumber.substring(0,3));
        completeAccountNumber.setAccountNumber(accountNumber.substring(3,15));
        completeAccountNumber.setCurrencyCode(currencyEnum.getCode());
        return completeAccountNumber;
    }

    public static CompleteAccountNumber generateCompleteAccountNumberWithLRD(String accountNumber) throws Exception{
        CompleteAccountNumber completeAccountNumber = new CompleteAccountNumber();
        if(accountNumber.length() != 15 ){
            throw new Exception(String.format("The accountNumber %s is different than 15 characters which is the actual length " +
                    "in the date of 06/16/2024 of one account number in the system ", accountNumber));
        }
        completeAccountNumber.setBranchCode(accountNumber.substring(0,3));
        completeAccountNumber.setAccountNumber(accountNumber.substring(3,15));
        completeAccountNumber.setCurrencyCode("430");
        return completeAccountNumber;
    }

    public static CompleteAccountNumber generateCompleteAccountNumber(Account account) throws Exception{
        CompleteAccountNumber completeAccountNumber = new CompleteAccountNumber();
        completeAccountNumber.setBranchCode(account.getBranch());
        completeAccountNumber.setAccountNumber(account.getAccountNumber());
        completeAccountNumber.setCurrencyCode(account.getCurrency());
        return completeAccountNumber;
    }

    public String getCompleteAccountNumber(){
        return this.branchCode+this.accountNumber;
    }

    public static CompleteAccountNumber generateCompleteAccountNumber(String completeLongFormatAccountNumber) throws Exception{
        CompleteAccountNumber completeAccountNumber = new CompleteAccountNumber();
        if(completeLongFormatAccountNumber == null || completeLongFormatAccountNumber.length() != 15 ){
            throw new Exception(String.format("The accountNumber %s is different than 15 characters which is the actual length " +
                    "in the date of 06/16/2024 of one account number in the system ", completeLongFormatAccountNumber));
        }
        completeAccountNumber.setBranchCode(completeLongFormatAccountNumber.substring(0,3));
        completeAccountNumber.setAccountNumber(completeLongFormatAccountNumber.substring(3,15));

        String currencyCharacters = completeLongFormatAccountNumber.substring(3,5);

        if(currencyCharacters.equals("02")){
            completeAccountNumber.setCurrencyCode("840");
            completeAccountNumber.setCurrencyLabel("USD");
        }else if (currencyCharacters.equals("01")){
            completeAccountNumber.setCurrencyCode("430");
            completeAccountNumber.setCurrencyLabel("LRD");
        }else{
            throw new Exception(String.format("Unable to determine the currency ", completeLongFormatAccountNumber));
        }

        completeAccountNumber.setCustomerCode(completeLongFormatAccountNumber.substring(5,11));
        completeAccountNumber.setWholeAccountNumber(completeLongFormatAccountNumber);
        return completeAccountNumber;
    }

}
