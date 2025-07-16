package com.example.springbootfirst.services;

import com.example.springbootfirst.jwt.JwtTokenProvider;
import com.example.springbootfirst.models.JwtResponse;
import com.example.springbootfirst.models.RegisterDetails;
import com.example.springbootfirst.models.Roles;
import com.example.springbootfirst.models.UserDetailsDto;
import com.example.springbootfirst.repository.RegisterDetailsRepository;
import com.example.springbootfirst.repository.RegisterRepository;
import com.example.springbootfirst.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class AuthService {

    @Autowired
    RegisterRepository registerRepository;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private RegisterDetailsRepository regRepo;

    @Autowired
    private RoleRepository roleRepo;

    @Autowired
    JwtTokenProvider jwtTokenProvider;

    @Autowired
    PasswordEncoder passwordEncoder;



    public List<RegisterDetails> getRegisterDetails() {
        return regRepo.findAll();
    }

    public String addNewUser(UserDetailsDto register) {
        RegisterDetails registerDetails = new RegisterDetails();
        registerDetails.setEmpId(register.getEmpId());
        registerDetails.setName(register.getName());
        registerDetails.setEmail(register.getEmail());
        registerDetails.setPassword(passwordEncoder.encode(register.getPassword()));
        registerDetails.setUserName(register.getUserName());

        Set<Roles> roles = new HashSet<>();
        for (String roleName : register.getRoleNames()) {
            Roles role = roleRepo.findByRoleName(roleName)
                    .orElseThrow(() -> new RuntimeException("Role not found: " + roleName));
            roles.add(role);
        }
        registerDetails.setRoles(roles);

        regRepo.save(registerDetails);
        return "User registered successfully!";
    }


//    public String authenticate(RegisterDetails login) {
    ////      This triggers Spring Security:
    ////      Calls your CustomUserDetailsService.loadUserByUsername()
//        Authentication authentication =
//                authenticationManager.authenticate(
//                        new UsernamePasswordAuthenticationToken(
//                                login.getUserName(), login.getPassword()
//                        )
//                );
//        return jwtTokenProvider.generateToken(authentication);
//    }


// response like JwtResponse
    public JwtResponse authenticate(RegisterDetails login) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(login.getUserName(), login.getPassword())
        );

        String token = jwtTokenProvider.generateToken(authentication);

        // Extract username
        String username = login.getUserName();

        // Extract roles
        List<String> roles = authentication.getAuthorities().stream()
                .map(role -> role.getAuthority())
                .collect(Collectors.toList());

        String joinedRoles = String.join(",", roles);

        return new JwtResponse(token, username, joinedRoles);
    }


    public Optional<RegisterDetails> findByUserByUsername(String userName) {
        return regRepo.findByUserName(userName);
    }

}
