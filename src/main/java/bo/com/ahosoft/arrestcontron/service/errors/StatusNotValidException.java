package bo.com.ahosoft.arrestcontron.service.errors;

public class StatusNotValidException extends Exception {

    private String code;

    public StatusNotValidException() {
        super("Status not valid");
        code = "STATUS_NOT_VALID";
    }

    public String getCode() {
        return code;
    }
}
