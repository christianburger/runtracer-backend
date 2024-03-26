package com.runtracer.runtracerbackend.repository;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.InMemoryReactiveClientRegistrationRepository;
import org.springframework.security.oauth2.client.registration.ReactiveClientRegistrationRepository;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.oidc.IdTokenClaimNames;

@Configuration
@Slf4j
public class ReactiveOAuth2ClientRegistrationRepository {

    @Value("${security.oauth2.client.registration.google.client-id}")
    private String clientId;

    @Value("${security.oauth2.client.registration.google.client-secret}")
    private String clientSecret;

    @Value("${security.oauth2.client.registration.google.redirect-uri}")
    private String redirectUri;

    @Bean
    public ReactiveClientRegistrationRepository clientRegistrationRepository() {
        log.info("Client ID: {}", clientId);
        log.info("Client Secret: {}", clientSecret);
        log.info("Redirect URI: {}", redirectUri);

        ClientRegistration registration = ClientRegistration.withRegistrationId("google")
                .clientId(clientId)
                .clientSecret(clientSecret)
                .redirectUri(redirectUri)
                .authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE)
                .scope("openid", "profile", "email")
                .authorizationUri("https://accounts.google.com/o/oauth2/v2/auth")
                .tokenUri("https://www.googleapis.com/oauth2/v4/token")
                .userInfoUri("https://www.googleapis.com/oauth2/v3/userinfo")
                .userNameAttributeName(IdTokenClaimNames.SUB)
                .jwkSetUri("https://www.googleapis.com/oauth2/v3/certs")
                .clientName("Google")
                .build();

        log.info("Client Registration: {}", registration);

        return new InMemoryReactiveClientRegistrationRepository(registration);
    }
}