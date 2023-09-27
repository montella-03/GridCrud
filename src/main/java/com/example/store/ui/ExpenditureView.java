package com.example.store.ui;

import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import jakarta.annotation.security.RolesAllowed;

@Route(value = "expenditure",layout = MainLayout.class)
@RolesAllowed({"USER","MANAGER"})
public class ExpenditureView extends VerticalLayout {

    public ExpenditureView() {
        add(
                new H1("Expenditure")
        );
    }
}
