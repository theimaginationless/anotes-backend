package com.anotes.entity;

import lombok.*;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.Set;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Table(name = "`user`")
public class User extends DatedEntity {

    @Id
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "seq_user"
    )
    @SequenceGenerator(
            name = "seq_user",
            allocationSize = 5
    )
    private Long id;

    @Column(unique = true, nullable = false)
    private String nickname;

    private String fullName;

    @Column(nullable = false)
    private String password;

    @Type(type = "json")
    @Column(columnDefinition = "json")
    private Set<String> authorities;

    private Boolean enabled;

    public User(String nickname, String fullName, String password, Set<String> authorities, Boolean enabled) {
        this.nickname = nickname;
        this.fullName = fullName;
        this.password = password;
        this.authorities = authorities;
        this.enabled = enabled;
    }
}
