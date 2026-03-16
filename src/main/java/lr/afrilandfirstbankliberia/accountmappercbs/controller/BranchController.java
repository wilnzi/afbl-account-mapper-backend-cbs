package lr.afrilandfirstbankliberia.accountmappercbs.controller;

import lr.afrilandfirstbankliberia.accountmappercbs.dto.ResponseDTO;
import lr.afrilandfirstbankliberia.accountmappercbs.service.BranchService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/branch")
public class BranchController extends AbstractController{
    @Autowired
    BranchService service;

    private static final Logger logger = LoggerFactory.getLogger(BranchController.class);

    @GetMapping("/")
    public ResponseEntity<ResponseDTO> getALlBranches(){
        logRequest(logger, "/branch/");
        return generateResponseEntity(service.getAllBranch(), logger);
    }

}
