package com.example.amrat.LearningSpringBootSecurity.entity;


import com.example.amrat.LearningSpringBootSecurity.entity.type.AuthProviderType;
import com.example.amrat.LearningSpringBootSecurity.entity.type.RoleType;
import com.example.amrat.LearningSpringBootSecurity.security.RolePermissionMapping;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(
        name = "app_user",
        indexes = {
                @Index(name = "index_provider_id_provider_type", columnList = "providerId, providerType")
        }
)
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JoinColumn(unique = true)
    private String username;

    private String password;

    private String providerId;

    @Enumerated(EnumType.STRING)
    private AuthProviderType providerType;

    @ElementCollection(fetch = FetchType.EAGER) // this annotation create a new table for user roles
    @Enumerated(EnumType.STRING)
    private Set<RoleType> roles = new HashSet<>();

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
//        return List.of();
        // Difference b/w Role and Authority
        // A role is just a special type of authority with the prefix "ROLE_". Example: "ROLE_ADMIN", "ROLE_PATIENT".
        // An authority is the most basic permission string in Spring Security. Example: "READ_PRIVILEGE", "WRITE_PRIVILEGE".
//        return roles.stream()
//                .map(role -> new SimpleGrantedAuthority("ROLE_"+role.name()))
//                .collect(Collectors.toSet());

        // fetching authorities for each role of user
        Set<SimpleGrantedAuthority> authorities = new HashSet<>();
        roles.forEach(
                role -> {
                    Set<SimpleGrantedAuthority> permissions = RolePermissionMapping.getAuthoritiesForRole(role);
                    authorities.addAll(permissions);
                    authorities.add(new SimpleGrantedAuthority("ROLE_"+role.name()));
                }
        );
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }
}
