package com.example.Success18.Authentication.UserDetails;

import com.example.Success18.Authentication.Users.Users;
import com.example.Success18.Authentication.Users.UsersRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class DetailsOfUsersService implements UserDetailsService {
    @Autowired
    UsersRepo usersRepo;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Users user = usersRepo.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User Not Found with username: " + username));

//todo: When a user is found , the build method is called upon which returns user details and granted authorities
        return DetailsOfUsers.build(user);
    }
}
