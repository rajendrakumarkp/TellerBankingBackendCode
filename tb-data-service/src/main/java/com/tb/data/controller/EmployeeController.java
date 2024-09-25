package com.tb.data.controller;


import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.tb.data.model.Employee;
import com.tb.data.repository.EmployeeRepository;

@RestController
@RequestMapping("/rest/employees")
public class EmployeeController {

    @Autowired
    private EmployeeRepository employeeRepository;

    @PostMapping("/register")
    public ResponseEntity<Map<String, Object>> registerEmployee(@RequestBody Employee employee) {
    	System.out.println("EmployeeController: registerEmployee() method.");
        employee.setCredit(BigDecimal.valueOf(1000.00));
        employeeRepository.save(employee);
        Map<String, Object> response = new HashMap<>();
        response.put("success", true);
        response.put("credit", employee.getCredit());
        return ResponseEntity.ok(response);
    }

    @GetMapping("/balance")
    public ResponseEntity<Map<String, Object>> getEmployeeBalance(@RequestParam String email, @RequestParam String company) {
    	System.out.println("EmployeeController: getEmployeeBalance() method.");
        Optional<Employee> employee = employeeRepository.findByEmailAndCompany(email, company);
        if (employee.isPresent()) {
            Map<String, Object> response = new HashMap<>();
            response.put("credit", employee.get().getCredit());
            return ResponseEntity.ok(response);
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/list")
    public ResponseEntity<List<Employee>> listEmployees(@RequestParam String company) {
    	System.out.println("EmployeeController: listEmployees() method.");
        List<Employee> employees = employeeRepository.findByCompany(company);
        return ResponseEntity.ok(employees);
    }

    @PutMapping("/update-balance")
    public ResponseEntity<Map<String, Object>> updateBalance(@RequestParam String email, @RequestParam String company, @RequestParam BigDecimal credit) {
    	System.out.println("EmployeeController: updateBalance() method.");
        Optional<Employee> employee = employeeRepository.findByEmailAndCompany(email, company);
        if (employee.isPresent()) {
            employee.get().setCredit(credit);
            employeeRepository.save(employee.get());
            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("credit", credit);
            return ResponseEntity.ok(response);
        }
        return ResponseEntity.notFound().build();
    }
}