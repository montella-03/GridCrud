package com.example.store;

import com.example.store.Backend.employees.Employee;
import com.example.store.Backend.employees.EmployeeRepository;
import com.vaadin.flow.component.page.AppShellConfigurator;
import com.vaadin.flow.theme.Theme;
import com.vaadin.flow.theme.lumo.Lumo;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collections;

@SpringBootApplication
@Theme(value="store",variant = Lumo.LIGHT)
public class StoreApplication implements AppShellConfigurator {

    private final EmployeeRepository employeeRepository;

    public StoreApplication(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }


    public static void main(String[] args) {
        SpringApplication.run(StoreApplication.class, args);
    }

    @Bean
    CommandLineRunner commandLineRunner(){
        return args -> {
            var employee = new Employee();
            employee.setFirstName("Admin");
            employee.setLastName("Admin");
            employee.setEmail("admin@localhost");
            employee.setPassword("admin");
            employee.setPhoneNumber("254700000000");
            employee.setAddress("Nairobi");
            employee.setLocked(false);
            employee.setAuthorities(Collections.singletonList(new SimpleGrantedAuthority("MANAGER")));
            employee.setPassword("{bcrypt}"+employee.getPassword());
            employeeRepository.save(employee);
        };
    }

}
