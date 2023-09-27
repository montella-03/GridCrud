package com.example.store.ui;

import com.example.store.Backend.employees.Employee;
import com.example.store.Backend.employees.EmployeeService;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import jakarta.annotation.security.RolesAllowed;
import org.vaadin.crudui.crud.impl.GridCrud;

@RolesAllowed("MANAGER")
@Route(value = "/manager",layout = MainLayout.class)
public class ManagerView extends VerticalLayout {

    private ManagerView(EmployeeService employeeService){
        GridCrud<Employee> grid = new GridCrud<>(Employee.class,employeeService);
        grid.getGrid().setColumns("firstName","lastName","email","phoneNumber","address","role");
        grid.getGrid().addColumn(employee -> employee.isLocked() ? "Locked" : "Unlocked").setHeader("Status");
        grid.getCrudFormFactory().setUseBeanValidation(true);
        grid.getCrudFormFactory().setVisibleProperties("firstName","lastName","email","phoneNumber","address","role","isLocked");
        grid.setAddOperationVisible(false);
        grid.addClassNames("shadow-lg","p-2");
        grid.getCrudLayout().addToolbarComponent(addEmployeeLink());
        add(
                new H2("All Employees"),
               header(),
                grid
        );

    }

    private Button addEmployeeLink() {
        var addEmployee = new Button("Add Employee",e->getUI().ifPresent(ui -> ui.navigate("new-employee")));
        addEmployee.addClassNames("btn","btn-primary","gap-6");
        return addEmployee;
    }

    private HorizontalLayout header() {
        var employee = new Button("Employee",e->getUI().ifPresent(ui -> ui.navigate("manager")));
        employee.addClassNames("btn", "btn-primary","gap-6");

        var room = new Button("Room",e->getUI().ifPresent(ui -> ui.navigate("manager/rooms")));
        return new HorizontalLayout(
                employee,
                room
        );
    }
}
