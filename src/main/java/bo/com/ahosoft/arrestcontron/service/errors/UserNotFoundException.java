package bo.com.ahosoft.arrestcontron.service.errors;

public class UserNotFoundException extends Exception {

    private String code;

    public UserNotFoundException() {
        super("User not found");
        code = "USER_NOT_FOUND";
    }

    public String getCode() {
        return code;
    }
}
