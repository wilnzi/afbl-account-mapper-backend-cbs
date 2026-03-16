package lr.afrilandfirstbankliberia.accountmappercbs.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AccountDetailDTO {
    private static final long serialVersionUID = 1L;

    // Customer Information
    private String customerId;
    private String uniqueBankingIdentifier;
    private String firstName;
    private String otherName;
    private String lastName;
    private String customerName;
    private String customerPhoneNumber1;
    private String customerPhoneNumber2;
    private String customerPhoneNumber3;
    private String customerEmail;
    private String customerAddress;
    private Boolean pep; // Politically Exposed Person

    // Account Information
    private String accountNumber;
    private String oldAccountNumber;
    private String bban;
    private String iban;
    private String accountTitle;
    private String accountTitle2;
    private String jointHolderName;
    private String accountCategory; // Personal, Moral
    private String accountType;
    private String accountCurrency;
    private String accountTier;
    private String accountStatus; // Active, Dormant, Closed
    private String alternativeAccountId;
    private LocalDate dateAccountCreated;

    // Business/Legal Information
    private String businessRegNo;
    private String industryName;
    private LocalDate legalIssueDate;
    private String legalId;
    private LocalDate expiryDate;
    private String legalDocumentName;
    private String legalHolderName;

    // Proxy Information
    private String proxyType;
    private String proxyIdentifier;
    private String proxyScheme;
}
