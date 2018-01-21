package com.thebuyback.eve.web.rest.errors;

import java.net.URI;

public final class ErrorConstants {

    public static final String ERR_CONCURRENCY_FAILURE = "error.concurrencyFailure";
    public static final String ERR_VALIDATION = "error.validation";
    public static final String PROBLEM_BASE_URL = "http://www.jhipster.tech/problem";
    public static final URI DEFAULT_TYPE = URI.create(PROBLEM_BASE_URL + "/problem-with-message");
    public static final URI CONSTRAINT_VIOLATION_TYPE = URI.create(PROBLEM_BASE_URL + "/constraint-violation");
    public static final URI PARAMETERIZED_TYPE = URI.create(PROBLEM_BASE_URL + "/parameterized");
    public static final URI INVALID_PASSWORD_TYPE = URI.create(PROBLEM_BASE_URL + "/invalid-password");
    public static final URI LOGIN_ALREADY_USED_TYPE = URI.create(PROBLEM_BASE_URL + "/login-already-used");
    public static final URI ERR_INTERNAL_SERVER_ERROR = URI.create(PROBLEM_BASE_URL + "/internal-server-error");
    public static final URI ERR_METHOD_NOT_SUPPORTED = URI.create(PROBLEM_BASE_URL + "/method-not-supported");
    public static final URI ERR_ACCESS_DENIED = URI.create(PROBLEM_BASE_URL + "/access-denied");

    private ErrorConstants() {
    }
}
