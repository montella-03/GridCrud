package com.example.store.ui;

import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import jakarta.annotation.security.RolesAllowed;

@Route(value = "booking",layout = MainLayout.class)
@RolesAllowed("USER")
public class BookingView extends VerticalLayout {

    public BookingView() {
        add(
                new H1("Booking")
        );
    }
}
