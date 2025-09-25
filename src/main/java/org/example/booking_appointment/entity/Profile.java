package org.example.booking_appointment.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.example.booking_appointment.enums.ROLE;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.util.LinkedHashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "profiles")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Profile {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "email")
    private String email;

    @Column(name = "password")
    private String password;

    @Column(name = "date_of_birth")
    private LocalDate dateOfBirth;

    @Column(name = "role")
    @Enumerated(EnumType.STRING)
    private ROLE role;

    @Column(name = "is_verified")
    private boolean isVerified = false;

    @Column(name = "two_factor_email")
    private boolean twoFactorEmail = false;

    @Column(name = "verification_code")
    private String verificationCode;

    @Column(name = "verification_code_expiration")
    private LocalDateTime verificationCodeExpiration;

    @Column(name = "created_at", insertable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at", updatable = false, insertable = false)
    private LocalDateTime updatedAt;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "avatar_id")
    private Avatar avatar;

    @OneToOne(mappedBy = "profile", cascade = CascadeType.ALL)
    private Admin admin;

    @OneToOne(mappedBy = "profile", cascade = CascadeType.ALL)
    private HotelOwner hotelOwner;

    @OneToOne(mappedBy = "profile", cascade = CascadeType.ALL)
    private User user;


    public static boolean isAdult(LocalDate dob) {
        return Period.between(dob, LocalDate.now()).getYears() >= 18;
    }

}