package lr.afrilandfirstbankliberia.accountmappercbs.mapper;

import lr.afrilandfirstbankliberia.accountmappercbs.dto.AccountDTO;
import lr.afrilandfirstbankliberia.accountmappercbs.model.data.AccountWithAllInformation;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface AccountMapper {

//@Mapping(source="accountNumber", target = "acctNum")
//@Mapping(source="name", target = "acDesc")
//@Mapping(source="accountId.currency", target = "ccy")
//@Mapping(source="status", target = "acStatus")
//@Mapping(source="nature.code", target = "acctTypeCode")
//@Mapping(source="nature.name", target = "acctTypeDesc")
//AccountDTO entityToDTO(Account entity);

@Mapping(source="accountNumber", target = "acctNum")
@Mapping(source="name", target = "acDesc")
@Mapping(source="currency.shortCode", target = "ccy")
@Mapping(source="status", target = "acStatus")
@Mapping(source="nature.code", target = "acctTypeCode")
@Mapping(source="nature.name", target = "acctTypeDesc")
@Mapping(source="branch.code", target = "branch")
AccountDTO accountWithOppositionEntityToDTO(AccountWithAllInformation entity);


List<AccountDTO> entityToDTOList(List<AccountWithAllInformation> entity);
}
