package com.example.Success18.Authentication.UserDetails;

import com.example.Success18.Authentication.Users.Users;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
//todo: (Returned or)Used with detailsOfUsersService to loadUser from database during authentications
public class DetailsOfUsers implements UserDetails {

    private static final long serialVersionUID = 1L;
    private final Long id;
    private final String username;
    private final String email;
    private final boolean isAcctActive;

    @JsonIgnore
    private final String password;


    //TODO:USER AUTHORITIES
    private final Collection<? extends GrantedAuthority> authorities;
//TODO: A CONSTRUCTOR TO INITIALIZE THE USER FIELDS
    public DetailsOfUsers(Long id, String username, String email, boolean isAcctActive, String password, Collection<? extends GrantedAuthority> authorities) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.isAcctActive = isAcctActive;
        this.password = password;
        this.authorities = authorities;
    }

//TODO: A METHOD TO CREATE AN INSTANCE OF DetailsOfUsers FROM A USERS OBJECT
    public static DetailsOfUsers build(Users user) {
        //TODO:Retrieves a collection of roles associated with the user and converts them to a stream
        List<GrantedAuthority> authorities = user.getRoles().stream()
                //TODO:maps every role to a an instance of simple granted authority with the role name(converts roles to authorities)

                .map(role -> new SimpleGrantedAuthority(role.getName()))

                //TODO:collects the simpleGrantedAuthorities objects into a list

                .collect(Collectors.toList());
        //TODO:Returns an instance of the DetailsOfUser WITH AUTHORITIES and user's details
        return new DetailsOfUsers(
                user.getSn(),
                user.getUsername(),
                user.getEmail(),
                user.isAcctActive(),
                user.getPassword(),
                authorities);
    }


    @Override
    //todo: Overridden to provide user specifications
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    public String getEmail(){
        return email;
    }


    public Long getId(){
        return id;
    }

    public Boolean getAcctActive(){
        return isAcctActive;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }


}
