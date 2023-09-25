package com.example.store.ui;

import com.example.store.Backend.products.Product;
import com.example.store.Backend.products.ProductService;
import com.vaadin.collaborationengine.CollaborationBinder;
import com.vaadin.collaborationengine.CollaborationMessageInput;
import com.vaadin.collaborationengine.CollaborationMessageList;
import com.vaadin.collaborationengine.UserInfo;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.IntegerField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.router.Route;
import jakarta.annotation.security.RolesAllowed;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

@Route("new-product")
@RolesAllowed("USER")
public class NewProductView extends VerticalLayout {
    private final ProductService productService;
    private TextField name = new TextField("Name");
    private TextField description = new TextField("Description");
    private TextField farm = new TextField("Farm");
    private TextField category = new TextField("Category");
    private IntegerField quantity = new IntegerField("Quantity");


        public NewProductView( ProductService productService1) {
            this.productService = productService1;
            UserDetails userDetails=(UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            String username=userDetails.getUsername();
            var userInfo = new UserInfo(username,username);
            var binder = new CollaborationBinder<>(Product.class,userInfo);
            binder.bindInstanceFields(this);
            binder.setTopic("product", Product::new);

            var messageList = new CollaborationMessageList(userInfo,"product");
            add(
                    new H1("New Product"),
                    new HorizontalLayout(
                            new VerticalLayout(
                                    new FormLayout(
                                            name,
                                            description,
                                            farm,
                                            category,
                                            quantity),
                                    new Button("Save", event -> {
                                        var product = new Product();
                                        if(binder.writeBeanIfValid(product)){
                                            productService.add(product);
                                            Notification.show("Product saved");
                                            binder.reset(new Product());

                                        }
                                        else {
                                            Notification.show("Product not saved");
                                        }

                                    })

                            ),
                            new VerticalLayout(
                                    messageList,
                                    new CollaborationMessageInput(messageList)

                    )
                    )
            );
        }
}
