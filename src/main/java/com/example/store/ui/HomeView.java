package com.example.store.ui;

import com.example.store.Backend.products.Product;
import com.example.store.Backend.products.ProductService;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.auth.AnonymousAllowed;
import org.vaadin.crudui.crud.impl.GridCrud;


@AnonymousAllowed
@Route("")
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

        Button btn = new Button("Main");
        btn.addClickListener(e -> btn.getUI().ifPresent(ui -> ui.navigate("/manager")));
        header.add(new H1("Welcome to the store"));

        header.add(btn);
        return header;
    }


}
