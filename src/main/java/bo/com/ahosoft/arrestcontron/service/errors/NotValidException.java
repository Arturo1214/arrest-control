package bo.com.ahosoft.arrestcontron.service.errors;

public class NotValidException extends Exception {

    private String code;

    public NotValidException() {
        super("Not valid");
        code = "NOT_VALID";
    }

    public String getCode() {
        return code;
    }
}
