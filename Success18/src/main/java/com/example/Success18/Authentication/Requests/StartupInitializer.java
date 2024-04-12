package com.example.Success18.Authentication.Requests;

import com.example.Success18.Authentication.Users.Users;
import com.example.Success18.Authentication.Users.UsersRepo;
import com.example.Success18.Role.Role;
import com.example.Success18.Role.RoleEnum;
import com.example.Success18.Role.RoleRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.Date;

@Component
@Slf4j
public class StartupInitializer implements ApplicationRunner {

    @Autowired
     UsersRepo usersRepo;

    @Autowired
     RoleRepository roleRepository;

    @Autowired
     PasswordEncoder passwordEncoder;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        createSuperUser();
    }

    private void createSuperUser() {
        log.info("------->>>>>> CREATING SUPERUSER <<<<<<-------------");

        if (usersRepo.existsByUsername("superadmin")) {
            log.info("username exists!.Skipping creation");
        }else if (usersRepo.existsByEmail("supperadmin@example.com")) {
            log.info("Superuser with email mailto:superadmin@example.com already exists. Skipping creation.");
        }else{
//            Role superUserRole = roleRepository.findByName(ERole.ROLE_SUPERUSER.toString())
            Role superUserRole = new Role();
            superUserRole.setName(RoleEnum.SUPERUSER.toString());
            roleRepository.save(superUserRole);
            log.info("Role found!");

            Users superUser = new Users();
            superUser.setUsername("superadmin");
            mailto:superUser.setEmail("superadmin@example.com");
            superUser.setPassword(passwordEncoder.encode("Kalama@1010"));
            superUser.setRoles(Collections.singleton(superUserRole));
            superUser.setAcctActive(true);
            superUser.setAcctLocked(false);
            superUser.setStatus("ACTIVE");
            log.info("username = superadmin");
            log.info("superadmin  log in password is = Kalama@1010 ");
            superUser.setVerifiedFlag('Y');
            superUser.setFirstLogin('Y');
            superUser.setVerifiedOn(new Date());
            mailto:superUser.setEmail("superadmin@example.com");
            superUser.setFirstName("Super");
            superUser.setLastName("Admin");
            superUser.setPhoneNo("1234567890");

            usersRepo.save(superUser);
        }
    }
}
