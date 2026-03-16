package lr.afrilandfirstbankliberia.accountmappercbs;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class AccountMapperCBSApplication {

	public static void main(String[] args) {
		SpringApplication.run(AccountMapperCBSApplication.class, args);
	}

}

