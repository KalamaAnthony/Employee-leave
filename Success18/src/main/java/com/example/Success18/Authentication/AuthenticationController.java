package com.example.Success18.Authentication;

import com.example.Success18.Authentication.Requests.LoginRequest;
import com.example.Success18.Authentication.Requests.SignUp;
import com.example.Success18.Authentication.Responses.JwtResponse;
import com.example.Success18.Authentication.SecurityConfig.JwtService;
import com.example.Success18.Authentication.UserDetails.DetailsOfUsers;
import com.example.Success18.Authentication.Users.*;
import com.example.Success18.Role.Role;
import com.example.Success18.Role.RoleEnum;
import com.example.Success18.Role.RoleRepository;
import com.example.Success18.Utilities.EntityResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api/v1/auth")
@Slf4j
public class AuthenticationController {

    @Autowired
     UsersRepo usersRepo;
    @Autowired
    PasswordEncoder encoder;
    @Autowired
    RoleRepository roleRepository;
    @Autowired
     JwtService jwtService;


    @Autowired
     AuthenticationManager authenticationManager;

    @PostMapping("/signUp")

    public EntityResponse signUp(@RequestBody SignUp signUp) {
        EntityResponse response = new EntityResponse<>();
        try {
            if(!signUp.getPassword().equals(signUp.getConfirmPassword())){
                response.setMessage("The password do not match, kindly confirm the password correctly");
                response.setStatusCode(HttpStatus.NOT_ACCEPTABLE.value());
                return response;
            }
            Optional<Users> checkUserName = usersRepo.findByUsername(signUp.getUsername());
            Optional<Users> checkEmail = usersRepo.findByEmail(signUp.getEmail());
            if(checkUserName.isPresent()){
                response.setMessage("User name already exist, create another userName");
                response.setStatusCode(HttpStatus.NOT_ACCEPTABLE.value());
                return response;
            }else if (checkEmail.equals(signUp.getEmail())){
                response.setMessage("Email already exist");
                response.setStatusCode(HttpStatus.NOT_ACCEPTABLE.value());
                return response;

            }else {
                Users user = new Users();
                user.setUsername(signUp.getUsername());
                user.setEmail(signUp.getEmail());
                user.setPassword(encoder.encode(signUp.getPassword()));

                Set<String> strRoles = signUp.getRole();
                Set<Role> roles = new HashSet<>();



                if (strRoles == null || strRoles.isEmpty()) {
                    response.setMessage("Enter a role for the user");
                    Role userRole = roleRepository.findByName(RoleEnum.USER.toString())
                            .orElseThrow(() -> new RuntimeException("Role not found."));
                    roles.add(userRole);
                } else {
                    for (String role : signUp.getRole()) {
                        try {
                            System.out.println("-------->>>creating role<<<----------");
                            Role userRole = roleRepository.findByName(role)
                                    .orElseThrow(() -> new RuntimeException("Role not found."));
                            roles.add(userRole);
                        } catch (RuntimeException e) {
                            response.setMessage("Role not found: " + role);
                            return response;
                        }
                    }
                }

//                    user.setRoles(roles);
//                    user.setCreatedOn(new Date());
//                    user.setDeletedFlag('N');
//                    user.setAcctActive(false);
//                    user.setAcctLocked(false);
//                    user.setStatus("PENDING");
//                    user.setVerifiedFlag('Y');
//                    user.setFirstLogin('Y');
//                    user.setVerifiedOn(new Date());
                user.setEmail(signUp.getEmail());
                user.setFirstName(signUp.getFirstName());
                user.setLastName(signUp.getLastName());
                user.setPhoneNo(signUp.getPhoneNo());

                 usersRepo.save(user);
                 response.setMessage("User created successfully");




            }

        }catch(Exception e){
            response.setMessage("An error occurred");
        }
        return response;
}

    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest, HttpServletResponse res) throws MessagingException {
        EntityResponse response = new EntityResponse<>();
        // Check if the user exists based on the username
        Optional<Users> existingUserOptional = usersRepo.findByUsername(loginRequest.getUsername());
        if (existingUserOptional.isEmpty()) {
            response.setMessage("User not found.");
            response.setStatusCode(HttpStatus.NOT_FOUND.value());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
        Users existingUser = existingUserOptional.get();
        try {
            if (existingUser.getStatus().equals("PENDING")) {
                response.setMessage("Your status is pending approval, contact the Admin!");
                response.setStatusCode(HttpStatus.NOT_ACCEPTABLE.value());
                return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(response);
            }
            System.out.println("Authentication----------------------------------------------------------------------");
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));
            SecurityContextHolder.getContext().setAuthentication(authentication);
            String jwt = jwtService.generateJwtToken(authentication);
            Cookie jwtTokenCookie = new Cookie("user-id", "c2FtLnNtaXRoQGV4YW1wbGUuY29t");
            jwtTokenCookie.setMaxAge(86400);
            System.out.println("hello lets proceed");
            jwtTokenCookie.setSecure(true);
            jwtTokenCookie.setHttpOnly(true);
            jwtTokenCookie.setPath("/user/");
            res.addCookie(jwtTokenCookie);
            Cookie accessTokenCookie = new Cookie("accessToken", jwt);
            accessTokenCookie.setMaxAge(1 * 24 * 60 * 60); // expires in 1 day
            accessTokenCookie.setSecure(true);
            accessTokenCookie.setHttpOnly(true);
            res.addCookie(accessTokenCookie);
            Cookie userNameCookie = new Cookie("username", loginRequest.getUsername());
            accessTokenCookie.setMaxAge(1 * 24 * 60 * 60); // expires in 1 day
            res.addCookie(userNameCookie);
            DetailsOfUsers userDetails = (DetailsOfUsers) authentication.getPrincipal();
            List<String> roles = userDetails.getAuthorities().stream()
                    .map(item -> item.getAuthority())
                    .collect(Collectors.toList());

            JwtResponse jwtResponse = new JwtResponse();
            jwtResponse.setToken(jwt);
            jwtResponse.setType("Bearer");
            jwtResponse.setId(userDetails.getId());
            jwtResponse.setUsername(userDetails.getUsername());
            jwtResponse.setEmail(userDetails.getEmail());
            jwtResponse.setRoles(roles);
            jwtResponse.setFirstLogin(existingUser.getFirstLogin());
            jwtResponse.setIsAcctActive(userDetails.getAcctActive());
            jwtResponse.setPhoneNo(existingUser.getPhoneNo());
            jwtResponse.setFirstName(existingUser.getFirstName());
            jwtResponse.setLastName(existingUser.getLastName());
            jwtResponse.setStatus("APPROVED");
            jwtResponse.setIsAcctActive(true);

            response.setMessage("successfully signed in");
            response.setStatusCode(HttpStatus.CREATED.value());
            response.setEntity(jwtResponse);

            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            log.error("Exception {}",e);
            response.setMessage("An error occurred during user authentication.");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
}
    @PostMapping("/admin/signin")
    public ResponseEntity<?> signin(@Valid @RequestBody LoginRequest loginRequest, HttpServletResponse res) throws MessagingException {
        System.out.println("Authentication----------------------------------------------------------------------");
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));
        Users user = usersRepo.findByUsername(loginRequest.getUsername()).orElse(null);
        log.info("Username is {}", loginRequest.getUsername());
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtService.generateJwtToken(authentication);
        Cookie jwtTokenCookie = new Cookie("user-id", "c2FtLnNtaXRoQGV4YW1wbGUuY29t");
        jwtTokenCookie.setMaxAge(86400);
        jwtTokenCookie.setSecure(true);
        jwtTokenCookie.setHttpOnly(true);
        jwtTokenCookie.setPath("/user/");
        res.addCookie(jwtTokenCookie);
        Cookie accessTokenCookie = new Cookie("accessToken", jwt);
        accessTokenCookie.setMaxAge(1 * 24 * 60 * 60); // expires in 1 day
        accessTokenCookie.setSecure(true);
        accessTokenCookie.setHttpOnly(true);
        res.addCookie(accessTokenCookie);
        Cookie userNameCookie = new Cookie("username", loginRequest.getUsername());
        accessTokenCookie.setMaxAge(1 * 24 * 60 * 60); // expires in 1 day
        res.addCookie(userNameCookie);
        DetailsOfUsers userDetails = (DetailsOfUsers) authentication.getPrincipal();
        List<String> roles = userDetails.getAuthorities().stream()
                .map(item -> item.getAuthority())
                .collect(Collectors.toList());
//        String otp = "Your otp code is " + otpService.generateOTP(userDetails.getUsername());
//        mailService.sendEmail(userDetails.getEmail(), otp, "OTP Code");

        JwtResponse response = new JwtResponse();
        response.setToken(jwt);
        response.setType("Bearer");
        response.setId(userDetails.getId());
        response.setUsername(userDetails.getUsername());
        response.setEmail(userDetails.getEmail());
        response.setRoles(roles);
//        response.setSolCode(user.getSolCode());
//        response.setEmpNo(user.getEmpNo());
        response.setFirstLogin(user.getFirstLogin());
//        response.setRoleClassification(user.getRoleClassification());
        response.setIsAcctActive(userDetails.getAcctActive());
        return ResponseEntity.ok(response);
    }
    @PutMapping("/approveOrReject")
    public ResponseEntity<?> approveOrReject(@RequestBody List<UserStatusDTO>userStatusDTOList , @RequestParam String remarks){
        EntityResponse response = new EntityResponse<>();
        try {
            if (userStatusDTOList.isEmpty()){
                response.setMessage("you must provide at least one user for approval or rejection");
                response.setStatusCode(HttpStatus.NOT_ACCEPTABLE.value());
            }else{
                List<Users> updatedUsers = new ArrayList<>();
                for (UserStatusDTO userStatusDTO :userStatusDTOList){
                    Optional<Users>OptionalUser = usersRepo.findById(userStatusDTO.getSn());
                    if (OptionalUser.isPresent()){
                        Users user = OptionalUser.get();
                        String Status = userStatusDTO.getStatus().toUpperCase();
                        switch (Status){
                            case "APPROVED":
                                user.setStatus("APPROVED");
                                user.setAdminApprovedBy(UserRequestContext.getCurrentUser());
                                user.setApprovedTime(new Date());
                                user.setApprovedFlag('Y');
                                break;
                            case "REJECTED":
                                user.setStatus("REJECTED");
                                user.setApprovedFlag('N');
                                user.setAdminApprovedBy(null);
                                user.setApprovedTime(null);
                                break;
                            default:
                                response.setMessage("Invalid Status provided: "+ Status);
                                response.setStatusCode(HttpStatus.NOT_ACCEPTABLE.value());
                                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);


                        }
                        updatedUsers.add(usersRepo.save(user));

                    }else {
                        response.setMessage("No user found with such an Id: "+userStatusDTO.getSn());
                        response.setStatusCode(HttpStatus.NOT_FOUND.value());
                        return ResponseEntity.status(HttpStatus.NOT_FOUND.value()).body(response);

                    }

                }
                response.setMessage("User Status updated Successfully");
                response.setStatusCode(HttpStatus.OK.value());
                response.setEntity(updatedUsers);

            }

        }catch (Exception e){
            log.error(e.toString());
            response.setMessage("An unexpected error occurred while updating status");
            response.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);

        }
        return ResponseEntity.ok(response);


    }

//    @PostMapping("/userSignIn")
//    public ResponseEntity<?> userSignIn(@Valid @RequestBody LoginRequest loginRequest, HttpServletResponse res) throws MessagingException {
//        EntityResponse response = new EntityResponse<>();
//        // Check if the user exists based on the username
//        Optional<Users> existingUserOptional = usersRepo.findByUsername(loginRequest.getUsername());
//        if (existingUserOptional.isEmpty()) {
//            response.setMessage("User not found.");
//            response.setStatusCode(HttpStatus.NOT_FOUND.value());
//            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
//        }
//        Users existingUser = existingUserOptional.get();
//        try {
//            if (existingUser.getStatus().equals("PENDING")) {
//                response.setMessage("Your status is pending approval, contact the Admin!");
//                response.setStatusCode(HttpStatus.NOT_ACCEPTABLE.value());
//                return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(response);
//            }
//            System.out.println("Authentication----------------------------------------------------------------------");
//            Authentication authentication = authenticationManager.authenticate(
//                    new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));
//            SecurityContextHolder.getContext().setAuthentication(authentication);
//            String jwt = jwtService.generateJwtToken(authentication);
//            Cookie jwtTokenCookie = new Cookie("user-id", "c2FtLnNtaXRoQGV4YW1wbGUuY29t");
//            jwtTokenCookie.setMaxAge(86400);
//            jwtTokenCookie.setSecure(true);
//            jwtTokenCookie.setHttpOnly(true);
//            jwtTokenCookie.setPath("/user/");
//            res.addCookie(jwtTokenCookie);
//            Cookie accessTokenCookie = new Cookie("accessToken", jwt);
//            accessTokenCookie.setMaxAge(1 * 24 * 60 * 60); // expires in 1 day
//            accessTokenCookie.setSecure(true);
//            accessTokenCookie.setHttpOnly(true);
//            res.addCookie(accessTokenCookie);
//            Cookie userNameCookie = new Cookie("username", loginRequest.getUsername());
//            accessTokenCookie.setMaxAge(1 * 24 * 60 * 60); // expires in 1 day
//            res.addCookie(userNameCookie);
//            DetailsOfUsers userDetails = (DetailsOfUsers) authentication.getPrincipal();
//            List<String> roles = userDetails.getAuthorities().stream()
//                    .map(item -> item.getAuthority())
//                    .collect(Collectors.toList());
//
////            String otp = otpService.generateOTP(userDetails.getUsername());
////            sendOtpEmail(userDetails.getEmail(), otp);
//
//            JwtResponse jwtResponse = new JwtResponse();
//            jwtResponse.setToken(jwt);
//            jwtResponse.setType("Bearer");
//            jwtResponse.setId(userDetails.getId());
//            jwtResponse.setUsername(userDetails.getUsername());
//            jwtResponse.setEmail(userDetails.getEmail());
//            jwtResponse.setRoles(roles);
//            jwtResponse.setFirstLogin(existingUser.getFirstLogin());
//            jwtResponse.setIsAcctActive(userDetails.getAcctActive());
//            jwtResponse.setPhoneNo(existingUser.getPhoneNo());
//            jwtResponse.setFirstName(existingUser.getFirstName());
//            jwtResponse.setLastName(existingUser.getLastName());
//            jwtResponse.setStatus("APPROVED");
//            jwtResponse.setIsAcctActive(true);
//
//            response.setMessage("Hi, " + loginRequest.getUsername() + ", welcome to ERP solution");
//            response.setStatusCode(HttpStatus.CREATED.value());
//            response.setEntity(jwtResponse);
//
//            return new ResponseEntity<>(response, HttpStatus.OK);
//        } catch (Exception e) {
//            log.error("Exception {}",e);
//            response.setMessage("An error occurred during user authentication.");
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
//        }

}
