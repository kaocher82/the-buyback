package com.thebuyback.eve.security;

/**
 * Constants for Spring Security authorities.
 */
public final class AuthoritiesConstants {

    public static final String ADMIN = "ROLE_ADMIN";

    public static final String USER = "ROLE_USER";

    public static final String ANONYMOUS = "ROLE_ANONYMOUS";

    public static final String MANAGER = "ROLE_MANAGER";

    public static final String STOCK_MANAGER = "ROLE_STOCK_MANAGER";

    public static final String ASSETS = "ROLE_ASSETS";

    private AuthoritiesConstants() {
    }
}
