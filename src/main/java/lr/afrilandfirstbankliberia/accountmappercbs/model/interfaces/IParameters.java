package lr.afrilandfirstbankliberia.accountmappercbs.model.interfaces;

public interface IParameters {
    String getFeesAccount(String branchCode) throws Exception;
    String getLRAAccountNumber() throws Exception ;
    String getLitasPaymentOperationCode() throws Exception ;
    String getTaxAccount() throws Exception ;
}
