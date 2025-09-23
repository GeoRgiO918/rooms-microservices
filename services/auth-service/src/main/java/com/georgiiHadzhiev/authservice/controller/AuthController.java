package com.georgiiHadzhiev.authservice.controller;

import com.georgiiHadzhiev.authservice.dto.LoginRequest;
import com.georgiiHadzhiev.authservice.dto.RegisterRequest;
import org.keycloak.OAuth2Constants;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.KeycloakBuilder;
import org.keycloak.representations.AccessTokenResponse;
import org.keycloak.representations.idm.UserRepresentation;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.ws.rs.core.Response;
import java.util.List;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final Keycloak keycloak;

    @Value("${keycloak.realm}")
    private String realm;
    @Value("${keycloak.server-url}")
    private String serverUrl;
    @Value("${keycloak.client-id}")
    private String clientId;
    @Value("${keycloak.client-secret}")
    private String clientSecret;


    public AuthController(Keycloak keycloak) {
        this.keycloak = keycloak;
    }

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody RegisterRequest req) {
        UserRepresentation user = new UserRepresentation();
        user.setUsername(req.getUsername());
        user.setEmail(req.getEmail());
        user.setEnabled(true);

        CredentialRepresentation cred = new CredentialRepresentation();
        cred.setType(CredentialRepresentation.PASSWORD);
        cred.setTemporary(false);
        cred.setValue(req.getPassword());
        user.setCredentials(List.of(cred));

        Response response = keycloak.realm(realm).users().create(user);

        return response.getStatus() == 201
                ? ResponseEntity.ok("User created")
                : ResponseEntity.status(response.getStatus())
                .body("Error: " + response.getStatus());
    }

    @PostMapping("/login")
    public ResponseEntity<AccessTokenResponse> login(@RequestBody LoginRequest req) {
        Keycloak kcUser = KeycloakBuilder.builder()
                .serverUrl(serverUrl)
                .realm(realm)
                .clientId(clientId)
                .clientSecret(clientSecret)
                .username(req.getUsername())
                .password(req.getPassword())
                .grantType(OAuth2Constants.PASSWORD)
                .build();

        // возвращаем полностью объект токена (access, refresh, expiresIn и т.д.)
        return ResponseEntity.ok(kcUser.tokenManager().getAccessToken());
    }
}