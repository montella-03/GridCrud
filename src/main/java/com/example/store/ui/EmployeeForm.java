package com.example.store.ui;

import com.example.store.Backend.enumerations.Role;
import com.example.store.Backend.employees.Employee;
import com.example.store.Backend.employees.EmployeeService;
import com.vaadin.collaborationengine.UserInfo;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.EmailField;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.router.Route;
import jakarta.annotation.security.RolesAllowed;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.Objects;

@Route(value = "new-employee",layout = MainLayout.class)
@RolesAllowed("MANAGER")
public class EmployeeForm extends VerticalLayout {
    private final EmployeeService employeeService;
    private TextField firstName = new TextField("First Name");
    private TextField lastName = new TextField("Last Name");
    private EmailField email = new EmailField("Email");
    private PasswordField password = new PasswordField("Password");
    private TextField phoneNumber = new TextField("Phone Number", "254");
    private TextField address = new TextField("Address");
    private final ComboBox<Role> roles = new ComboBox<>("Authorization Level");

    private ComboBox<Boolean> isLocked = new ComboBox<>("Locked");


        public EmployeeForm(EmployeeService employeeService) {
            this.employeeService = employeeService;

            firstName.setRequired(true);
            lastName.setRequired(true);
            email.setRequired(true);
            password.setRequired(true);
            phoneNumber.setRequired(true);
            address.setRequired(true);
            roles.setRequired(true);
            isLocked.setRequired(true);

            UserDetails userDetails=(UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            String username=userDetails.getUsername();
            var userInfo = new UserInfo(username,username);
            var binder = new Binder<>(Employee.class);
            isLocked.setItems(true,false);
            roles.setItems(Role.values());
            binder.bindInstanceFields(this);
//            binder.setTopic("product", Employee::new);

//            String employeeTopic = "employee";
//
//            var messageList = new CollaborationMessageList(userInfo,employeeTopic);

            //binder
            binder.forField(firstName)
                    .withValidator(name -> name.length() >= 3, "Name must be at least 3 characters long")
                    .bind(Employee::getFirstName, Employee::setFirstName);
            binder.forField(lastName)
                    .withValidator(name -> name.length() >= 3, "Name must be at least 3 characters long")
                    .bind(Employee::getLastName, Employee::setLastName);
            binder.forField(email)
                    .withValidator(email -> email.contains("@"), "Please enter a valid email address")
                    .bind(Employee::getEmail, Employee::setEmail);
            binder.forField(password)
                    .withValidator(password -> password.length() >= 8, "Password must be at least 8 characters long")
                    .bind(Employee::getPassword, Employee::setPassword);
            binder.forField(phoneNumber)
                    .withValidator(phoneNumber -> phoneNumber.length() == 12, "Please enter a valid phone number starting with country code")
                    .bind(Employee::getPhoneNumber, Employee::setPhoneNumber);
            binder.forField(address)
                    .withValidator(address -> address.length() >= 3, "Address must be at least 3 characters long")
                    .bind(Employee::getAddress, Employee::setAddress);
            binder.forField(roles)
                    .withValidator(Objects::nonNull, "Please select a role")
                    .bind(Employee::getRole, Employee::setRole);

            binder.forField(isLocked)
                    .withValidator(Objects::nonNull, "Please select a lock status")
                    .bind(Employee::isLocked, Employee::setLocked);

            var form =  new FormLayout(
                    firstName,
                    lastName,
                    email,
                    password,
                    phoneNumber,
                    address,
                    roles,
                    isLocked
            );
            form.setWidth("60%");
            form.addClassNames("flex","justify-center");
            add(
                    new H1("New Employee"),
                    new HorizontalLayout(
                            new VerticalLayout(
                                   form,
                                    new HorizontalLayout(
                                            new Button("Save", event -> {
                                                var employee = new Employee();
                                                if(binder.writeBeanIfValid(employee)){
                                                    employeeService.add(employee);
                                                    Notification.show("Employee saved", 3000, Notification.Position.MIDDLE);

                                                    binder.readBean(new Employee());

                                                }
                                                else {
                                                    Notification.show("Employee not saved", 3000, Notification.Position.MIDDLE);
                                                }

                                            }),
                                            new Button("Cancel", event -> getUI().ifPresent(ui -> ui.navigate("manager")))

                                    )

                                    )


                    )
            );
        }

}
