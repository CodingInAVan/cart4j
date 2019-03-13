package com.cart4j.auth.entity;

import com.cart4j.model.security.dto.v1.UserDto;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import java.util.List;

@Table(name="c4_user")
@Entity
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class User implements Serializable {
    private static final long serialVersionUID = 6053113692202648976L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String username;
    private String email;

    @ManyToMany(mappedBy = "user")
    private List<AccessToken> accessTokens;
    private String password;
    private Boolean activated;
    private Date createdAt;


    @ManyToMany
    @JoinTable(name = "c4_user_role", joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id"))
    private Collection<Role> roles;

    public UserDto toDto() {
        return UserDto.builder()
                .createdAt(createdAt.getTime())
                .email(email)
                .id(id)
                .password(password)
                .username(username)
                .build();
    }
}
