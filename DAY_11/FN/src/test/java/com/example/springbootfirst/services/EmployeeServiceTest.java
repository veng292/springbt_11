package com.example.springbootfirst.sevices;

import com.example.springbootfirst.models.RegisterDetails;
import com.example.springbootfirst.repository.RegisterDetailsRepository;
import com.example.springbootfirst.services.EmployeeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

public class EmployeeServiceTest {
    @Mock
    RegisterDetailsRepository registerDetailsRepository;

    @InjectMocks
    EmployeeService employeeService;

    @BeforeEach
    void setUp(){
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void testGetMethod(){
        RegisterDetails emp1 = new RegisterDetails();
        RegisterDetails emp2 = new RegisterDetails();

//        when -> creating an mock
        when(registerDetailsRepository.findAll()).thenReturn(Arrays.asList(emp1,emp2));

        List<RegisterDetails> result = employeeService.getAllEmployees();
        System.out.println(result);
        assertEquals(2,result.size());
    }


}
