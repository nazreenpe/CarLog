package com.nasreen.carlog;

import com.nasreen.carlog.model.User;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.context.support.WithSecurityContextFactory;

import java.util.List;
import java.util.Map;
import java.util.UUID;

public class WithMockScopeSecurityContextFactory implements WithSecurityContextFactory<WithMockAuthScope> {
  @Override
  public SecurityContext createSecurityContext(WithMockAuthScope annotation) {
    SecurityContext context = SecurityContextHolder.createEmptyContext();

    User principal = new User(UUID.randomUUID(), "test", "test@gmail.com", "some-password");
    Authentication auth = new UsernamePasswordAuthenticationToken(principal,
            "Fake",
            List.of(new SimpleGrantedAuthority("ROLE_USER")));

    context.setAuthentication(auth);

    return context;
  }
}