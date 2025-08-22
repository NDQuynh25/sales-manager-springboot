package com.example.sales_manager.entity;

import com.example.sales_manager.util.constant.GenderEnum;
import com.example.sales_manager.util.constant.AuthProviderEnum;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;

import lombok.*;
import java.io.Serializable;
import java.security.AuthProvider;
import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "users", uniqueConstraints = {
    @UniqueConstraint(columnNames = "email"),
    @UniqueConstraint(columnNames = "phone_number")
})
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class User extends BaseEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "full_name")
    private String fullName;

    @Email
    @Column(name = "email", unique = true)
    private String email;

    @Column(name = "phone_number")
    private String phoneNumber;

    @JsonIgnore
   
    @Column(name = "password")
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(name = "gender")
    private GenderEnum gender;

    // Quan hệ n-1 với Role (tránh cascade ALL để không xóa Role khi xóa User)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "role_id", nullable = false)
    @JsonBackReference
    @JsonIgnoreProperties("users") // tránh vòng lặp JSON nếu Role có List<User>
    private Role role;

    // Quan hệ 1-1 với Cart
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "cart_id")
    @JsonBackReference
    @JsonIgnoreProperties("user")
    private Cart cart;

    // Quan hệ 1-n với Order
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonIgnore
    private List<Order> orders;

    // Quan hệ 1-n với FeedbackReview
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonIgnore
    private List<FeedbackReview> feedbackReviews;

    @Column(columnDefinition = "TEXT")
    private String address;

    @Column(name = "date_of_birth")
    private LocalDate dateOfBirth;

    @Column(name = "facebook_account_id", columnDefinition = "TEXT")
    private String facebookAccountId;

    @Column(name = "google_account_id", columnDefinition = "TEXT")
    private String googleAccountId;

    @Enumerated(EnumType.STRING)
    @Column(name = "auth_provider", nullable = false)
    private AuthProviderEnum authProvider = AuthProviderEnum.LOCAL;

    @Column(name = "avatar", columnDefinition = "TEXT")
    private String avatar;

    @JsonIgnore
    @Column(name = "refresh_token", columnDefinition = "TEXT")
    private String refreshToken;
}
