package com.blog.controller;

import com.blog.entity.Role;
import com.blog.entity.User;
import com.blog.payload.JWTAuthResponse;
import com.blog.payload.LoginDto;
import com.blog.payload.SignUpDto;
import com.blog.repository.RoleRepository;
import com.blog.repository.UserRepository;


import com.blog.security.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    @Autowired
    private JwtTokenProvider tokenProvider;
    @Autowired
    private AuthenticationManager authenticationManager;


    @Autowired
     private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

     //http://localhost:8080/api/auth/signup
    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@RequestBody SignUpDto signUpDto){

        if(userRepository.existsByEmail(signUpDto.getEmail())){
        return new ResponseEntity<>("Email already exists with the email::"+signUpDto.getEmail(),HttpStatus.INTERNAL_SERVER_ERROR);

        }

        if(userRepository.existsByUsername(signUpDto.getUsername())){
            return new ResponseEntity<>("Email already exists with the username::"+signUpDto.getUsername(),HttpStatus.INTERNAL_SERVER_ERROR);

        }


    User user = new User();
    user.setName(signUpDto.getName());
    user.setUsername(signUpDto.getUsername());
    user.setEmail(signUpDto.getEmail());
    user.setPassword(passwordEncoder.encode(signUpDto.getPassword()));


        Role role = new Role();
        role.setId(1);
        role.setName("ROLE_ADMIN");

        Role role1 = new Role(); // for  defining  new role to the user you need to  create new method like this  means signup 2 which will directly
                                   //  map the incoming  user to role 2
        role1.setId(2);
        role1.setName("ROLE_USER");

    roleRepository.save(role);  //save parent object
    roleRepository.save(role1);  //save parent object


    List<Role> roles  = new ArrayList<>();
    roles.add(role);

    user.setRole(roles);         // child object.set(parent object)
    userRepository.save(user);   //   save  child object

return new ResponseEntity<>("user Registered", HttpStatus.CREATED);
}


//http://localhost:8080/api/auth/signin
@PostMapping("/signin")
public ResponseEntity<JWTAuthResponse> authenticateUser (@RequestBody LoginDto loginDto){

    UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =

            new UsernamePasswordAuthenticationToken(loginDto.getUsernameOrEmail(), loginDto.getPassword());
    // Class :UsernamePasswordAuthenticationToken



    Authentication authentication = authenticationManager.authenticate(usernamePasswordAuthenticationToken); //Interface:AuthenticationManager

    // comparison with user email and password is done here  like an if condition
    // comparison of loginDto data  with user data  present in db with
    // authenticate method automatically calls loadUserByUsername

    SecurityContextHolder.getContext().setAuthentication(authentication);  //

    // get token form tokenProvider
    String token = tokenProvider.generateToken(authentication);
    return ResponseEntity.ok(new JWTAuthResponse(token));




}
}
