package todo.webapp.api.constant;
public final class SecurityConstant {

    private SecurityConstant(){}

    public static final long EXPIRATION_TIME = 432_000_000; // 5 days expressed in milliseconds
    public static final String TOKEN_PREFIX = "Bearer ";
    public static final String JWT_TOKEN_HEADER = "Jwt-Token";
    public static final String TOKEN_CANNOT_BE_VERIFIED = "Token cannot be verified.";
    public static final String TODO_WEBAPP = "TO-DO WebApp";
    public static final String TODO_WEBAPP_ADMINISTRATION = "Login Portal";
    public static final String AUTHORITIES = "authorities";
    public static final String FORBIDDEN_MESSAGE = "You must login to access this page.";
    public static final String ACCESS_DENIED_MESSAGE = "You don't have permission to access this page.";
    public static final String OPTIONS_HTTP_METHOD = "OPTIONS";
    //public static final String[] PUBLIC_URLS = { "/user/login", "/user/register", "/user/image/**","/tema/image/**" };
    public static final String[] PUBLIC_URLS = { "**" };
}
