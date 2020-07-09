package bo.com.ahosoft.arrestcontron.service.errors;

public class UserNotValidException extends Exception {

    private String code;

    public UserNotValidException() {
        super("User not valid");
        code = "USER_NOT_VALID";
    }

    public String getCode() {
        return code;
    }
}
