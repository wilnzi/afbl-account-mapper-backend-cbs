package lr.afrilandfirstbankliberia.accountmappercbs.repository;

import lr.afrilandfirstbankliberia.accountmappercbs.entity.Branch;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BranchRepository extends JpaRepository<Branch, String> {

    @Query("SELECT a FROM Branch a WHERE code NOT IN ('100','999') ORDER BY code ASC")
    List<Branch> getAllCustomerBranch();


}
