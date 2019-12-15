package com.engineering.shop.users;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "ROLES")
@Data
@RequiredArgsConstructor
@NoArgsConstructor
public class UserRole {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    int id;

    @NonNull
    @Getter(AccessLevel.PUBLIC)
    @Enumerated(EnumType.STRING)
    private UserRoleType type;

    @Override
    public String toString() {
        return type.toString();
    }
}

