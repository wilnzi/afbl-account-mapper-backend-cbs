package lr.afrilandfirstbankliberia.accountmappercbs.util;

import java.util.UUID;

public class CNWUtils {
    public static String generateUUIDCode(){
        UUID uuid = UUID.randomUUID();
        return uuid.toString();
    }
}
