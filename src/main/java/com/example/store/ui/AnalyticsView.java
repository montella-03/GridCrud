package com.example.store.ui;

import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import jakarta.annotation.security.RolesAllowed;

@Route(value = "analytics",layout = MainLayout.class)
@RolesAllowed({"USER","MANAGER","ADMIN"})
public class AnalyticsView extends VerticalLayout {

        public AnalyticsView() {
            add(
                    new H1("Analytics")
            );
        }
}
