package bo.com.ahosoft.arrestcontron.service.errors;

public class ArrestNotFoundException extends Exception {

    private String code;

    public ArrestNotFoundException() {
        super("Arrest not found");
        code = "ARREST_NOT_FOUND";
    }

    public String getCode() {
        return code;
    }
}
