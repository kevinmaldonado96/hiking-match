package org.example.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

@Data
@Entity
@NoArgsConstructor
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;
    private String password;
    private LocalDateTime createDate;
    private LocalDateTime lastLogin;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "personal_information_id")
    private PersonalInformation personalInformation;

    @NotNull
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "role_id")
    private Role role;

    @Builder
    public User(String username, String password, PersonalInformation personalInformation, Role role) {
        this.username = username;
        this.password = password;
        this.personalInformation = personalInformation;
        this.role = role;
    }

    public Collection<? extends GrantedAuthority> getRoleAuthorities() {
        return List.of(new SimpleGrantedAuthority(this.role.getRole()));
    }

}
