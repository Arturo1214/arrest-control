package bo.com.ahosoft.arrestcontron.service.errors;

public class CheckException extends Exception {

    private String code;

    public CheckException() {
        super("Check not valid");
        code = "CHECK_NOT_VALID";
    }

    public String getCode() {
        return code;
    }
}
