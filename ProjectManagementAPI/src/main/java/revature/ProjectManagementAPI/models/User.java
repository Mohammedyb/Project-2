package revature.ProjectManagementAPI.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
/*@AllArgsConstructor*/
@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "email", nullable = false, length = 40)
    private String email;

    @Column(name = "password", length = 40)
    private String password;

    @Column(name = "name", length = 40)
    private String name;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "project_id")
    private Project projects;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "role")
    private Role roles;

    @Enumerated(EnumType.STRING)
    @Column(name = "auth_provider")
    private AuthenticationProvider authProvider;

    /*@Column(name = "refresh_token")
    private String refreshToken;*/

    public User(Integer id, String email, String password, String name, Project projects, Role roles, AuthenticationProvider authProvider) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.name = name;
        this.projects = projects;
        this.roles = roles;
        this.authProvider = authProvider;
        /*this.refreshToken = "NONE";*/
    }

}