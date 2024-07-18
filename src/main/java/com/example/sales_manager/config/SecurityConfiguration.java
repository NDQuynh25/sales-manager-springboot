package com.example.sales_manager.config;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtEncoder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.config.Customizer;
import com.nimbusds.jose.jwk.source.ImmutableSecret;
import com.nimbusds.jose.util.Base64;

@Configuration
@EnableMethodSecurity(securedEnabled = true)
public class SecurityConfiguration {

    public static final MacAlgorithm JWT_ALGORITHM = MacAlgorithm.HS256;

    private final CustomAuthenticationEntryPoint customAuthenticationEntryPoint;

    @Value("${jwt.base64-secret}")
    private String jwtKey;

    @Value("${jwt.access-token-validity-in-seconds}")
    private long expiration;

    public SecurityConfiguration(CustomAuthenticationEntryPoint customAuthenticationEntryPoint) {
        this.customAuthenticationEntryPoint = customAuthenticationEntryPoint;
    }




    // Phương thức để lấy SecretKey từ chuỗi base64 được cấu hình
    private SecretKey getSecretKey() {
        byte[] keyBytes = Base64.from(jwtKey).decode();
        return new SecretKeySpec(keyBytes, 0, keyBytes.length, JWT_ALGORITHM.getName());
    }

    // Bean để cấu hình JwtEncoder dựa trên NimbusJwtEncoder
    @Bean
    public JwtEncoder jwtEncoder() {
        return new NimbusJwtEncoder(new ImmutableSecret<>(getSecretKey()));
    }

    /**
     * Bean để tạo JwtDecoder sử dụng secret key và algorithm đã được cấu hình.
     * Lambda expression được sử dụng để thực hiện việc giải mã token.
     * @return JwtDecoder để giải mã token JWT
     */
    @Bean
    public JwtDecoder jwtDecoder() {
        NimbusJwtDecoder jwtDecoder = NimbusJwtDecoder.withSecretKey(getSecretKey()).macAlgorithm(JWT_ALGORITHM).build();
        // Ghi đè phương thức decode của JwtDecoder để bắt lỗi khi giải mã token
        return token -> {
            try {
                return jwtDecoder.decode(token);
            } catch (Exception e) {
                System.out.println(">>> Error decoding token: " + e.getMessage());
                throw e;
            }
        };
    }

    // Bean để cấu hình PasswordEncoder, sử dụng BCryptPasswordEncoder
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


    @Bean
    public JwtAuthenticationConverter jwtAuthenticationConverter() {
        JwtGrantedAuthoritiesConverter grantedAuthoritiesConverter = new JwtGrantedAuthoritiesConverter();
        grantedAuthoritiesConverter.setAuthorityPrefix("");
        grantedAuthoritiesConverter.setAuthoritiesClaimName("user");

        JwtAuthenticationConverter jwtAuthenticationConverter = new JwtAuthenticationConverter();
        jwtAuthenticationConverter.setJwtGrantedAuthoritiesConverter(grantedAuthoritiesConverter);
        return jwtAuthenticationConverter;
    }

    /**
     * Cấu hình bộ lọc bảo mật của ứng dụng.
     * CSRF được disable, các endpoint "/api/v1/auth/login" và "/" được phép public,
     * tất cả các yêu cầu khác yêu cầu xác thực.
     * Cấu hình OAuth2 Resource Server để xác thực và ủy quyền dựa trên JWT.
     * Session được quản lý theo chế độ STATELESS.
     * @param http HttpSecurity để cấu hình bộ lọc bảo mật
     * @return SecurityFilterChain được cấu hình
     * @throws Exception Ném ra khi xảy ra lỗi trong quá trình cấu hình
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .csrf(f -> f.disable())
            .cors(Customizer.withDefaults()) // Cấu hình CORS
            .authorizeHttpRequests(
                authz -> authz
                    .requestMatchers("/", "/api/v1/auth/**", "/api/v1/auth/refresh").permitAll()
                    // .anyRequest().permitAll())
                    .anyRequest().authenticated()) // Tất cả các yêu cầu khác cần xác thực

            .oauth2ResourceServer(
                oauth2 -> oauth2
                    .jwt(Customizer.withDefaults()) // Cấu hình OAuth2 Resource Server và JWT
                    .authenticationEntryPoint(this.customAuthenticationEntryPoint)) // Xử lý lỗi xác thực


            .formLogin(f -> f.disable())

            .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        return http.build();
    }

}
