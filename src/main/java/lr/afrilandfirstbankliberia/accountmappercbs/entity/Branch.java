package lr.afrilandfirstbankliberia.accountmappercbs.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.util.Objects;

@Entity
@Table(name="AGENCE")
public class Branch {


    @Id
    @Column(name="CODAGE")
    private String code;

    @Column(name="NOMAGE")
    private String name;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Branch branch = (Branch) o;
        return Objects.equals(getCode(), branch.getCode());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getCode());
    }
}
