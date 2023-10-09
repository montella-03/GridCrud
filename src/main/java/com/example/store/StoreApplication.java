package com.example.store;

import com.vaadin.flow.component.page.AppShellConfigurator;
import com.vaadin.flow.theme.Theme;
import com.vaadin.flow.theme.lumo.Lumo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@Theme(value="store",variant = Lumo.LIGHT)
public class StoreApplication implements AppShellConfigurator {


    public static void main(String[] args) {
        SpringApplication.run(StoreApplication.class, args);
    }




}
