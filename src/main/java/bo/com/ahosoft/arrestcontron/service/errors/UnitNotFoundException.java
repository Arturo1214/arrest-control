package bo.com.ahosoft.arrestcontron.service.errors;

public class UnitNotFoundException extends Exception {

    private String code;

    public UnitNotFoundException() {
        super("Unit not found");
        code = "UNIT_NOT_FOUND";
    }

    public String getCode() {
        return code;
    }
}
