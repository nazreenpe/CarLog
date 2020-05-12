package com.nasreen.carlog;

import org.springframework.security.test.context.support.WithSecurityContext;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.UUID;

@Retention(RetentionPolicy.RUNTIME)
@WithSecurityContext(factory = WithMockScopeSecurityContextFactory.class)
public @interface WithMockAuthScope {
    String USER_ID_STR = "0a20dd9b-2055-4ed8-b3a7-1cfd91b0b67a";

    String userId() default USER_ID_STR;
}
