package com.example.store.Backend.employees;

import com.example.store.Backend.config.SecurityService;
import org.springframework.stereotype.Service;
import org.vaadin.crudui.crud.CrudListener;

import java.time.LocalDateTime;
import java.util.Collection;

@Service
public class EmployeeService implements CrudListener<Employee> {
    private final EmployeeRepository employeeRepository;
    private final SecurityService securityService;

    public EmployeeService(EmployeeRepository employeeRepository, SecurityService securityService) {
        this.employeeRepository = employeeRepository;
        this.securityService = securityService;
    }

    @Override
    public Collection<Employee> findAll() {
        return employeeRepository.findAll();
    }

    @Override
    public Employee add(Employee employee) {
        employee.setCreatedAt(LocalDateTime.now());
        employee.setCreatedBy(securityService.getLoggedInUser());
        return employeeRepository.save(employee);
    }

    @Override
    public Employee update(Employee employee) {
        employee.setUpdatedAt(LocalDateTime.now());
        employee.setUpdatedBy(securityService.getLoggedInUser());
        return employeeRepository.save(employee);
    }

    @Override
    public void delete(Employee employee) {
        employeeRepository.delete(employee);

    }

}
