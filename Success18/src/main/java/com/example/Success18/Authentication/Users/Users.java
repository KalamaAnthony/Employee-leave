package com.example.Success18.Authentication.Users;

import com.example.Success18.Role.Role;
import com.example.Success18.Utilities.StatusEnum;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
@ToString
@NoArgsConstructor
@Entity
@Table (name = "Users")
public class Users  {
    @Id
    @SequenceGenerator(name = "user_seq", allocationSize = 1, initialValue = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_seq")
    @Column(name = "sn", updatable = false)
    private Long sn;
    @Column(name = "username", length = 40, unique = true, nullable = false)
    private String username;
    @Column(name = "firstname",  length = 50)
    private String firstName;
    @Column(name = "lastname", length = 50)
    private String lastName;
    @Column(name = "email", length = 150, nullable = false, unique = true)
    private String email;
    @Column(name = "phone", length = 15)
    private String phoneNo;
    @Column(name = "password", length = 255   , nullable = false)
    private String password;
    @Column(name = "createdOn", length = 50)
    private Date createdOn;
    @Column(name = "modifiedBy", length = 50)
    private String modifiedBy;
    @Column(name = "modifiedOn", length = 50)
    private Date modifiedOn;
    @Column(name = "verifiedBy", length = 50)
    private String verifiedBy;
    @Column(name = "verifiedOn", length = 50)
    private Date verifiedOn;
    @Column(name = "verifiedFlag", length = 5)
    private Character verifiedFlag;
    @Column(name = "deleteFlag", length = 5)
    private Character deletedFlag;
    @Column(name = "deleteby", length = 5)
    private String deletedBy;
    @Column(name = "deleteOn", length = 50)
    private Date deleteOn;
    @Column(name = "deletedOn", length = 50)
    private LocalDateTime deletedOn;
    @Column(name = "active", length = 50)
    private boolean isAcctActive;
    @Column(name = "first_login", length = 1)
    private Character firstLogin = 'Y';
    @Column(name = "locked", length = 15)
    private boolean isAcctLocked;
    private boolean systemGenPassword = true;
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles = new HashSet<>();

    //Operational Audit
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private String status = StatusEnum.PENDING.toString();
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private String adminApprovedBy;
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Character approvedFlag;
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Date approvedTime;
    @JsonProperty(access = JsonProperty.Access.READ_ONLY )
    private String remarks;

//    private final Long id;
//    private final String username;
//    private final String email;
//    //	private final String roleClassification;
//    private final Boolean isAcctActive;
//
//    @JsonIgnore
//    private final String password;
//
//    private final Collection<? extends GrantedAuthority> authorities;

//    public Users(Long id, Collection<? extends GrantedAuthority> authorities) {
//        this.id = id;
//        this.authorities = authorities;
////    }

//    public Users(Long id, Collection<? extends GrantedAuthority> authorities) {
//        this.id = id;
//        this.authorities = authorities;
//    }


    //private final Collection<? extends GrantedAuthority> authorities;



//    public Users(Collection<? extends GrantedAuthority> authorities) {
//        this.authorities = authorities;
//    }

//    public static Users build(Users user) {
//        //TODO:Retrieves a collection of roles associated with the user and converts them to a stream
//        List<GrantedAuthority> authorities = user.getRoles().stream()
//        //TODO:maps every role to a an instance of simple granted authority with the role name
//
//                .map(role -> new SimpleGrantedAuthority(role.getRoleName()))
//
//                //TODO:collects the simpleGrantedAuthorities objects into a list
//
//                .collect(Collectors.toList());
//                //TODO:Returns an instance of the user
//        return new Users(
//                user.getSn(),
//                user.getUsername(),
//                user.getEmail(),
//                user.isAcctActive(),
//                user.getPassword(),
//                authorities);
//
//
//    }
//    public Users (Long id, String userName, String email, String password,
//                  Collection<? extends GrantedAuthority> authorities, Collection<? extends GrantedAuthority> authorities1) {
//        this.id = id;
//        this.userName = userName;
//        this.email = email;
////		this.roleClassification = String.valueOf(roleClassification);
//        //this.isAcctActive = isAcctActive;
//        this.password = password;
////        this.authorities = authorities;
//        this.authorities = authorities1;
//    }
//
//    @Override
//    public Collection<? extends GrantedAuthority> getAuthorities() {
//        return authorities;
//    }
//
//    @Override
//    public String getPassword() {
//        return password;
//    }
//
//    @Override
//    public String getUsername() {
//        return email;
//    }
//
//    @Override
//    public boolean isAccountNonExpired() {
//        return true;
//    }
//
//    @Override
//    public boolean isAccountNonLocked() {
//        return true;
//    }
//
//    @Override
//    public boolean isCredentialsNonExpired() {
//        return true;
//    }
//
//    @Override
//    public boolean isEnabled() {
//        return true;
//    }
}
