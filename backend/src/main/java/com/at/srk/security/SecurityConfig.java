package com.at.srk.security;

import java.util.*;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import org.springframework.beans.factory.annotation.Value;

import org.springframework.security.oauth2.server.resource.introspection.OpaqueTokenIntrospector;

// import java.util.stream.Stream;
// import org.springframework.core.convert.converter.Converter;
// import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
// import org.springframework.security.core.GrantedAuthority;
// import org.springframework.security.core.authority.SimpleGrantedAuthority;
// import org.springframework.security.oauth2.jwt.Jwt;
// import org.springframework.security.authentication.AbstractAuthenticationToken;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

    @Bean
    UrlBasedCorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(List.of("http://localhost:3001"));
        configuration.setAllowedMethods(List.of("*"));
        configuration.setAllowedHeaders(List.of("*"));
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

    /**
     * JWT Decoder
     */

    // interface AuthoritiesConverter extends Converter<Map<String, Object>,
    // Collection<GrantedAuthority>> {
    // }

    // @Bean
    // AuthoritiesConverter realmRolesAuthoritiesConverter() {
    // return claims -> {
    // final var realmAccess = Optional.ofNullable((Map<String, Object>)
    // claims.get("realm_access"));
    // final var roles = realmAccess.flatMap(map ->
    // Optional.ofNullable((List<String>) map.get("roles")));
    // return
    // roles.map(List::stream).orElse(Stream.empty()).map(SimpleGrantedAuthority::new)
    // .map(GrantedAuthority.class::cast).toList();
    // };
    // }

    // @Bean
    // JwtAuthenticationConverter authenticationConverter(AuthoritiesConverter
    // authoritiesConverter) {
    // JwtAuthenticationConverter jwtAuthenticationConverter = new
    // JwtAuthenticationConverter();
    // jwtAuthenticationConverter
    // .setJwtGrantedAuthoritiesConverter(jwt ->
    // authoritiesConverter.convert(jwt.getClaims()));
    // return jwtAuthenticationConverter;
    // }

    // @Bean
    // SecurityFilterChain securityFilterChain(HttpSecurity http,
    // Converter<Jwt, AbstractAuthenticationToken> jwtAuthenticationConverter)
    // throws Exception {
    // http.oauth2ResourceServer(resourceServer -> {
    // resourceServer.jwt(jwtDecoder -> {
    // jwtDecoder.jwtAuthenticationConverter(jwtAuthenticationConverter);
    // });
    // });

    // http.sessionManagement(sessions -> {
    // sessions.sessionCreationPolicy(SessionCreationPolicy.STATELESS);
    // }).csrf(csrf -> {
    // csrf.disable();
    // });

    // // permit every request, block on specific controller
    // http.authorizeHttpRequests(requests -> {
    // requests.anyRequest().permitAll();
    // });

    // return http.build();
    // }

    /**
     *
     * JWT Introspection
     */

    @Value("${spring.security.oauth2.resourceserver.opaque-token.introspection-uri}")
    private String introspectionUri;

    @Value("${spring.security.oauth2.resourceserver.opaque-token.client-id}")
    private String clientId;

    @Value("${spring.security.oauth2.resourceserver.opaque-token.client-secret}")
    private String clientSecret;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        OpaqueTokenIntrospector introspector = new KeyCloakOpaqueTokenIntrospector(introspectionUri, clientId,
                clientSecret);

        http.oauth2ResourceServer(oauth2 -> oauth2
                .opaqueToken(opaqueToken -> opaqueToken
                        .introspector(introspector)));

        http.sessionManagement(sessions -> {
            sessions.sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        }).csrf(csrf -> {
            csrf.disable();
        });

        http.authorizeHttpRequests(requests -> {
            requests.anyRequest().permitAll();
        });

        return http.build();
    }
}
