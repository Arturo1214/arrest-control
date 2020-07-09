package bo.com.ahosoft.arrestcontron.service.errors;

public class RegisterCaseNotFoundException extends Exception {

    private String code;

    public RegisterCaseNotFoundException() {
        super("Register case not found");
        code = "REGISTER_CASE_NOT_FOUND";
    }

    public String getCode() {
        return code;
    }
}
