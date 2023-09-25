package com.example.store.ui;

import com.example.store.Backend.products.Product;
import com.example.store.Backend.products.ProductService;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouterLink;
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
        grid.setAddOperationVisible(false);
        grid.getCrudLayout().addToolbarComponent(new RouterLink("New Product", NewProductView.class));
        add(
                new HorizontalLayout(
                        new H1("Store Products"),
                new RouterLink("Report", ReportView.class)
                ),
                grid
        );

    }
}
