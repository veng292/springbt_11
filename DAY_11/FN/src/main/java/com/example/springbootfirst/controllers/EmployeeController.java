package com.example.springbootfirst.controllers;

import com.example.springbootfirst.models.RegisterDetails;
import com.example.springbootfirst.models.UserDetailsDto;
import com.example.springbootfirst.services.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/employee")
public class EmployeeController {

    @Autowired
    EmployeeService employeeService;

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public List<RegisterDetails> getEmployees(){
        return employeeService.getAllEmployees();
    }


    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    public RegisterDetails getEmployeeById(@PathVariable int id){
        return  employeeService.getEmployeeById(id);
    }


    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public String addEmployee(@RequestBody UserDetailsDto dto){
        return employeeService.addEmployee(dto);
    }


    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    public String updateEmployee(@PathVariable int id, @RequestBody UserDetailsDto reg){
        return employeeService.updateEmployeeById(id, reg);
    }


    @DeleteMapping
    @PreAuthorize("hasRole('ADMIN')")
    public String deleteEmployees(){
        return employeeService.deleteAllEmployees();
    }


    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public String deleteEmployeeById(@PathVariable int id){
        return employeeService.deleteEmployeeById(id);
    }


    @GetMapping("/roles/{role}")
    @PreAuthorize("hasRole('ADMIN')")
    public List<RegisterDetails> getEmployeesByRoles(@PathVariable String role){
        return employeeService.findEmployeesByRole(role);
    }

    public String route() {
        return "hello";
    }
}