package com.example.springbootfirst.controller;

import com.example.springbootfirst.controllers.EmployeeController;
import com.example.springbootfirst.models.RegisterDetails;
import com.example.springbootfirst.services.EmployeeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

public class EmployeeControllerTest {

    @Mock
    EmployeeService employeeService;

    @Mock
    EmployeeController employeeController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void testRoute(){
        String result = employeeController.route();
        assertEquals("hello",result);
    }

    @Test
    void testGetAllEmployees(){
        RegisterDetails emp1 =  new RegisterDetails();
        RegisterDetails emp2 =  new RegisterDetails();

        when(employeeService.getAllEmployees()).thenReturn(Arrays.asList(emp1,emp2));
        List<RegisterDetails> result = employeeService.getAllEmployees();
        assertEquals(2,result.size());

    }
}
