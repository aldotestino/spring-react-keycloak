package com.at.srk.security;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.core.DefaultOAuth2AuthenticatedPrincipal;
import org.springframework.security.oauth2.core.OAuth2AuthenticatedPrincipal;
import org.springframework.security.oauth2.server.resource.introspection.NimbusOpaqueTokenIntrospector;
import org.springframework.security.oauth2.server.resource.introspection.OpaqueTokenIntrospector;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Stream;

public class KeyCloakOpaqueTokenIntrospector implements OpaqueTokenIntrospector {

    private OpaqueTokenIntrospector delegate;

    public KeyCloakOpaqueTokenIntrospector(String introspectionUri, String clientId, String clientSecret) {
        this.delegate = new NimbusOpaqueTokenIntrospector(introspectionUri, clientId, clientSecret);
    }

    public OAuth2AuthenticatedPrincipal introspect(String token) {
        OAuth2AuthenticatedPrincipal principal = this.delegate.introspect(token);
        return new DefaultOAuth2AuthenticatedPrincipal(principal.getName(), principal.getAttributes(), this.extractAuthorities(principal));
    }

    private Collection<GrantedAuthority> extractAuthorities(OAuth2AuthenticatedPrincipal principal) {
        final var realmAccess = Optional.ofNullable((Map<String, Object>) principal.getAttributes().get("realm_access"));
        final var roles = realmAccess.flatMap(map -> Optional.ofNullable((List<String>) map.get("roles")));
        return roles.map(List::stream).orElse(Stream.empty()).map(SimpleGrantedAuthority::new)
                .map(GrantedAuthority.class::cast).toList();
    }
}
