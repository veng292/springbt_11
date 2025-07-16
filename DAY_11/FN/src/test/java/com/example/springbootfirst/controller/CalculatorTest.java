package com.example.springbootfirst.controller;

import com.example.springbootfirst.controllers.Calculator;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CalculatorTest {

    Calculator calculator = new Calculator();

    private int a = 7 , b = 5;

    @Test
    public void TestAddition(){
        int result = calculator.add(a,b);
        System.out.println(result);
        assertEquals(12,result);
    }

    @Test
    public void TestSubtraction(){
        int result = calculator.sub(a,b);
        System.out.println(result);
        assertEquals(2,result);
    }

    @Test
    public void TestMultiplication(){
        int result = calculator.mul(a,b);
        System.out.println(result);
        assertEquals(35,result);
    }

    @Test
    public void TestDivision(){
        int result = calculator.div(a,b);
        System.out.println(result);
        assertEquals(1,result);
    }

    @Test
    public void TestModulo(){
        int result = calculator.mod(a,b);
        System.out.println(result);
        assertEquals(2,result);
    }

}
