package com.example.Success18.Authentication.Users;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UsersRepo extends JpaRepository <Users , Long> {
    boolean existsByUsername(String username);

    boolean existsByEmail(String mail);

   

    Optional<Users> findByEmail(String email);

    Optional<Users> findByUsername(String username);

    List<Users> findAllByDeletedFlag(char n);

    //Optional<Users> findByUserName(String username);

//    Optional<Users> findByEmail(String email);
//
//    Optional<Users> findByUsername(String username);
//    Boolean existsByEmail(String email);
//    Boolean existsByUsername(String username);
}
