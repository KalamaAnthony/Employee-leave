package com.example.Success18.Authentication.Users;

import com.example.Success18.Role.RoleRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
@Slf4j
@AllArgsConstructor
public class UsersService {
    @Autowired
    private UsersRepo usersRepo;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private RoleRepository roleRepository;

    private final Date today = new Date();

    public Users userRegistration(Users user){
        roleRepository.saveAll(user.getRoles());
        user.setCreatedOn(this.today);
        user.setDeletedFlag('N');
//        user.setModifiedOn(this.today);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return usersRepo.save(user);
    }

    public Users updateUser(Users user){
        return usersRepo.save(user);
    }

    public List<Users> getAllUsers(){
        return usersRepo.findAllByDeletedFlag('N');
    }

    public Users getUser(Long id) {
        return usersRepo.findById(id).orElse(null);
    }}
