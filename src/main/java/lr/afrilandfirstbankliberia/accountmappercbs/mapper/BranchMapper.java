package lr.afrilandfirstbankliberia.accountmappercbs.mapper;

import lr.afrilandfirstbankliberia.accountmappercbs.dto.BranchDTO;
import lr.afrilandfirstbankliberia.accountmappercbs.entity.Branch;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface BranchMapper {

@Mapping(source="code", target = "branchCode")
@Mapping(source="name", target = "branchName")
BranchDTO entityToDTO(Branch entity);

List<BranchDTO> entityToDTOList(List<Branch> entity);
}
