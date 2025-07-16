package com.example.springbootfirst.services;

import com.example.springbootfirst.models.RegisterDetails;
import com.example.springbootfirst.models.Roles;
import com.example.springbootfirst.models.UserDetailsDto;
import com.example.springbootfirst.repository.RegisterDetailsRepository;
import com.example.springbootfirst.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class EmployeeService {

    @Autowired
    RegisterDetailsRepository regRepo;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    RoleRepository roleRepo;

    public List<RegisterDetails> getAllEmployees(){
        return regRepo.findAll();
    }

    public RegisterDetails getEmployeeById(int id){
        return regRepo.findById(id).get();
    }

    public String addEmployee(UserDetailsDto register) {
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
        return "User added successfully!";
    }


    public String deleteEmployeeById(int id) {
        if (!regRepo.existsById(id)) {
            throw new RuntimeException("Employee not found with id: " + id);
        }
        regRepo.deleteById(id);
        return "Employee deleted successfully";
    }


    public String deleteAllEmployees(){
        regRepo.deleteAll();
        return "All employee deleted successfully";
    }

//  1 .  Update query by id

    public String updateEmployeeById(int id, UserDetailsDto employee) {
        Optional<RegisterDetails> existingEmp = regRepo.findById(id);

        if (existingEmp.isPresent()) {
            RegisterDetails existingEmployee = existingEmp.get();

            existingEmployee.setName(employee.getName());
            existingEmployee.setEmail(employee.getEmail());
            existingEmployee.setPassword(passwordEncoder.encode(employee.getPassword()));
            existingEmployee.setUserName(employee.getUserName());

            Set<Roles> roles = new HashSet<>();
            for (String roleName : employee.getRoleNames()) {
                Roles role = roleRepo.findByRoleName(roleName)
                        .orElseThrow(() -> new RuntimeException("Role not found"));
                roles.add(role);
            }
            existingEmployee.setRoles(roles);

            regRepo.save(existingEmployee);
            return "Employee updated successfully";
        } else {
            throw new RuntimeException("Employee not found with id: " + id);
        }
    }



//    2 . find by roles specified

    public List<RegisterDetails> findEmployeesByRole(String roleName){
        List<RegisterDetails> employees = new ArrayList<>();

        for(RegisterDetails registerDetails : regRepo.findAll()){
            for(Roles role : registerDetails.getRoles()){
                if(role.getRoleName().equals(roleName.toUpperCase())){
                    employees.add(registerDetails);
                }
            }
        }
        return employees;
    }

}
