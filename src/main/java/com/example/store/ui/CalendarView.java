package com.example.store.ui;

import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import jakarta.annotation.security.RolesAllowed;
import org.vaadin.stefan.fullcalendar.FullCalendar;

@Route(value = "calendar",layout = MainLayout.class)
@RolesAllowed({"USER","MANAGER","ADMIN"})
public class CalendarView extends VerticalLayout {

    public CalendarView() {
        add(
                new H1("Calendar"),
                new FullCalendar()
        );
    }

}
