package bo.com.ahosoft.arrestcontron.service.errors;

public class DateArrestOutRangeException extends Exception {

    private String code;

    public DateArrestOutRangeException() {
        super("Date of arrest out of range");
        code = "dateArrestOutRange";
    }

    public String getCode() {
        return code;
    }
}
