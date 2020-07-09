package bo.com.ahosoft.arrestcontron.security;

/**
 * Constants for Spring Security authorities.
 */
public final class AuthoritiesConstants {

    public static final String ADMIN = "ROLE_ADMIN";

    public static final String UNIT = "ROLE_UNIT";

    public static final String USER = "ROLE_USER";

    public static final String REPORT = "ROLE_REPORT";

    public static final String ANONYMOUS = "ROLE_ANONYMOUS";

    public static final String RECEPTIONIST = "ROLE_RECEPTIONIST";
    public static final String DISPATCHER = "ROLE_DISPATCHER";

    private AuthoritiesConstants() {
    }
}
