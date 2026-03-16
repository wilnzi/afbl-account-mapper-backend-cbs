package lr.afrilandfirstbankliberia.accountmappercbs.repository;

import lr.afrilandfirstbankliberia.accountmappercbs.entity.Currency;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CurrencyRepository extends JpaRepository<Currency, String> {

    Currency getCurrencyByShortCode(String shortCode);
}
