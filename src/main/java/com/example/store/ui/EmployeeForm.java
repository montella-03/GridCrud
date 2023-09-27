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
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

@Route(value = "new-employee",layout = MainLayout.class)
@RolesAllowed("MANAGER")
public class EmployeeForm extends VerticalLayout {
    private final EmployeeService employeeService;
    private TextField firstName = new TextField("First Name");
    private TextField lastName = new TextField("Last Name");
    private EmailField email = new EmailField("Email");
    private PasswordField password = new PasswordField("Password");
    private TextField phoneNumber = new TextField("Phone Number");
    private TextField address = new TextField("Address");
    private ComboBox<Role> role = new ComboBox<>("Role");

    private ComboBox<Boolean> isLocked = new ComboBox<>("Locked");


        public EmployeeForm(EmployeeService employeeService) {
            this.employeeService = employeeService;
            UserDetails userDetails=(UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            String username=userDetails.getUsername();
            var userInfo = new UserInfo(username,username);
            var binder = new Binder<>(Employee.class);
            isLocked.setItems(true,false);
            role.setItems(Role.values());
            binder.bindInstanceFields(this);
//            binder.setTopic("product", Employee::new);

//            String employeeTopic = "employee";
//
//            var messageList = new CollaborationMessageList(userInfo,employeeTopic);
            var form =  new FormLayout(
                    firstName,
                    lastName,
                    email,
                    password,
                    phoneNumber,
                    address,
                    role,
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
