package com.example.store.ui;

import com.example.store.Backend.products.Product;
import com.example.store.Backend.products.ProductService;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.cookieconsent.CookieConsent;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.auth.AnonymousAllowed;
import jakarta.annotation.security.RolesAllowed;
import org.vaadin.crudui.crud.impl.GridCrud;


@RolesAllowed({"ROLE_USER"})
@Route(value = "",layout = MainLayout.class)
public class HomeView extends VerticalLayout {

    public HomeView(ProductService productService){
        GridCrud<Product> grid = new GridCrud<>(Product.class,productService);
        grid.getGrid().setColumns("name", "description", "farm", "category", "quantity");
        grid.getCrudFormFactory().setUseBeanValidation(true);
        grid.getCrudFormFactory().setVisibleProperties("name", "description", "farm", "category", "quantity");

        add(
                getHeader(),
                new H2("Products"),

                grid
        );

    }

    private Component getHeader() {
        HorizontalLayout header = new HorizontalLayout();
        header.setWidth("100%");
        header.setJustifyContentMode(JustifyContentMode.BETWEEN);
        header.setAlignItems(Alignment.CENTER);

        header.add(new H1("Welcome to the store"));

        return header;
    }


}
