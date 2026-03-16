package lr.afrilandfirstbankliberia.accountmappercbs.model.enumerator;

public enum CurrencyEnum {
    LRD("430"),
    USD("840");

    String code;

    CurrencyEnum(String code){
        this.code = code;
    }

    public String getCode() {
        return code;
    }
}
