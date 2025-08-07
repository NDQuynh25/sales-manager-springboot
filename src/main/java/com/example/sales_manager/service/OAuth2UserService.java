package com.example.sales_manager.service;

import com.example.sales_manager.dto.OAuth2UserInfo;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.gson.GsonFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.Map;

@Service
public class OAuth2UserService {

    private final GoogleIdTokenVerifier googleVerifier;

    public OAuth2UserService(@Value("${spring.security.oauth2.client.registration.google.client-id}") String googleClientId) {
        this.googleVerifier = new GoogleIdTokenVerifier.Builder(new NetHttpTransport(), new GsonFactory())
                .setAudience(Collections.singletonList(googleClientId))
                .build();
    }

    // Google
    public OAuth2UserInfo verifyGoogleToken(String idTokenString) throws Exception {
        GoogleIdToken idToken = googleVerifier.verify(idTokenString);
        System.out.println(">>> [INFO] Verifying Google ID token: " + idTokenString);
        if (idToken != null) {
            GoogleIdToken.Payload payload = idToken.getPayload();
            System.out.println(">>> [INFO] payload: " + payload);
            return new OAuth2UserInfo(
                    payload.getEmail(),
                    (String) payload.get("name"),
                    (String) payload.get("picture")
            );
        } else {
            throw new Exception("Invalid Google ID token.");
        }
    }

    // Facebook
    public OAuth2UserInfo verifyFacebookToken(String accessToken) {
        String url = "https://graph.facebook.com/me?fields=id,name,email,picture&access_token=" + accessToken;
        RestTemplate restTemplate = new RestTemplate();

        try {
            Map<String, Object> result = restTemplate.getForObject(url, Map.class);
            if (result == null || result.get("email") == null) {
                throw new RuntimeException("Facebook token does not contain required fields");
            }

            Map<String, Object> pictureObj = (Map<String, Object>) result.get("picture");
            Map<String, Object> dataObj = (Map<String, Object>) pictureObj.get("data");
            String pictureUrl = (String) dataObj.get("url");

            return new OAuth2UserInfo(
                    (String) result.get("email"),
                    (String) result.get("name"),
                    pictureUrl
            );
        } catch (Exception e) {
            throw new RuntimeException("Invalid Facebook token");
        }
    }
}
