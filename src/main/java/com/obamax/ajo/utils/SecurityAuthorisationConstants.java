package com.obamax.ajo.utils;

public class SecurityAuthorisationConstants {

    public static final String[] PUBLIC_URIS = new String[]{
            "/",
            "/admin/login",

            // -- Swagger UI v3 (OpenAPI) Start
            "/v2/api-docs",
            "/swagger-resources",
            "/swagger-resources/**",
            "/configuration/ui",
            "/configuration/security",
            "/swagger-ui.html",
            "/webjars/**",
            "/v3/api-docs/**",
            "/swagger-ui/**",
            "/swagger-ui/login/",
            "/swagger-ui/api/login/",
            "/swagger-ui/#/**",
            // -- Swagger UI v3 (OpenAPI) End
            "/login",
            "/api/login",
            "api/login/",
            "/#/api/login/",
            "/register",
            "/api/register"
            // -- Swagger UI v3 (OpenAPI) End
    };
}
