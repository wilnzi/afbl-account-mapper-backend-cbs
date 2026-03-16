package lr.afrilandfirstbankliberia.accountmappercbs.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PersonDTO {
    private String code;
    private String firstPhoneNumber;
    private String secondPhoneNumber;
    private String name;
    private String firstName;
    private String email;


}