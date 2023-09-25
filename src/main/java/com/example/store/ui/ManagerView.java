package com.example.store.ui;

import com.example.store.Backend.products.Product;
import com.example.store.Backend.products.ProductService;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import jakarta.annotation.security.RolesAllowed;
import org.vaadin.crudui.crud.impl.GridCrud;

@RolesAllowed("USER")
@Route("/manager")
public class ManagerView extends VerticalLayout {

    private ManagerView(ProductService productService){
        GridCrud<Product> grid = new GridCrud<>(Product.class,productService);
        grid.getGrid().setColumns("name", "description", "farm", "category", "quantity");
        grid.getCrudFormFactory().setUseBeanValidation(true);
        grid.getCrudFormFactory().setVisibleProperties("name", "description", "farm", "category", "quantity");
        add(
                new H1("Welcome to the store"),
                grid
        );

    }
}
