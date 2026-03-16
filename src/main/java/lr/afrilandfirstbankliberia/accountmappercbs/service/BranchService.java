package lr.afrilandfirstbankliberia.accountmappercbs.service;

import lr.afrilandfirstbankliberia.accountmappercbs.dto.BranchDTO;
import lr.afrilandfirstbankliberia.accountmappercbs.dto.ResponseDTO;
import lr.afrilandfirstbankliberia.accountmappercbs.entity.Branch;
import lr.afrilandfirstbankliberia.accountmappercbs.mapper.BranchMapper;
import lr.afrilandfirstbankliberia.accountmappercbs.repository.BranchRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BranchService extends AbstractService{

    @Autowired
    BranchRepository repository;
    @Autowired
    BranchMapper BranchMapper;
    public ResponseDTO<List<BranchDTO>> getAllBranch(){
        ResponseDTO<List<BranchDTO>> responseDTO = generateResponseDTO();

        try{
            List<Branch> branchEntityList = repository.getAllCustomerBranch();
            responseDTO.setResult(BranchMapper.entityToDTOList(branchEntityList));
            return responseDTO;
        }catch(Exception e){
            e.printStackTrace();
            responseDTO.setErrMsg(e.getMessage());
            responseDTO.setResult(null);
            responseDTO.setErrCode(ERROR_CODE);
            return responseDTO;
        }
    }
}
